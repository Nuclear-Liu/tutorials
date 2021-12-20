# `@Synchronized`

## Overview _概述_


`@Synchronized` is a safer variant of the `synchronized` method modifier. 
Like synchronized, the annotation can be used on static and instance methods only. 
It operates similarly to the `synchronized` keyword, but it locks on different objects. 
The keyword locks on `this`, but the annotation locks on a field named `$lock`, which is private.
If the field does not exist, it is created for you. 
If you annotate a `static` method, the annotation locks on a static field named `$LOCK` instead.


`@Synchronized` 是 `synchronized` 方法修饰符的一个更安全的变体。
与同步一样，注解只能用于静态和实例方法。
它的操作类似于 `synchronized` 关键字，但它锁定不同的对象。
关键字锁定 `this` ，但注释锁定名为 `$lock` 的字段，该字段是私有的。
如果该字段不存在，则会为您创建。
如果你注解一个 `static` 方法，注解会锁定一个名为 `$LOCK` 的静态字段。


If you want, you can create these locks yourself. 
The `$lock` and `$LOCK` fields will of course not be generated if you already created them yourself. 
You can also choose to lock on another field, by specifying it as parameter to the `@Synchronized` annotation. 
In this usage variant, the fields will not be created automatically, and you must explicitly create them yourself, or an error will be emitted.


如果需要，您可以自己创建这些锁。
如果您自己创建了 `$lock` 和 `LOCK `$LOCK` ` 字段，当然不会生成它们。
您还可以选择锁定另一个字段，方法是将其指定为 `@Synchronized` 注释的参数。
在此用法变体中，字段不会自动创建，您必须自己显式创建它们，否则将发出错误。


Locking on `this` or your own class object can have unfortunate side-effects, as other code not under your control can lock on these objects as well, which can cause race conditions and other nasty threading-related bugs.


锁定“this”或您自己的类对象可能会产生不幸的副作用，因为不受您控制的其他代码也可以锁定这些对象，这可能会导致竞争条件和其他与线程相关的严重错误。

