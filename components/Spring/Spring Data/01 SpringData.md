# Spring Data MongoDB

## 1. Dependencies 依赖

POM 依赖：
```xml
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-mongodb</artifactId>
    <version>3.4.5</version>
</dependency>
```

## 2. Core concepts 核心概念

### `Repository`

`interface Repository<T, ID>` 是 Spring Data 持久化顶级接口： 
* 捕获要管理的域类型以及域类型的 ID 类型
* 保存类型信息，并能够在类路径扫描期间发现此代码的接口，以便于创建 Spring Bean

### `CrudRepository`

`interface CrudRepository<T, ID> extends Repository<T, ID>` 为被管理的实体类 `T` 提供 CRUD 功能

* `<S extends T> S save(S entity)`: 保存给定实体 `entity`
* `<S extends T> Iterable<S> saveAll(Iterable<S> entities)`: 批量保存实体类（可遍历集合） `entities`
* `Optional<T> findById(ID id)`: 根据实体 `id` 查询
* `boolean existsById(ID id)`: 给定 `id` 的实体是否存在
* `Iterable<T> findAll()`: 返回域类型的所有实例
* `Iterable<T> findAllById(Iterable<ID> ids)`: 返回实体 `ID` 集合 `ids` 查询域类型实例
* `long count()`: 返回实体的数量
* `void deleteById(ID id)`: 根据给定的 `id` 删除实体
* `void delete(T entity)`: 删除给定的实体
* `void delete(Iterable<? extends ID> ids)`: 删除 `ID` 的集合 `ids` 对应的的实体集合
* `void deleteAll(Iterable<? extends T> entities)`: 删除给定实体集合
* `void deleteALL()`: 删除存储库管理的所有实体

### `PagingAndSortingRepository`

`interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID>` 添加对实体的分页、排序功能

* `Iterable<T> findAll(Sort sort)`: 返回按给定选项排序的所有实体
* `Page<T> findAll(Pageable pageable)`: 返回满足 Pageable 对象中提供的分页限制的实体的页

Example:
```jshelllanguage
PagingAndSortingRepository<User, Long> repository = null;
Page<User> users = repository.findAll(PageRequest.of(1, 20));
```

> 支持派生
> 
> 自定义 Repository 时，按照规范自定义方法接口。

## 3. Query Methods _查询方法_

* Defining Repository Interfaces _定义 Repository 接口_ 

   继承 `Repository` 或继承 `Repository` 子接口

* Defining Query Methods _接口内定义查询方法_
* Creating Repository Instances _创建存储库实例_

   * JavaConfig 方式创建接口代理实例
   * XML 方式创建接口代理实例
* Custom Implementations for Spring Data Repositories _Spring Data Repositories 自定义实现_

### 3.1. Defining Repository Interface _定义存储库接口_

首先需要定义特定于**域类**的 Repository 接口。
接口必须扩展 Repository 并将接口类型为对应域类和 `ID` 类型。
根据需要公开的能力继承 `Repository` (`CrudRepository` `PagingAndSortingRepository`) 。

#### 3.1.1. Fine-tuning Repository Definition _微调存储库定义_

通常，存储库接口继承 `Repository` `CrudRepository` `PagingAndSortingRepository` 。
如果不想扩展 Spring Data 接口，可以使用 `@RepositoryDefinition` 对存储库接口进行注释。

> 中间存储库接口使用 `@NoRepositoryBean` 修饰，确保该注释修饰的接口不会被创建实例。

#### 3.1.2. Using Repositories with Multiple Spring  Data Modules 使用多个 Spring Data 模块的存储库

有时，应用程序需要使用多个 Spring Data 模块。
这种情况下，存储库定义必须区分持久化技术。
当 Spring Data 在类路径上检测到多个存储库， Spring Data 进入严格的存储库配置模式。
严格配置使用**存储库**或**域类**的详细信息来决定 Spring Data 模块绑定于存储库定义：

1. 如果存储库定义 `extends` 了特定于模块的存储库 (`JpaRepository` `MongoRepository`) ，那么它就是特定 Spring Data 模块的有效候选者
2. 如果**域类**使用特定于模块的类型注解 (`@Entity` `@Document`) 进行修饰，则是特定 Spring Data 模块的有效候选者

   > 如果不同模块的 Spring Data 域类注解修饰同一个类，则不饿能区分具体的存储库，导致未定义行为。

3. 指定存储库包扫描路径。（不同的存储库需要定义在适当的包中 `@EnableJpaRepositories(basePackages = "")` `@EnableMongoRepositories(basePackage = "")` ）

#### 3. Defining Query Methods 定义查询方法

存储库代理有两种方法从方法名派生特定于存储库的查询：
* 通过直接从方法名派生查询
* 通过使用手动定义的查询
可用选项取决于实际存储。但是，必须有一个决定创建实际查询的策略。

##### 3.1 Query Lookup Strategies 查询查找策略

> 对于特定的数据存储，可能不支持某些策略。

* 使用 XML 配置： `query-lookup-policy` 属性在命名空间中配置策略。
* 使用 JavaConf 配置： `@Enable%{store}Repository` 注解的 `queryLookupStrategy` 属性配置策略。


> * `QueryLookupStrategy.Key.CREATE` : 尝试从查询方法构造特定存储区的查询
> 
>    通常的方法是从方法名称中删除给定的一组已知前缀，并解析方法的其余部分。 参考 [Query Creation]()
> 
> * `QueryLookupStrategy.Key.USE_DECLARED_QUERY` : 尝试查找已声明的查询，如果找不到，则引发异常
> 
>    查询可以通过某处的注解 `@Query` 定义，也可以通过其他方式声明。参阅 特定存储区的文档
> 
> * `QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND` : (**默认值**)结合了 `CREATE` 和 `USE_DECLARED_QUERY` ，首先查找声明的查询，如果未找到声明的查询，则创建一个自定义的基于方法名称的查询。
> 
>    默认策略，没有显式的配置时，使用它。它允许根据方法名称快速自定义查询，还可以根据需要引入声明的查询来自定义调优这些查询。
>

##### 3.2 Query Creation 创建查询

##### 3.3 Property Expressions 属性表达式

##### 3.4 Special parameter handling 特殊参数处理

##### 3.5 Limiting Query Results 限制查询

##### 3.6 Repository Methods Returning Collections or Iterables

##### 3.7 Null Handling of Repository Methods

##### 3.8 Streaming Query Results

##### 3.9 Asynchronous Query Results

#### 4. Creating Repository Instances

#### 5. Custom Implementations for Spring Data Repositories

#### 6. Publishing Events from Aggregate Roots

#### 7. Spring Data Extensions 
