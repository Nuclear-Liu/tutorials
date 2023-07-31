# Caches 缓存

> Example:
> 
> 

## 适用性

缓存在各种用例中都非常有用。
例如，当某个值的计算或检索成本很高，而且在某个输入中需要多次使用该值时，就应该考虑使用缓存。

`Cache` 类似于 `CurrentMap` ，但不完全相同。
最根本的区别是， `CurrentMap` 会**保留添加到其中的所有元素**，直到它们被显式删除。
另一方面， `Cache` 通常配置为**自动逐出条目**，以**限制其内存占用**。
在某些情况下， **`LoadCache`** 即使不逐出条目也很有用，因为它会**自动加载缓存**。

通常， Guava 缓存组件适用于以下情况：

* 额外的内存开销提高速度。
* 预计 `key` 会被查询多次。
* 缓存数据不会超过 RAM 中的可用量。（Guava 缓存是应用程序单次运行的**本地**缓存；不会将数据存储在文件中或外部服务器上。）

使用 `CacheBuilder` 构建器模式获得 `Cache` ，重点在于定制缓存。

> **注意：**
> 如果不需要 `Cache` 的功能，那么 `ConcurrentHashMap` 会更节省内存。

## 入口

关于缓存，首先要问自己的问题是：是否有一些*可执行默认*函数来加载或计算与键相关的值？
如果是这样，应该使用 `CacheLoader` 。
如果没有，或者需要覆盖默认值，但仍然想要原子式的 **"get-if-absent-compute"** 语义，你就应该在 `get` 调用中传递一个 `Callable` 。
元素可以使用 `Cache.put()` 直接插入，但**首选**自动缓存加载，因为它可以更轻松推理所有缓存内容的一致性。

#### From a `CacheLoader`

`LoadingCache` 是使用附加的 `CacheLoader` 构建的 `Cache` 。
创建 `CacheLoader` 通常与实现 `V load(K key) throws Exception` 方法一样简单。
例如：

```jshelllanguage
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
       .maximumSize(1000)
       .build(
           new CacheLoader<Key, Graph>() {
             public Graph load(Key key) throws AnyException {
               return createExpensiveGraph(key);
             }
           });

// ...
try {
  return graphs.get(key);
} catch (ExecutionException e) {
  throw new OtherException(e.getCause());
}
```

查询 `LoadingCache` 的标准方法是使用方法 `get(K)` 。
该方法要么返回一个已缓存的值，要么使用缓存的 `CacheLoader` 原子式地将一个新值加载到缓存中。
由于 `CacheLoader` 可能会引发 `Exception` 异常，因此 `LoadingCache.get(K)` 会引发 `ExecutionException` 异常。
（如果缓存加载器抛出一个 _unchecked_ 异常）

#### From a `Callable`
