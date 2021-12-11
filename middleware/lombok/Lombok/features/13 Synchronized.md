# @Synchronized

## Overview

`@Synchronized` is a safer variant of the `synchronized` method modifier. 
Like synchronized, the annotation can be used on static and instance methods only. 
It operates similarly to the `synchronized` keyword, but it locks on different objects. 
The keyword locks on `this`, but the annotation locks on a field named `$lock`, which is private.
If the field does not exist, it is created for you. 
If you annotate a `static` method, the annotation locks on a static field named `$LOCK` instead.

If you want, you can create these locks yourself. 
The `$lock` and `$LOCK` fields will of course not be generated if you already created them yourself. 
You can also choose to lock on another field, by specifying it as parameter to the `@Synchronized` annotation. 
In this usage variant, the fields will not be created automatically, and you must explicitly create them yourself, or an error will be emitted.

Locking on `this` or your own class object can have unfortunate side-effects, as other code not under your control can lock on these objects as well, which can cause race conditions and other nasty threading-related bugs.