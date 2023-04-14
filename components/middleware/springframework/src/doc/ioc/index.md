# IOC

## 引入


xml 注入：
```xml
<beans>
    <bean id="petStore" class="org.hui.middleware.springframework.core.xml.services.PetStoreServiceImpl">
        <property name="accountDao" ref="accountDao"/>
        <property name="itemDao" ref="itemDao"/>
    </bean>
    <bean id="petStore2" class="org.hui.middleware.springframework.core.xml.services.PetStoreServiceImpl2">
        <constructor-arg name="accountDao" ref="accountDao"/>
        <constructor-arg name="itemDao" ref="itemDao"/>
        <constructor-arg name="str" value="123"/>
    </bean>
</beans>
```

Bean 初始化流程（Spring在具体的实现过程中在各个阶段增加了扩展性）：

1. 加载 `xml` 文件
2. 解析 `xml` 文件
3. 封装 `BeanDefinition`
4. 实例化
5. 放入容器中
6. 从容器中获取

> **容器**实现：
> 
> 使用 `Map` 存储（获取Bean的时候根据参数获取）； 可能的类型： `K:String V:Object` `K:Class V:Object` `K:String V:ObjectFactory` `K:String V:BeanDefinition`


## 容器


`BeanDefinitionReader` : 介于 `bean` 定义文件与 `BeanDefinition` 之间，定义解析**规范**，提供**扩展**；

`BeanDefinition` : 描述了一个 `bean` 实例，它具有**属性值**、**构造函数参数值**以及具体实现提供的更多信息；

`BeanFactory` : Bean 工厂；用于访问 Spring bean 容器的**根接口**。

> `DefaultListableBeanFactory` : Spring 的 `ConfigurableListableBeanFactory` 和 `BeanDefinitionRegistry` 接口的默认实现：一个基于 `bean` 定义元数据的成熟 `bean` 工厂，可通过后处理器进行扩展。
> 
> `AbstractAutowireCapableBeanFactory` : 提供 bean 创建（使用构造函数解析）、属性填充、连接（包括自动连接）和初始化。处理运行时 bean 引用、解析托管集合、调用初始化方法等。支持自动装配构造函数、按名称的属性和按类型的属性。

> **如果想随时修改 `BeanDefinition` 中的 `bean` 信息**；

`BeanFactoryPostProcessor` : 工厂挂钩，允许自定义修改应用程序上下文的 `bean` 定义，调整上下文底层 `bean` 工厂的 `bean` 属性值。

> `PlaceholderConfigurerSupport` : 解析 bean 定义属性值中的占位符的属性资源配置器的抽象基类。实现将属性文件或其他属性源中的值提取到 bean 定义中。

> PostProcessor: `BeanFactoryPostProcessor` vs `BeanPostProcessor`
> 
> 后置处理器、增强器
> 
> `BeanFactoryPostProcessor` : 增强 beanDefinition 信息；
> `BeanPostProcessor` : 增强 bean 信息

* 创建对象（bean生命周期）
    * 实例化：在堆中开辟一片空间（执行**构造方法**，对象属性值都是默认值）；

    * 初始化：给属性设置值；
        1. 填充属性 `popu` ；
        2. 设置 `Aware` 接口属性；
           `Aware`: 当容器创建的 bean 对象在进行具体操作的时候，如果需要容器的其他对象，此时可以将对象实现 `Aware` 接口满足需求；
        3. `BeanPostProcessor` : 增强(扩展 AOP)
            * `postProcessBeforeInitialization` : 前置处理方法
        4. 执行初始化方法 (`init-method`) ；
            
        5. `BeanPostProcessor` : 增强(扩展 AOP)
            * `postProcessAfterInitialization` : 后置处理方法
           
    * 得到完整对象； `context.getBean()` 获取对象；
    


* Spring Bean
  * 普通对象（自定义对象）
  * 容器对象（Spring 内置对象）

> `AbstractAutoProxyCreator implements BeanPostProcessor` : AOP
> 

观察者模式： 在不同的阶段处理不同的工作；
  * 监听器 `listener`
  * 监听事件
  * 多播器（广播器） `multicaster`

Spring 重点： **接口**
* `BeanFactory`
* `Aware`
* `BeanDefinition`
* `BeanDefinitionRegistry`
* `BeanDefinitionReader`
* `BeanFactoryPostProcessor`
* `BeanPostProcessor`
* `Environment` : `StanderEnvironment`
    * `System.getEnv()`
    * `System.getProperties()`
* `FactoryBean`

> `BeanFactory` vs `FactoryBean`
> 
1. 都是用来创建对象；
2. 使用 `BeanFactory` 必须遵循由 Spring 管理控制的创建过程；
3. 使用 `FactoryBean` 只需要调用 `getObject()` 可以得到具体对象，整个对象的创建过程由用户自己控制，更加灵活；
