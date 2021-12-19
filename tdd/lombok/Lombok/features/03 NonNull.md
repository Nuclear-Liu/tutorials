# @NonNull

* `@NonNull` was introduced in lombok v0.11.10.

## Overview


You can use `@NonNull` on a record component, or a parameter of a method or constructor. 
This will cause to lombok generate a null-check statement for you.


您可以在记录组件、方法或构造函数的参数上使用 `@NonNull` 。
这将导致 lombok 为您生成一个空检查语句。


Lombok has always treated various annotations generally named `@NonNull` on a field as a signal to generate a null-check if lombok generates an entire method or constructor for you, via for example `@Data`. 
However, using lombok's own `@lombok.NonNull` on a parameter or record component results in the insertion of the null-check at the top of that method.


Lombok 一直将字段上通常名为 `@NonNull` 的各种注解视为信号，以生成空检查，如果 lombok 为您生成整个方法或构造函数，例如通过 `@Data` 。
但是，在参数或记录组件上使用 lombok 自己的 `@lombok.NonNull` 会导致在该方法的顶部插入空检查。


The null-check looks like `if (param == null) throw new NullPointerException("param is marked @NonNull but is null");` and will be inserted at the very top of your method. 
For constructors, the null-check will be inserted immediately following any explicit `this()` or `super()` calls. 
For record components, the null-check will be inserted in the 'compact constructor' (the one that has no argument list at all), which will be generated if you have no constructor. 
If you have written out the record constructor in long form (with parameters matching your components exactly), then nothing happens - you'd have to annotate the parameters of this long-form constructor instead.


空检查看起来像 `if (param == null) throw new NullPointerException("param is marked @NonNull but is null");` 并且将被插入到你的方法的最顶部。
对于构造函数，将在任何显式的 `this()` 或 `super()` 调用之后立即插入空检查。
对于记录组件，空检查将插入到 'compact constructor' （根本没有参数列表的构造函数）中，如果您没有构造函数，则会生成该构造函数。
如果您已经写出长格式的记录构造函数（参数与您的组件完全匹配），那么什么也不会发生——您必须改为注释这个长格式构造函数的参数。


If a null-check is already present at the top, no additional null-check will be generated.


如果顶部已经存在空检查，则不会生成额外的空检查。

