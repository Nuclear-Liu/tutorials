# `@Getter(lazy=true)`

## Overview


You can let lombok generate a getter which will calculate a value once, the first time this getter is called, and cache it from then on. 
This can be useful if calculating the value takes a lot of CPU, or the value takes a lot of memory. 
To use this feature, create a `private final` variable, initialize it with the expression that's expensive to run, and annotate your field with `@Getter(lazy=true)`. 
The field will be hidden from the rest of your code, and the expression will be evaluated no more than once, when the getter is first called. 
There are no magic marker values (i.e. even if the result of your expensive calculation is `null`, the result is cached) and your expensive calculation need not be thread-safe, as lombok takes care of locking.


你可以让 lombok 生成一个 getter，它会在第一次调用这个 getter 时计算一个值，然后缓存它。
如果计算该值需要大量 CPU 或该值需要大量内存，这会很有用。
要使用此功能，请创建一个 `private final` 变量，使用运行成本高的表达式对其进行初始化，并使用 `@Getter(lazy=true)` 注释您的字段。
该字段将对您的其余代码隐藏，并且在首次调用 getter 时，该表达式的计算不会超过一次。
没有魔法标记值（即，即使您的昂贵计算的结果是 `null`，结果也会被缓存）并且您的昂贵计算不需要是线程安全的，因为 lombok 负责锁定。


If the initialization expression is complex, or contains generics, we recommend moving the code to a private (if possible static) method, and call that instead.


如果初始化表达式很复杂，或者包含泛型，我们建议将代码移动到一个 `private` （如果可能的话 `static` ）方法，并调用它。

