# Cleanup

* Scope
  * `ElementType.LOCAL_VARIABLE` _局部变量声明_


Automatic resource management: Call your `close()` methods safely with no hassle.


自动资源管理：安全地调用你的 `close()` 方法，没有麻烦。


## Overview


You can use `@Cleanup` to ensure a given resource is automatically cleaned up before the code execution path exits your current scope. 
You do this by annotating any local variable declaration with the `@Cleanup` annotation like so:
`@Cleanup InputStream in = new FileInputStream("some/file");`
As a result, at the end of the scope you're in, `in.close()` is called. 
This call is guaranteed to run by way of a `try`/`finally` construct. 
Look at the example below to see how this works.


您可以使用 `@Cleanup` 来确保在代码执行路径退出当前范围之前自动清理给定资源。
您可以通过使用 `@Cleanup` 注释来注释任何局部变量声明来做到这一点，如下所示：
`@Cleanup InputStream in = new FileInputStream("some/file");`
结果，在您所在范围的末尾，调用了 `in.close()` 。
这个调用保证通过 `try` / `finally` 结构运行。
查看下面的示例以了解其工作原理。


If the type of object you'd like to cleanup does not have a `close()` method, but some other no-argument method, you can specify the name of this method like so:
`@Cleanup("dispose") org.eclipse.swt.widgets.CoolBar bar = new CoolBar(parent, 0);`
By default, the cleanup method is presumed to be `close()`. 
A cleanup method that takes 1 or more arguments cannot be called via `@Cleanup`.


如果你想要清理的对象类型没有 `close()` 方法，而是其他一些无参数的方法，你可以像这样指定这个方法的名称：
`@Cleanup("dispose") org.eclipse.swt.widgets.CoolBar bar = new CoolBar(parent, 0);`
默认情况下，清理方法假定为 `close()` 。
不能通过 `@Cleanup` 调用带有 1 个或多个参数的清理方法。


## Supported configuration keys:


`lombok.cleanup.flagUsage` = [`warning` | `error`] (default: not set)


`lombok.cleanup.flagUsage` = [`warning` | `error`] (默认值：未设置)


Lombok will flag any usage of `@Cleanup` as a warning or error if configured.


如果已配置，Lombok 会将任何使用 `@Cleanup` 的情况标记为警告或错误。


## Small print


In the finally block, the cleanup method is only called if the given resource is not `null`. 
However, if you use delombok on the code, a call to `lombok.Lombok.preventNullAnalysis(Object o)` is inserted to prevent warnings if static code analysis could determine that a null-check would not be needed. 
Compilation with `lombok.jar` on the classpath removes that method call, so there is no runtime dependency.


在 finally 块中，仅当给定资源不为 null 时才会调用清理方法。
但是，如果您在代码上使用 delombok ，则会插入对 `lombok.Lombok.preventNullAnalysis(Object o)` 的调用，以防止在静态代码分析可以确定不需要空检查时发出警告。
在类路径上使用 `lombok.jar` 进行编译会删除该方法调用，因此没有运行时依赖性。


If your code throws an exception, and the cleanup method call that is then triggered also throws an exception, then the original exception is hidden by the exception thrown by the cleanup call. 
You should not rely on this 'feature'. 
Preferably, lombok would like to generate code so that, if the main body has thrown an exception, any exception thrown by the close call is silently swallowed (but if the main body exited in any other way, exceptions by the close call will not be swallowed). 
The authors of lombok do not currently know of a feasible way to implement this scheme, but if java updates allow it, or we find a way, we'll fix it.


如果您的代码抛出异常，并且随后触发的清理方法调用也抛出异常，那么原始异常将被清理调用抛出的异常隐藏。
您不应依赖此“功能”。
最好，lombok 希望生成代码，这样，如果主体抛出异常，则 close 调用抛出的任何异常都会被静默吞下（但如果主体以任何其他方式退出，则 close 调用引发的异常将不会被吞了）。
lombok的作者目前不知道实现这个方案的可行方法，但是如果java更新允许，或者我们找到方法，我们会修复它。


You do still need to handle any exception that the cleanup method can generate!


您仍然需要处理清理方法可以生成的任何异常！
