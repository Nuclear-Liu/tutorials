# `Loading` `Linking` `Initializing`

Class 文件加载到内存，通过三大步骤完成加载：
1. `Loading` 加载到内存
2. `Linking` 链接

    1. `verification` 校验
    2. `preparation` class 静态变量**赋默认值**
    3. `resolution` 

3. `Initializing` 惊天变量**赋初始值**

## `Loading`

每个 `.class` 文件都需要 **类加载器**(`ClassLoader`) 加载到内存中；

> 获取类的加载器： `*.class.getClassLoader()` `object.getClass().getClassLoader()`

`.class` 文件加载到内存包括两部分：
1. `.class` 二进制文件在内存中占用的空间；
2. 生成 `.class` 文件对应 `Class` 类对象，指向内存中 `.class` 二进制文件位置；

> **`LazyLoading` 懒加载**五种情况（JVM 规范没有具体规定）；
> JVM 严格规定了初始化的情形：
> 1. `new` `getstatic` `putstatic` `invokestatic` 指令，访问 `final` 变量除外；
> 2. `java.lang.reflect` 对类进行反射调用时；
> 3. 初始化子类的时候，父类需要先初始化；
> 4. 虚拟机启动时，被执行的主类必须初始化；
> 5. 动态语言支持： `java.lang.invoke.MethodHandle` 解析的结果为 `REF_getstatic` `REF_putstatic` `REF_invokestatic` 的方法句柄时，该类必须初始化；

### 类加载器

类加载器分成不同的层次，不同的类加载器负责加载不同的 `Class` ：

* Bootstrap ClassLoader : `null`

    加载核心类库（ `lib/rt.jar` `lib/charset.jar` 等），由 C++ 实现；
    
    通过 `getClassLoader()` 获得 `Bootstrap` 类加载器时返回 `null` ；

* Extension ClassLoader : `ExtClassLoader`

    加载扩展类库（ `jre/lib/ext/*.jar` ，或者由 `-DextJarFullyQualifiedName .jar`）

* Application ClassLoader : `AppClassLoader`

    加载 `classpath` 指定内容；

* Custom ClassLoader : ``

    自定义加载器（模板方法设计模式）：

    1. 重写 `ClassLoader` 的 `findClass()` ；

    > `ClassLoader::defineClass()` 将字节流转换为 `Class` 对象；

    > 动态代理，spring 等

### `Compiler` 编译

JVM 支持**解释器**解释执行与**编译器**编译执行(`JIT`)；
默认情况下使用**混合模式**；

编译模式配置：
* `-Xmixed` : （默认）混合模式；开始解释执行，启动速度较快，对热点代码进行检测和编译；
* `-Xint` : 解释模式；启动速度快，执行相对慢；
* `-Xcomp` : 编译模式；启动相对慢，执行速度快；

> * 解释器： bytecode intepreter
> * `JIT` ： Just In-Time compiler

> **混合模式**
> 
> 混合使用解释器 + 热点代码编译
> * 其实阶段采用解释执行
> * 热点代码检测，进行编译（热点检测**计数器配置**： `-XX:CompileThreshold = 10000` ）：
>     * 多次被调用的**方法**（**方法计数器**：检测方法执行频率）
>     * 多次被调用的**循环**（**循环计数器**：检测循环执行频率）


### 加载过程：双亲委派

`.class` 加载内存过程，即**双亲委派**：
1. 调用 `CustomClassLoader` ，查询自定义类加载器**缓存**；找到返回结果；
2. 如果没有找到，调用**父加载器** `AppClassLoader` ，查询应用类加载器**缓存**；找到返回结果；
3. 如果没有找到，调用**父加载器** `ExtClassLoader` ，查询扩展类加载器**缓存**；找到返回结果；
4. 如果没有找到，调用**父加载器** `BootstrapClassLoader` ，查询扩展类加载器**缓存**；找到返回结果；
5. 如果没有找到，要求**子加载器** `ExtClassLoader` 尝试加载，在管理加载区域找到，加载并返回结果；
6. 如果没有找到，要求**子加载器** `AppClassLoader` 尝试加载，在管理加载区域找到，加载并返回结果；
7. 如果没有找到，要求**子加载器** `CustomClassLoader` 尝试加载，在管理加载区域找到，加载并返回结果；
8. 如果没有找到，抛出 `ClassNotFoundException` ；

> 双亲委派：从子加载器到父加载器的过程，再从父加载器到子加载器的过程；

> 获取父加载器： `classLoaderObject.getParent()`

> 不同加载器之间不是继承关系，是使用关系（当前类加载器不存在，委托给父加载器），与类继承没有任何关系；
> 
> 父加载器**不是类加载器的加载器**；

> 双亲委派的优势：
> 
> * 主要是为了**安全**，父子加载器对应不同安全等级；
> * 避免重复加载；

> 打破双亲委派：
> 
> 重写 `ClassLoader::loadClass()` ；
> 
> 应用情形：
> 1. JDK 1.2 之前，自定义 `ClassLoader` 都必须重写 `loadClass()` ；
> 2. `ThreadContextClassLoader` 可以实现基础类调用实现类代码，通过 `thread.setContextClassLoader` 指定；
> 3. 热启动，热部署： （ OSGI tomcat 都有自己模块指定 `ClassLoader` （加载同一个类库的不同版本））；

#### 类加载器加载范围

* jdk 18: `jdk.internal.loader.ClassLoaders`

* jdk 11 jdk 8: `sun.misc.Launcher`
    * `sun.boot.class.path`: BootstrapClassLoader 加载路径
    * `java.ext.dirs`: ExtClassLoader 加载路径
    * `java.class.path`: AppClassLoader 加载路径

## `Linking` 链接

### `verification` 验证

验证 `.class` 文件是否符合 JVM 规定；

### `preparation` 预处理

静态变量**赋默认值**；

### `resolution` 解析

将**类**、**方法**、**属性**等符号引用解析为**直接引用**；
常量池中的各种符号引用解析为指针、偏移量等内存地址的直接引用；

## `Initializing`

调用类初始化代码 `<clinit>` ；
静态变量**赋初始值**；

> 创建对象的过程（与类加载过程类似）：
> 
> 1. 申请内存，属性赋默认值；
> 2. 调用构造方法，属性赋初始值；
