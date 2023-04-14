# `@Log (and friends)`


Captain's Log, stardate 24435.7: "What was that line again?"


船长日志，星历 24435.7：“那条线又是什么？”


The various `@Log` variants were added in lombok v0.10. NEW in lombok 0.10: You can annotate any class with a log annotation to let lombok generate a logger field.


在 lombok v0.10 中添加了各种 `@Log` 变体。 lombok 0.10 中的新功能：您可以使用日志注释对任何类进行注释，让 lombok 生成记录器字段。


The logger is named `log` and the field's type depends on which logger you have selected.


记录器名为 `log` ，字段的类型取决于您选择的记录器。


NEW in lombok v1.16.24: Addition of google's FluentLogger (via `@Flogger`).


lombok v1.16.24 中的新功能：添加了 google 的 FluentLogger（通过 `@Flogger` ）。


NEW in lombok v1.18.10: Addition of `@CustomLog` which lets you add any logger by configuring how to create them with a config key.


lombok v1.18.10 中的新功能：添加了 `@CustomLog` ，它允许您通过配置如何使用配置键创建记录器来添加任何记录器。


## Overview


You put the variant of `@Log` on your class (whichever one applies to the logging system you use); you then have a `static` `final` `log` field, initialized as is the commonly prescribed way for the logging framework you use, which you can then use to write log statements.


你把 `@Log` 的变体放在你的类上（无论哪个适用于你使用的日志系统）；然后你有一个 `static` `final` `log` 字段，按照你使用的日志框架的常用规定方式进行初始化，然后你可以用它来编写日志语句。


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


logger 类型是可选的；如果省略，则使用 logger 工厂类型。
（因此，如果您的 logger 类具有创建 logger 的静态方法，您可以缩短记录器定义）。


Please contact us if there is a public, open source, somewhat commonly used logging framework that we don't yet have an explicit annotation for. 
The primary purpose of `@CustomLog` is to support your in-house, private logging frameworks.


如果有一个公共的、开源的、有些常用的日志框架，但我们还没有明确的注释，请联系我们。
`@CustomLog` 的主要目的是支持您的内部私有日志框架。


By default, the topic (or name) of the logger will be the (name of) the class annotated with the `@Log` annotation. 
This can be customised by specifying the `topic` parameter. 
For example: `@XSlf4j(topic="reporting")`.


默认情况下， logger 的主题（或名称）将是用 `@Log` 注解注解的类（名称）。
这可以通过指定 `topic` 参数来定制。
例如： `@XSlf4j(topic="reporting")` 。


## Supported configuration keys:


`lombok.log.fieldName =` **an identifier** (default: `log`).


The generated logger fieldname is by default `log`, but you can change it to a different name with this setting.


生成的 logger 字段名称默认为 `log` ，但您可以使用此设置将其更改为不同的名称。


`lombok.log.fieldIsStatic =` [`true` | `false`] (default: `true`)


Normally the generated logger is a `static` field. By setting this key to `false`, the generated field will be an instance field instead.


通常生成的记录器是一个 `static` 字段。通过将此键设置为 `false` ，生成的字段将改为实例字段。


`lombok.log.custom.declaration` = _LoggerType_ **LoggerFactoryType.loggerFactoryMethod**(_loggerFactoryMethodParams_)(_loggerFactoryMethodParams_)


Configures what to generate when `@CustomLog` is used. (The italicized parts are optional). 
loggerFactoryMethodParams is a comma-separated list of zero to any number of parameter kinds to pass. 
Valid kinds: `TYPE`, `NAME`, `TOPIC`, and `NULL`. 
You can include a parameter definition for the case where no explicit topic is set (do not include the `TOPIC` in the parameter list), and for when an explicit topic is set (do include the `TOPIC` parameter in the list).


配置使用 `@CustomLog` 时生成的内容。 （斜体部分是可选的）。
loggerFactoryMethodParams 是一个以逗号分隔的列表，其中包含零到任意数量的要传递的参数类型。
有效种类：`TYPE`、`NAME`、`TOPIC` 和 `NULL`。
您可以在未设置显式主题的情况下（不要在参数列表中包含 `TOPIC` ）以及在设置显式主题时（在列表中包含 `TOPIC` 参数）包含参数定义。


`lombok.log.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of any of the various log annotations as a warning or error if configured.


如果已配置，Lombok 会将任何各种日志注释的任何使用标记为警告或错误。


`lombok.log.custom.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@lombok.CustomLog` as a warning or error if configured.


如果已配置，Lombok 会将任何对 `@lombok.CustomLog` 的使用标记为警告或错误。


`lombok.log.apacheCommons.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@lombok.extern.apachecommons.CommonsLog` as a warning or error if configured.


如果已配置，Lombok 会将任何对 `@lombok.extern.apachecommons.CommonsLog` 的使用标记为警告或错误。


`lombok.log.flogger.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@lombok.extern.flogger.Flogger` as a warning or error if configured.


如果已配置，Lombok 会将任何对 `@lombok.extern.flogger.Flogger` 的使用标记为警告或错误。


`lombok.log.jbosslog.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@lombok.extern.jbosslog.JBossLog` as a warning or error if configured.


如果已配置，Lombok 会将任何对 `@lombok.extern.jbosslog.JBossLog` 的使用标记为警告或错误


`lombok.log.javaUtilLogging.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@lombok.extern.java.Log `as a warning or error if configured.


如果已配置，Lombok 会将任何对 `@lombok.extern.java.Log ` 的使用标记为警告或错误。


`lombok.log.log4j.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@lombok.extern.log4j.Log4j` as a warning or error if configured.


如果已配置，Lombok 会将任何对 `@lombok.extern.log4j.Log4j` 的使用标记为警告或错误。


`lombok.log.log4j2.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@lombok.extern.log4j.Log4j2` as a warning or error if configured.


如果已配置，Lombok 会将任何对 `@lombok.extern.log4j.Log4j2` 的使用标记为警告或错误。


`lombok.log.slf4j.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@lombok.extern.slf4j.Slf4j` as a warning or error if configured.


如果已配置，Lombok 会将任何对 `@lombok.extern.slf4j.Slf4j` 的使用标记为警告或错误。


`lombok.log.xslf4j.flagUsage =` [`warning` | `error`] (default: not set)


Lombok will flag any usage of `@lombok.extern.slf4j.XSlf4j` as a warning or error if configured.


如果已配置，Lombok 会将任何对 `@lombok.extern.slf4j.XSlf4j` 的使用标记为警告或错误。
