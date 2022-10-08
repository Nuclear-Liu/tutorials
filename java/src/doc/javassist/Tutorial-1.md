# Getting Started with Javassist

> Shigeru Chiba


## 1. Reading and writing bytecode

Javassist is a class library for dealing with Java bytecode.
Java bytecode is stored in a binary file called a class file.
Each class file contains one Java class or interface.

Javassist 是一个用于处理 Java 字节码的类库。
Java 字节码存储在称为类文件的二进制文档中。
每个类文件都包含一个 Java 类或接口。

The class `Javassist.CtClass` is an abstract representation of a class file.  
A `CtClass` (compile-time class) object is a handle for dealing with a class file.  
The following program is a very simple example:

`Javassist.CtClass` 类是类文档的抽象表示形式。
`CtClass` (compile-time class) 对象是用于处理类文档的句柄。
以下进程是一个非常简单的示例：

```jshelllanguage
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("test.Rectangle");
cc.setSuperclass(pool.get("test.Point"));
cc.writeFile();
```

This program first obtains a `ClassPool` object, which controls bytecode modification with Javassist.  
The `ClassPool` object is a container of `CtClass` object representing a class file.  
It reads a class file on demand for constructing a `CtClass` object and records the constructed object for responding later accesses.

进程首先获取一个 `ClassPool` 对象，它用 Javassist 控制字节码修改。
`ClassPool` 对象是表示类文档的 `CtClass` 对象的容器。
它按需读取类文档以构造 `CtClass` 对象，并记录构造的对象以响应以后的访问。

To modify the definition of a class, the users must first obtain a reference to a `CtClass` object representing that class from a `ClassPool` object.
`get()` in `ClassPool` is used for this purpose.
In the case of the program shown above, the `CtClass` object representing a class `test.Rectangle` is obtained from the `ClassPool` object and it is assigned to a variable `cc`.
The `ClassPool` object returned by `getDefault()` searches the default system search path.

要修改类的定义，用户必须首先从 `ClassPool` 对象中获取对表示该类的 `CtClass` 对象的引用。
`ClassPool` 中的 `get()` 用于此目的。
在上面显示的进程的情况下，代表类 `test.Rectangle` 的 `CtClass` 对象是从 `ClassPool` 对象中获取的，并分配给变量 `cc` 。
`getDefault()` 返回的 `ClassPool` 对象搜索默认的系统搜索路径。

From the implementation viewpoint, `ClassPool` is a hash table of `CtClass` objects, which uses the class names as keys.  
`get()` in `ClassPool` searches this hash table to find a `CtClass` object associated with the specified key.  
If such a `CtClass` object is not found, `get()` reads a class file to construct a new `CtClass` object, which is recorded in the hash table and then returned as the resulting value of `get()`.

从实现的角度来看， `ClassPool` 是 `CtClass` 对象的哈希表，它使用类名作为键。
`ClassPool` 中的 `get()` 搜索此哈希表以查找与指定键关联的 `CtClass` 对象。
如果找不到 `CtClass` 对象，则 `get()` 将读取一个类文档来构造一个新的 `CtClass` 对象，该对象记录的哈希表中，然后作为 `get()` 的结果值返回。

The `CtClass` object obtained from a `ClassPool` object can be modified ([details of how to modify a `CtClass`](./Tutorial-2.md) will be presented later).
In the example above, it is modified so that the superclass of `test.Rectangle` is changed into a class `test.Point`.  
This change is reflected on the original class file when `writeFile()` in `CtClass` is finally called.

从 `ClassPool` 对象中获取的 `CtClass` 对象可以被修改（稍后将提供[如何修改 `CtClass` 的详细信息](./Tutorial-2.md)）。
在上面的示例中，对其进行了修改，以便将 `test.Rectangle` 的超类更改为类 `test.Point` 。
当最终调用 `CtClass` 中的 `writeFile()` 时，此更改将反应在原始类文件中。

`writeFile()` translates the `CtClass` object into a class file and writes it on a local disk.
Javassist also provides a method for directly obtaining the modified bytecode.  
To obtain the bytecode, call `toBytecode()`:

`writeFile()` 将 `CtClass` 对象转换为类文档，并将其写入本地磁盘。
Javassist 还提供了一种直接获取修改的字节码的方法。
要获取字节码，请调用 `toBytecode()` ：

```jshelllanguage
byte[] b = cc.toBytecode();
```

You can directly load the `CtClass` as well:

你也可以直接加载 `CtClass` ：

```jshelllanguage
Class clazz = cc.toClass();
```

`toClass()` requests the context class loader for the current thread to load the class file represented by the `CtClass`.  
It returns a `java.lang.Class` object representing the loaded class.
For more details, please see [this section below](./Tutorial-1.md#3.1%20).

`toClass()` 请求当前线程的上下文类加载器，以载入由 `CtClass` 表示的类文件。
它返回要给表示已加载类的 `java.lang.Class` 对象。
有关更多细节，请参阅[下面的此部分]()

#### Defining a new class

To define a new class from scratch, `makeClass()` must be called on a `ClassPool`.

要从头开始定义新类，必须在 `ClassPool` 上调用 `makeClass()` 。

```jshelllanguage
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.makeClass("Point");
```

This program defines a class `Point` including no members.
Member methods of `Point` can be created with factory methods declared in `CtNewMethod` and appended to `Point` with `addMethod()` in `CtClass`.

该程序定义一个不含任何成员的类 `Point` 。
`Point` 的成员方法可以使用 `CtNewMethod` 中声明的工厂方法创建，并在 `CtClass` 中使用 `addMethod()` 附加到 `Point` 。

`makeClass()` cannot create a new interface;
`makeInterface()` in `ClassPool` can do.
Member methods in an interface can be created with `abstractMethod()` in `CtNewMethod`.
Note that an interface method is an abstract method.

`makeClass()` 不能创建一个新的接口；
`ClassPool` 中的 `makeInterface()` 可以。
使用 `CtNewMethod` 的 `abstractMethod()` 可以创建接口中的成员方法。
注意，一个接口方法是一个抽象方法。


#### Frozen classes

If a `CtClass` object is converted into a class file by `writeFile()`, `toClass()`, or `toBytecode()`, Javassist freezes that `CtClass` object.  
Further modifications of that `CtClass` object are not permitted.  
This is for warning the developers when they attempt to modify a class file that has been already loaded since the JVM does not allow reloading a class.

A frozen `CtClass` can be defrost so that modifications of the class definition will be permitted.  
For example,

```jshelllanguage
CtClasss cc = ...;
    :
cc.writeFile();
cc.defrost();
cc.setSuperclass(...);    // OK since the class is not frozen.
```

After `defrost()` is called, the `CtClass` object can be modified again.

If `ClassPool.doPruning` is set to `true`, then Javassist prunes the data structure contained in a `CtClass` object when Javassist freezes that object.
To reduce memory consumption, pruning discards unnecessary attributes (`attribute_info` structures) in that object.
For example, `Code_attribute` structures (method bodies) are discarded.
Thus, after a `CtClass` object is pruned, the bytecode of a method is not accessible except method names, signatures, and annotations.
The pruned `CtClass` object cannot be defrost again.
The default value of `ClassPool.doPruning` is `false`.

To disallow pruning a particular `CtClass`, `stopPruning()` must be called on that object in advance:

```jshelllanguage
CtClasss cc = ...;
cc.stopPruning(true);
    :
cc.writeFile();    // convert to a class file.
// cc is not pruned.
```

The `CtClass` object `cc` is not pruned.
Thus it can be defrost after `writeFile()` is called.

> **Note:**
> 
> While debugging, you might want to temporarily stop pruning and freezing and write a modified class file to a disk drive.
> `debugWriteFile()` is a convenient method for that purpose.  
> It stops pruning, writes a class file, defrosts it, and turns pruning on again (if it was initially on).

#### Class search path

The default `ClassPool` returned by a static method `ClassPool.getDefault()` searches the same path that the underlying JVM (Java virtual machine) has.
If a program is running on a web application server such as JBoss and Tomcat, the `ClassPool` object may not be able to find user classes since such a web application server uses multiple class loaders as well as the system class loader.  
In that case, an additional class path must be registered to the `ClassPool`.  
Suppose that `pool` refers to a ClassPool object:

```jshelllanguage
pool.insertClassPath(new ClassClassPath(this.getClass()));
```

This statement registers the class path that was used for loading the class of the object that `this` refers to.
You can use any `Class` object as an argument instead of `this.getClass()`.  
The class path used for loading the class represented by that `Class` object is registered.

You can register a directory name as the class search path.
For example, the following code adds a directory `/usr/local/javalib` to the search path:

```jshelllanguage
ClassPool pool = ClassPool.getDefault();
pool.insertClassPath("/usr/local/javalib");
```

The search path that the users can add is not only a directory but also a URL:

```jshelllanguage
ClassPool pool = ClassPool.getDefault();
ClassPath cp = new URLClassPath("www.javassist.org", 80, "/java/", "org.javassist.");
pool.insertClassPath(cp);
```

This program adds "http://www.javassist.org:80/java/" to the class search path.  
This URL is used only for searching classes belonging to a package `org.javassist`.  
For example, to load a class `org.javassist.test.Main`, its class file will be obtained from:

```text
http://www.javassist.org:80/java/org/javassist/test/Main.class
```

Furthermore, you can directly give a byte array to a `ClassPool` object and construct a `CtClass` object from that array.  
To do this, use `ByteArrayClassPath`.  
For example,

```jshelllanguage
ClassPool cp = ClassPool.getDefault();
byte[] b = <em>a byte array</em>;
String name = <em>class name</em>;
cp.insertClassPath(new ByteArrayClassPath(name, b));
CtClass cc = cp.get(name);
```

The obtained `CtClass` object represents a class defined by the class file specified by `b`.
The `ClassPool` reads a class file from the given `ByteArrayClassPath` if `get()` is called and the class name given to `get()` is equal to one specified by `name`.

If you do not know the fully-qualified name of the class, then you can use `makeClass()` in `ClassPool`:

```jshelllanguage
ClassPool cp = ClassPool.getDefault();
InputStream ins = <em>an input stream for reading a class file</em>;
CtClass cc = cp.makeClass(ins);
```

`makeClass()` returns the `CtClass` object constructed from the given input stream.  
You can use `makeClass()` for eagerly feeding class files to the `ClassPool` object.  
This might improve performance if the search path includes a large jar file.  
Since a `ClassPool` object reads a class file on demand, it might repeatedly search the whole jar file for every class file.
`makeClass()` can be used for optimizing this search.
The `CtClass` constructed by `makeClass()` is kept in the `ClassPool` object and the class file is never read again.

The users can extend the class search path.  
They can define a new class implementing `ClassPath` interface and give an instance of that class to `insertClassPath()` in `ClassPool`.  
This allows a non-standard resource to be included in the search path.

----

## 2. ClassPool

A `ClassPool` object is a container of `CtClass` objects.  
Once a `CtClass` object is created, it is recorded in a `ClassPool` for ever.  
This is because a compiler may need to access the `CtClass` object later when it compiles source code that refers to the class represented by that `CtClass`.

For example, suppose that a new method `getter()` is added to a `CtClass` object representing `Point` class.  
Later, the program attempts to compile source code including a method call to `getter()` in `Point` and use the compiled code as the body of a method, which will be added to another class `Line`.  
If the `CtClass` object representing `Point` is lost, the compiler cannot compile the method call to `getter()`.  
Note that the original class definition does not include `getter()`.  
Therefore, to correctly compile such a method call, the `ClassPool` must contain all the instances of `CtClass` all the time of program execution.

#### Avoid out of memory

This specification of `ClassPool` may cause huge memory consumption if the number of `CtClass` objects becomes amazingly large (this rarely happens since Javassist tries to reduce memory consumption in [various ways]()).
To avoid this problem, you can explicitly remove an unnecessary `CtClass` object from the `ClassPool`.  
If you call `detach()` on a `CtClass` object, then that `CtClass` object is removed from the `ClassPool`.  
For example,

```jshelllanguage
CtClass cc = ... ;
cc.writeFile();
cc.detach();
```

You must not call any method on that `CtClass` object after `detach()` is called.
However, you can call `get()` on `ClassPool` to make a new instance of `CtClass` representing the same class.  
If you call `get()`, the `ClassPool` reads a class file again and newly creates a `CtClass` object, which is returned by `get()`.

Another idea is to occasionally replace a `ClassPool` with a new one and discard the old one.  
If an old `ClassPool` is garbage collected, the `CtClass` objects included in that `ClassPool` are also garbage collected.
To create a new instance of `ClassPool`, execute the following code snippet:

```jshelllanguage
ClassPool cp = new ClassPool(true);
// if needed, append an extra search path by appendClassPath()
```

This creates a `ClassPool` object that behaves as the default `ClassPool` returned by `ClassPool.getDefault()` does.
Note that `ClassPool.getDefault()` is a singleton factory method provided for convenience.  
It creates a `ClassPool` object in the same way shown above although it keeps a single instance of `ClassPool` and reuses it.
A `ClassPool` object returned by `getDefault()` does not have a special role.  
`getDefault()` is a convenience method.

Note that `new ClassPool(true)` is a convenient constructor, which constructs a `ClassPool` object and appends the system search path to it.  
Calling that constructor is equivalent to the following code:

```jshelllanguage
ClassPool cp = new ClassPool();
cp.appendSystemPath();  // or append another path by appendClassPath()
```

#### Cascaded ClassPools

_If a program is running on a web application server, creating multiple instances of `ClassPool` might be necessary;
an instance of `ClassPool` should be created for each class loader (i.e. container).
The program should create a `ClassPool` object by not calling `getDefault()` but a constructor of `ClassPool`.

Multiple `ClassPool` objects can be cascaded like `java.lang.ClassLoader`.  
For example,

```jshelllanguage
ClassPool parent = ClassPool.getDefault();
ClassPool child = new ClassPool(parent);
child.insertClassPath("./classes");
```

If `child.get()` is called, the child `ClassPool` first delegates to the parent `ClassPool`.  
If the parent `ClassPool` fails to find a class file, then the child `ClassPool` attempts to find a class file under the `./classes` directory.

If `child.childFirstLookup` is true, the child `ClassPool` attempts to find a class file before delegating to the parent `ClassPool`.  
For example,

```jshelllanguage
ClassPool parent = ClassPool.getDefault();
ClassPool child = new ClassPool(parent);
child.appendSystemPath();         // the same class path as the default one.
child.childFirstLookup = true;    // changes the behavior of the child.
```

#### Changing a class name for defining a new class

A new class can be defined as a copy of an existing class.
The program below does that:

```jshelllanguage
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("Point");
cc.setName("Pair");
```

This program first obtains the `CtClass` object for class `Point`.  
Then it calls `setName()` to give a new name `Pair` to that `CtClass` object.
After this call, all occurrences of the class name in the class definition represented by that `CtClass` object are changed from `Point` to `Pair`.  
The other part of the class definition does not change.

Note that `setName()` in `CtClass` changes a record in the `ClassPool` object.  
From the implementation viewpoint, a `ClassPool` object is a hash table of `CtClass` objects.  
`setName()` changes the key associated to the `CtClass` object in the hash table.  
The key is changed from the original class name to the new class name.

Therefore, if `get("Point")` is later called on the `ClassPool` object again, then it never returns the `CtClass` object that the variable `cc` refers to.
The `ClassPool` object reads a class file `Point.class` again and it constructs a new `CtClass` object for class `Point`.
This is because the `CtClass` object associated with the name `Point` does not exist any more.
See the followings:

```jshelllanguage
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("Point");
CtClass cc1 = pool.get("Point");   // cc1 is identical to cc.
cc.setName("Pair");
CtClass cc2 = pool.get("Pair");    // cc2 is identical to cc.
CtClass cc3 = pool.get("Point");   // cc3 is not identical to cc.
```

`cc1` and `cc2` refer to the same instance of `CtClass` that `cc` does whereas `cc3` does not.  
Note that, after `cc.setName("Pair")` is executed, the `CtClass` object that `cc` and `cc1` refer to represents the `Pair` class.

The `ClassPool` object is used to maintain one-to-one mapping between classes and `CtClass` objects. 
Javassist never allows two distinct `CtClass` objects to represent the same class unless two independent `ClassPool` are created.
This is a significant feature for consistent program transformation.

To create another copy of the default instance of `ClassPool`, which is returned by `ClassPool.getDefault()`, execute the following code snippet (this code was already [shown above]()):

```jshelllanguage
ClassPool cp = new ClassPool(true);
```

If you have two `ClassPool` objects, then you can obtain, from each `ClassPool`, a distinct `CtClass` object representing the same class file.  
You can differently modify these `CtClass` objects to generate different versions of the class.

#### Renaming a frozen class for defining a new class

Once a `CtClass` object is converted into a class file by `writeFile()` or `toBytecode()`, Javassist rejects further modifications of that `CtClass` object.
Hence, after the `CtClass` object representing `Point` class is converted into a class file, you cannot define <code>`Pair`</code> class as a copy of <code>`Point`</code> since executing <code>`setName()`</code> on <code>`Point`</code> is rejected.
The following code snippet is wrong:

```jshelllanguage
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("Point");
cc.writeFile();
cc.setName("Pair");    // wrong since writeFile() has been called.
```

<p>To avoid this restriction, you should call <code>getAndRename()</code>
in <code>ClassPool</code>.  For example,

```java
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("Point");
cc.writeFile();
CtClass cc2 = pool.getAndRename("Point", "Pair");
```

<p>If <code>getAndRename()</code> is called, the <code>ClassPool</code>
first reads <code>Point.class</code> for creating a new <code>CtClass</code>
object representing <code>Point</code> class.  However, it renames that
<code>CtClass</code> object from <code>Point</code> to <code>Pair</code> before
it records that <code>CtClass</code> object in a hash table.
Thus <code>getAndRename()</code>
can be executed after <code>writeFile()</code> or <code>toBytecode()</code>
is called on the the <code>CtClass</code> object representing <code>Point</code>
class.

<p><br>

<a name="load">
<h2>3. Class loader</h2>

<p>If what classes must be modified is known in advance,
the easiest way for modifying the classes is as follows:

<ul><li>1. Get a <code>CtClass</code> object by calling
        <code>ClassPool.get()</code>,
    <li>2. Modify it, and
    <li>3. Call <code>writeFile()</code> or <code>toBytecode()</code>
           on that <code>CtClass</code> object to obtain a modified class file.
</ul>

<p>If whether a class is modified or not is determined at load time,
the users must make Javassist collaborate with a class loader.
Javassist can be used with a class loader so that bytecode can be
modified at load time.  The users of Javassist can define their own
version of class loader but they can also use a class loader provided
by Javassist.


<p><br>

<a name="toclass">
<h3>3.1 The <code>toClass</code> method in <code>CtClass</code></h3>
</a>

<p>The <code>CtClass</code> provides a convenience method
<code>toClass()</code>, which requests the context class loader for
the current thread to load the class represented by the <code>CtClass</code>
object.  To call this method, the caller must have appropriate permission;
otherwise, a <code>SecurityException</code> may be thrown.

<p>The following program shows how to use <code>toClass()</code>:

```java
public class Hello {
    public void say() {
        System.out.println("Hello");
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("Hello");
        CtMethod m = cc.getDeclaredMethod("say");
        m.insertBefore("{ System.out.println(\"Hello.say():\"); }");
        Class c = cc.toClass();
        Hello h = (Hello)c.newInstance();
        h.say();
    }
}
```

<p><code>Test.main()</code> inserts a call to <code>println()</code>
in the method body of <code>say()</code> in <code>Hello</code>.  Then
it constructs an instance of the modified <code>Hello</code> class
and calls <code>say()</code> on that instance.

<p>Note that the program above depends on the fact that the
<code>Hello</code> class is never loaded before <code>toClass()</code>
is invoked.  If not, the JVM would load the original
<code>Hello</code> class before <code>toClass()</code> requests to
load the modified <code>Hello</code> class.  Hence loading the
modified <code>Hello</code> class would be failed
(<code>LinkageError</code> is thrown).  For example, if
<code>main()</code> in <code>Test</code> is something like this:

```java
public static void main(String[] args) throws Exception {
    Hello orig = new Hello();
    ClassPool cp = ClassPool.getDefault();
    CtClass cc = cp.get("Hello");
        :
}
```

<p>then the original <code>Hello</code> class is loaded at the first
line of <code>main</code> and the call to <code>toClass()</code>
throws an exception since the class loader cannot load two different
versions of the <code>Hello</code> class at the same time.

<p><em>If the program is running on some application server such as
JBoss and Tomcat,</em> the context class loader used by
<code>toClass()</code> might be inappropriate.  In this case, you
would see an unexpected <code>ClassCastException</code>.  To avoid
this exception, you must explicitly give an appropriate class loader
to <code>toClass()</code>.  For example, if <code>bean</code> is your
session bean object, then the following code:

```java
CtClass cc = ...;
Class c = cc.toClass(bean.getClass().getClassLoader());
```

<p>would work.  You should give <code>toClass()</code> the class loader
that has loaded your program (in the above example, the class of
the <code>bean</code> object).

<p><code>toClass()</code> is provided for convenience.  If you need
more complex functionality, you should write your own class loader.

<p><br>

<h3>3.2 Class loading in Java</h3>

<p>In Java, multiple class loaders can coexist and
each class loader creates its own name space.
Different class loaders can load different class files with the
same class name.  The loaded two classes are regarded as different
ones.  This feature enables us to run multiple application programs
on a single JVM even if these programs include different classes
with the same name.

<ul>
<b>Note:</b> The JVM does not allow dynamically reloading a class.
Once a class loader loads a class, it cannot reload a modified
version of that class during runtime.  Thus, you cannot alter
the definition of a class after the JVM loads it.
However, the JPDA (Java Platform Debugger Architecture) provides
limited ability for reloading a class.
See <a href="#hotswap">Section 3.6</a>.
</ul>

<p>If the same class file is loaded by two distinct class loaders,
the JVM makes two distinct classes with the same name and definition.
The two classes are regarded as different ones.
Since the two classes are not identical, an instance of one class is
not assignable to a variable of the other class.  The cast operation
between the two classes fails
and throws a <em><code>ClassCastException</code></em>.

<p>For example, the following code snippet throws an exception:

```java
MyClassLoader myLoader = new MyClassLoader();
Class clazz = myLoader.loadClass("Box");
Object obj = clazz.newInstance();
Box b = (Box)obj;    // this always throws ClassCastException.
```

<p>
The <code>Box</code> class is loaded by two class loaders.
Suppose that a class loader CL loads a class including this code snippet.
Since this code snippet refers to <code>MyClassLoader</code>,
<code>Class</code>, <code>Object</code>, and <code>Box</code>,
CL also loads these classes (unless it delegates to another class loader).
Hence the type of the variable <code>b</code> is the <code>Box</code>
class loaded by CL.
On the other hand, <code>myLoader</code> also loads the <code>Box</code>
class.  The object <code>obj</code> is an instance of
the <code>Box</code> class loaded by <code>myLoader</code>.
Therefore, the last statement always throws a
<code>ClassCastException</code> since the class of <code>obj</code> is
a different verison of the <code>Box</code> class from one used as the
type of the variable <code>b</code>.

<p>Multiple class loaders form a tree structure.
Each class loader except the bootstrap loader has a
parent class loader, which has normally loaded the class of that child
class loader.  Since the request to load a class can be delegated along this
hierarchy of class loaders, a class may be loaded by a class loader that
you do not request the class loading.
Therefore, the class loader that has been requested to load a class C
may be different from the loader that actually loads the class C.
For distinction, we call the former loader <em>the initiator of C</em>
and we call the latter loader <em>the real loader of C</em>.

<p>
Furthermore, if a class loader CL requested to load a class C
(the initiator of C) delegates
to the parent class loader PL, then the class loader CL is never requested
to load any classes referred to in the definition of the class C.
CL is not the initiator of those classes.
Instead, the parent class loader PL becomes their initiators
and it is requested to load them.
<em>The classes that the definition of a class C referes to are loaded by
the real loader of C.</em>

<p>To understand this behavior, let's consider the following example.

```java
public class Point {    // loaded by PL
    private int x, y;
    public int getX() { return x; }
        :
}

public class Box {      // the initiator is L but the real loader is PL
    private Point upperLeft, size;
    public int getBaseX() { return upperLeft.x; }
        :
}

public class Window {    // loaded by a class loader L
    private Box box;
    public int getBaseX() { return box.getBaseX(); }
}
```

<p>Suppose that a class <code>Window</code> is loaded by a class loader L.
Both the initiator and the real loader of <code>Window</code> are L.
Since the definition of <code>Window</code> refers to <code>Box</code>,
the JVM will request L to load <code>Box</code>.
Here, suppose that L delegates this task to the parent class loader PL.
The initiator of <code>Box</code> is L but the real loader is PL.
In this case, the initiator of <code>Point</code> is not L but PL
since it is the same as the real loader of <code>Box</code>.
Thus L is never requested to load <code>Point</code>.

<p>Next, let's consider a slightly modified example.

```java
public class Point {
    private int x, y;
    public int getX() { return x; }
        :
}

public class Box {      // the initiator is L but the real loader is PL
    private Point upperLeft, size;
    public Point getSize() { return size; }
        :
}

public class Window {    // loaded by a class loader L
    private Box box;
    public boolean widthIs(int w) {
        Point p = box.getSize();
        return w == p.getX();
    }
}
```

<p>Now, the definition of <code>Window</code> also refers to
<code>Point</code>.  In this case, the class loader L must
also delegate to PL if it is requested to load <code>Point</code>.
<em>You must avoid having two class loaders doubly load the same
class.</em>  One of the two loaders must delegate to
the other.

<p>
If L does not delegate to PL when <code>Point</code>
is loaded, <code>widthIs()</code> would throw a ClassCastException.
Since the real loader of <code>Box</code> is PL,
<code>Point</code> referred to in <code>Box</code> is also loaded by PL.
Therefore, the resulting value of <code>getSize()</code>
is an instance of <code>Point</code> loaded by PL
whereas the type of the variable <code>p</code> in <code>widthIs()</code>
is <code>Point</code> loaded by L.
The JVM regards them as distinct types and thus it throws an exception
because of type mismatch.

<p>This behavior is somewhat inconvenient but necessary.
If the following statement:

```java
Point p = box.getSize();
```

<p>did not throw an exception,
then the programmer of <code>Window</code> could break the encapsulation
of <code>Point</code> objects.
For example, the field <code>x</code>
is private in <code>Point</code> loaded by PL.
However, the <code>Window</code> class could
directly access the value of <code>x</code>
if L loads <code>Point</code> with the following definition:

```java
public class Point {
    public int x, y;    // not private
    public int getX() { return x; }
        :
}
```

<p>
For more details of class loaders in Java, the following paper would
be helpful:

<ul>Sheng Liang and Gilad Bracha,
"Dynamic Class Loading in the Java Virtual Machine",
<br><i>ACM OOPSLA'98</i>, pp.36-44, 1998.</ul>

<p><br>

<h3>3.3 Using <code>javassist.Loader</code></h3>

<p>Javassist provides a class loader
<code>javassist.Loader</code>.  This class loader uses a
<code>javassist.ClassPool</code> object for reading a class file.

<p>For example, <code>javassist.Loader</code> can be used for loading
a particular class modified with Javassist.

```java
import javassist.*;
import test.Rectangle;

public class Main {
  public static void main(String[] args) throws Throwable {
    ClassPool pool = ClassPool.getDefault();
    Loader cl = new Loader(pool);

    CtClass ct = pool.get("test.Rectangle");
    ct.setSuperclass(pool.get("test.Point"));

    Class c = cl.loadClass("test.Rectangle");
    Object rect = c.newInstance();
        :
  }
}
```

<p>This program modifies a class <code>test.Rectangle</code>.  The
superclass of <code>test.Rectangle</code> is set to a
<code>test.Point</code> class.  Then this program loads the modified
class, and creates a new instance of the
<code>test.Rectangle</code> class.

<p>If the users want to modify a class on demand when it is loaded,
the users can add an event listener to a <code>javassist.Loader</code>.
The added event listener is
notified when the class loader loads a class.
The event-listener class must implement the following interface:

```java
public interface Translator {
    public void start(ClassPool pool)
        throws NotFoundException, CannotCompileException;
    public void onLoad(ClassPool pool, String classname)
        throws NotFoundException, CannotCompileException;
}
```

<p>The method <code>start()</code> is called when this event listener
is added to a <code>javassist.Loader</code> object by
<code>addTranslator()</code> in <code>javassist.Loader</code>.  The
method <code>onLoad()</code> is called before
<code>javassist.Loader</code> loads a class.  <code>onLoad()</code>
can modify the definition of the loaded class.

<p>For example, the following event listener changes all classes
to public classes just before they are loaded.

```java
public class MyTranslator implements Translator {
    void start(ClassPool pool)
        throws NotFoundException, CannotCompileException {}
    void onLoad(ClassPool pool, String classname)
        throws NotFoundException, CannotCompileException
    {
        CtClass cc = pool.get(classname);
        cc.setModifiers(Modifier.PUBLIC);
    }
}
```

<p>Note that <code>onLoad()</code> does not have to call
<code>toBytecode()</code> or <code>writeFile()</code> since
<code>javassist.Loader</code> calls these methods to obtain a class
file.

<p>To run an application class <code>MyApp</code> with a
<code>MyTranslator</code> object, write a main class as following:

```java
import javassist.*;

public class Main2 {
  public static void main(String[] args) throws Throwable {
     Translator t = new MyTranslator();
     ClassPool pool = ClassPool.getDefault();
     Loader cl = new Loader();
     cl.addTranslator(pool, t);
     cl.run("MyApp", args);
  }
}
```

<p>To run this program, do:

```shell
% java Main2 arg1 arg2...
```

<p>The class <code>MyApp</code> and the other application classes
are translated by <code>MyTranslator</code>.

<p>Note that <em>application</em> classes like <code>MyApp</code> cannot
access the <em>loader</em> classes such as <code>Main2</code>,
<code>MyTranslator</code>, and <code>ClassPool</code> because they
are loaded by different loaders.  The application classes are loaded
by <code>javassist.Loader</code> whereas the loader classes such as
<code>Main2</code> are by the default Java class loader.

<p><code>javassist.Loader</code> searches for classes in a different
order from <code>java.lang.ClassLoader</code>.
<code>ClassLoader</code> first delegates the loading operations to
the parent class loader and then attempts to load the classes
only if the parent class loader cannot find them.
On the other hand,
<code>javassist.Loader</code> attempts
to load the classes before delegating to the parent class loader.
It delegates only if:

<ul><li>the classes are not found by calling <code>get()</code> on
a <code>ClassPool</code> object, or

<p><li>the classes have been specified by using
<code>delegateLoadingOf()</code>
to be loaded by the parent class loader.
</ul>

<p>This search order allows loading modified classes by Javassist.
However, it delegates to the parent class loader if it fails
to find modified classes for some reason.  Once a class is loaded by
the parent class loader, the other classes referred to in that class will be
also loaded by the parent class loader and thus they are never modified.
Recall that all the classes referred to in a class C are loaded by the
real loader of C.
<em>If your program fails to load a modified class,</em> you should
make sure whether all the classes using that class have been loaded by
<code>javassist.Loader</code>.

<p><br>

<h3>3.4 Writing a class loader</h3>

<p>A simple class loader using Javassist is as follows:

```java
import javassist.*;

public class SampleLoader extends ClassLoader {
    /* Call MyApp.main().
     */
    public static void main(String[] args) throws Throwable {
        SampleLoader s = new SampleLoader();
        Class c = s.loadClass("MyApp");
        c.getDeclaredMethod("main", new Class[] { String[].class })
         .invoke(null, new Object[] { args });
    }

    private ClassPool pool;

    public SampleLoader() throws NotFoundException {
        pool = new ClassPool();
        pool.insertClassPath("./class"); // <em>MyApp.class must be there.</em>
    }

    /* Finds a specified class.
     * The bytecode for that class can be modified.
     */
    protected Class findClass(String name) throws ClassNotFoundException {
        try {
            CtClass cc = pool.get(name);
            // <em>modify the CtClass object here</em>
            byte[] b = cc.toBytecode();
            return defineClass(name, b, 0, b.length);
        } catch (NotFoundException e) {
            throw new ClassNotFoundException();
        } catch (IOException e) {
            throw new ClassNotFoundException();
        } catch (CannotCompileException e) {
            throw new ClassNotFoundException();
        }
    }
}
```

<p>The class <code>MyApp</code> is an application program.
To execute this program, first put the class file under the
<code>./class</code> directory, which must <em>not</em> be included
in the class search path.  Otherwise, <code>MyApp.class</code> would
be loaded by the default system class loader, which is the parent
loader of <code>SampleLoader</code>.
The directory name <code>./class</code> is specified by
<code>insertClassPath()</code> in the constructor.
You can choose a different name instead of <code>./class</code> if you want.
Then do as follows:

<ul><code>% java SampleLoader</code></ul>

<p>The class loader loads the class <code>MyApp</code>
(<code>./class/MyApp.class</code>) and calls
<code>MyApp.main()</code> with the command line parameters.

<p>This is the simplest way of using Javassist.  However, if you write
a more complex class loader, you may need detailed knowledge of
Java's class loading mechanism.  For example, the program above puts the
<code>MyApp</code> class in a name space separated from the name space
that the class <code>SampleLoader</code> belongs to because the two
classes are loaded by different class loaders.
Hence, the
<code>MyApp</code> class cannot directly access the class
<code>SampleLoader</code>.

<p><br>

<h3>3.5 Modifying a system class</h3>

<p>The system classes like <code>java.lang.String</code> cannot be
loaded by a class loader other than the system class loader.
Therefore, <code>SampleLoader</code> or <code>javassist.Loader</code>
shown above cannot modify the system classes at loading time.

<p>If your application needs to do that, the system classes must be
<em>statically</em> modified.  For example, the following program
adds a new field <code>hiddenValue</code> to <code>java.lang.String</code>:

```java
ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("java.lang.String");
CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
f.setModifiers(Modifier.PUBLIC);
cc.addField(f);
cc.writeFile(".");
```

<p>This program produces a file <code>"./java/lang/String.class"</code>.

<p>To run your program <code>MyApp</code>
with this modified <code>String</code> class, do as follows:

```shell
% java -Xbootclasspath/p:. MyApp arg1 arg2...
```

<p>Suppose that the definition of <code>MyApp</code> is as follows:

```java
public class MyApp {
    public static void main(String[] args) throws Exception {
        System.out.println(String.class.getField("hiddenValue").getName());
    }
}
```

<p>If the modified <code>String</code> class is correctly loaded,
<code>MyApp</code> prints <code>hiddenValue</code>.

<p><i>Note: Applications that use this technique for the purpose of
overriding a system class in <code>rt.jar</code> should not be
deployed as doing so would contravene the Java 2 Runtime Environment
binary code license.</i>

<p><br>

<a name="hotswap">
<h3>3.6 Reloading a class at runtime</h3></a>

<p>If the JVM is launched with the JPDA (Java Platform Debugger
Architecture) enabled, a class is dynamically reloadable.  After the
JVM loads a class, the old version of the class definition can be
unloaded and a new one can be reloaded again.  That is, the definition
of that class can be dynamically modified during runtime.  However,
the new class definition must be somewhat compatible to the old one.
<em>The JVM does not allow schema changes between the two versions.</em>
They have the same set of methods and fields.

<p>Javassist provides a convenient class for reloading a class at runtime.
For more information, see the API documentation of
<code>javassist.tools.HotSwapper</code>.