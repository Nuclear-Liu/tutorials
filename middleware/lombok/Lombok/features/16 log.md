# `@Log (and friends)`

## Overview


You put the variant of `@Log` on your class (whichever one applies to the logging system you use); you then have a static final `log` field, initialized as is the commonly prescribed way for the logging framework you use, which you can then use to write log statements.


你把 `@Log` 的变体放在你的班级上（无论哪个适用于你使用的日志系统）；然后你有一个静态的最终 `log` 字段，按照你使用的日志框架的常用规定方式进行初始化，然后你可以用它来编写日志语句。


There are several choices available:


有多种选择可供选择：


* `@CommonsLog`

  Creates `private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(LogExample.class);`

* `@Flogger`

  Creates `private static final com.google.common.flogger.FluentLogger log = com.google.common.flogger.FluentLogger.forEnclosingClass();`

* `@JBossLog`

  Creates `private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(LogExample.class);`

* `@Log`

  Creates `private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(LogExample.class.getName());`

* `@Log4j`

  Creates `private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LogExample.class);`

* `@Log4j2`

  Creates `private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(LogExample.class);`

* `@Slf4j`

  Creates `private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);`

* `@XSlf4j`

  Creates `private static final org.slf4j.ext.XLogger log = org.slf4j.ext.XLoggerFactory.getXLogger(LogExample.class);`

* `@CustomLog`

  Creates `private static final com.foo.your.Logger log = com.foo.your.LoggerFactory.createYourLogger(LogExample.class);`


This option requires that you add a configuration to your `lombok.config` file to specify what `@CustomLog` should do.


这个选项要求你在你的 `lombok.config` 文件中添加一个配置来指定 `@CustomLog` 应该做什么。


For example:`lombok.log.custom.declaration = com.foo.your.Logger com.foo.your.LoggerFactory.createYourLog(TYPE)(TOPIC)` which would produce the above statement. 
First comes a type which is the type of your `logger`, then a space, then the type of your `logger` factory, then a dot, then the name of the `logger` factory method, and then 1 or 2 parameter definitions; at most one definition with `TOPIC` and at most one without `TOPIC`. 
Each parameter definition is specified as a parenthesised comma-separated list of parameter kinds. 
The options are: `TYPE` (passes this `@Log` decorated type, as a class), `NAME` (passes this `@Log` decorated type's fully qualified name), `TOPIC` (passes the explicitly chosen topic string set on the `@CustomLog` annotation), and `NULL` (passes `null`).


例如： `lombok.log.custom.declaration = com.foo.your.Logger com.foo.your.LoggerFactory.createYourLog(TYPE)(TOPIC)` 这将产生上述语句。
首先是一个类型，它是你的 `logger` 的类型，然后是一个空格，然后是你的 `logger` 工厂的类型，然后是一个点，然后是 `logger` 工厂方法的名称，然后是 1 或 2 个参数定义;最多一个定义有 `TOPIC` ，最多一个没有 `TOPIC` 。
每个参数定义都指定为括号中逗号分隔的参数种类列表
选项是： `TYPE` （传递这个 `@Log` 修饰类型，作为一个类）， `NAME` （传递这个 `@Log` 修饰类型的完全限定名称）， `TOPIC` （传递明确选择的主题字符串集在 `@CustomLog` 注释上）和 `NULL` （传递 `null` ）。


The logger type is optional; if it is omitted, the logger factory type is used. 
(So, if your logger class has a static method that creates loggers, you can shorten your logger definition).


记录器类型是可选的；如果省略，则使用记录器工厂类型。
（因此，如果您的记录器类具有创建记录器的静态方法，您可以缩短记录器定义）。


Please contact us if there is a public, open source, somewhat commonly used logging framework that we don't yet have an explicit annotation for. 
The primary purpose of `@CustomLog` is to support your in-house, private logging frameworks.


如果有一个公共的、开源的、有些常用的日志框架，但我们还没有明确的注释，请联系我们。
`@CustomLog` 的主要目的是支持您的内部私有日志框架。


By default, the topic (or name) of the logger will be the (name of) the class annotated with the `@Log` annotation. 
This can be customised by specifying the `topic` parameter. For example: `@XSlf4j(topic="reporting")`.


默认情况下，记录器的主题（或名称）将是用 `@Log` 批注批注的类（名称）。
这可以通过指定 `topic` 参数来定制。例如： `@XSlf4j(topic="reporting")` 。

