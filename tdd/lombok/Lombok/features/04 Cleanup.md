# Cleanup


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
