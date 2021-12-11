# `@Log (and friends)`

## Overview

You put the variant of `@Log` on your class (whichever one applies to the logging system you use); you then have a static final `log` field, initialized as is the commonly prescribed way for the logging framework you use, which you can then use to write log statements.

There are several choices available:

`@CommonsLog`
Creates `private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(LogExample.class);`

`@Flogger`
Creates `private static final com.google.common.flogger.FluentLogger log = com.google.common.flogger.FluentLogger.forEnclosingClass();`

`@JBossLog`
Creates `private static final org.jboss.logging.Logger log = org.jboss.logging.Logger.getLogger(LogExample.class);`

`@Log`
Creates `private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(LogExample.class.getName());`

`@Log4j`
Creates `private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(LogExample.class);`

`@Log4j2`
Creates `private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(LogExample.class);`

`@Slf4j`
Creates `private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogExample.class);`

`@XSlf4j`
Creates `private static final org.slf4j.ext.XLogger log = org.slf4j.ext.XLoggerFactory.getXLogger(LogExample.class);`

`@CustomLog`
Creates `private static final com.foo.your.Logger log = com.foo.your.LoggerFactory.createYourLogger(LogExample.class);`

This option requires that you add a configuration to your `lombok.config` file to specify what `@CustomLog` should do.

For example:`lombok.log.custom.declaration = com.foo.your.Logger com.foo.your.LoggerFactory.createYourLog(TYPE)(TOPIC)` which would produce the above statement. 
First comes a type which is the type of your logger, then a space, then the type of your logger factory, then a dot, then the name of the logger factory method, and then 1 or 2 parameter definitions; at most one definition with `TOPIC` and at most one without `TOPIC`. 
Each parameter definition is specified as a parenthesised comma-separated list of parameter kinds. 
The options are: `TYPE` (passes this `@Log` decorated type, as a class), `NAME` (passes this `@Log` decorated type's fully qualified name), `TOPIC` (passes the explicitly chosen topic string set on the `@CustomLog` annotation), and `NULL` (passes `null`).

The logger type is optional; if it is omitted, the logger factory type is used. 
(So, if your logger class has a static method that creates loggers, you can shorten your logger definition).

Please contact us if there is a public, open source, somewhat commonly used logging framework that we don't yet have an explicit annotation for. 
The primary purpose of `@CustomLog` is to support your in-house, private logging frameworks.

By default, the topic (or name) of the logger will be the (name of) the class annotated with the `@Log` annotation. 
This can be customised by specifying the `topic` parameter. For example: `@XSlf4j(topic="reporting")`.
