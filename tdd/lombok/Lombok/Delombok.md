# Delombok


## Overview _概述_


Normally, lombok adds support for all the lombok features directly to your IDE and compiler by plugging into them.


通常，lombok 通过插入它们来直接向您的 IDE 和编译器添加对所有 lombok 功能的支持。


However, lombok doesn't cover all tools. 
For example, lombok cannot plug into javadoc, nor can it plug into the Google Widget Toolkit, both of which run on java sources. 
Delombok still allows you to use lombok with these tools by preprocessing your java code into java code with all of lombok's transformations already applied.


但是，lombok 并未涵盖所有工具。
例如，lombok 不能插入javadoc，也不能插入 Google Widget Toolkit ，两者都运行在java 源代码上。
Delombok 仍然允许您将 lombok 与这些工具一起使用，方法是将您的 java 代码预处理为 java 代码，并且已经应用了 lombok 的所有转换。


Delombok can of course also help understand what's happening with your source by letting you look at exactly what lombok is doing 'under the hood'.


Delombok 当然也可以通过让您准确地查看 lombok 正在“幕后”做什么来帮助了解您的源码发生了什么。


Delombok's standard mode of operation is that it copies an entire directory into another directory, recursively, skipping class files, and applying lombok transformations to any java source files it encounters.


Delombok 的标准操作模式是，它递归地将整个目录复制到另一个目录中，跳过类文件，并将 lombok 转换应用于它遇到的任何 Java 源文件。


Delombok's output format can be configured with command line options (use `--format-help` for a complete list). 
A few such options are automatically scanned from input if possible (such as indent). 
If delombok's formatting is not conforming to your preferred code style, have a look!


Delombok 的输出格式可以通过命令行选项进行配置（使用 `--format help` 查看完整列表）。
如果可能的话，一些这样的选项会从输入中自动扫描（例如缩进）。
如果 delombok 的格式不符合您首选的代码样式，请查看！


## Running delombok on the command line _在命令行上运行 delombok_


Delombok is included in `lombok.jar`. 
To use it, all you need to run on the command line is:


Delombok 包含在 `lombok.jar` 中。
要使用它，您只需要在命令行上运行：


```shell
java -jar lombok.jar delombok src -d src-delomboked
```


Which will duplicate the contents of the `src` directory into the `src-delomboked` directory, which will be created if it doesn't already exist, but delomboked of course. 
Delombok on the command line has a few more options; use the `--help` parameter to see more options.


这会将 `src` 目录的内容复制到 `src-delomboked` 目录中，如果它不存在，则会创建该目录，但当然是 delomboked。
命令行上的 Delombok 还有几个选项；使用 `--help` 参数查看更多选项。


To let delombok print the transformation result of a single java file directly to standard output, you can use:


要让 delombok 将单个 java 文件的转换结果直接打印到标准输出，可以使用：


```shell
java -jar lombok.jar delombok -p MyJavaFile.java
```


The `-classpath`, `-sourcepath`, and `--module-path` options of javac are replicated as `--classpath`, `--sourcepath`, and `--module-path` in delombok.


javac 的 `-classpath` 、 `-sourcepath` 和 `--module-path` 选项在 delombok 中被复制为 `--classpath` 、 `--sourcepath` 和 `--module-path` 。


## Running delombok in ant _在 ant 中运行 delombok_


`lombok.jar` includes an ant task which can apply delombok for you. 
For example, to create javadoc for your project, your `build.xml` file would look something like:


`lombok.jar` 包含一个 ant 任务，可以为您应用 delombok。
例如，要为您的项目创建 javadoc，您的 `build.xml` 文件将类似于：


```xml
<target name="javadoc">
    <taskdef classname="lombok.delombok.ant.Tasks$Delombok" classpath="lib/lombok.jar" name="delombok" />
        <mkdir dir="build/src-delomboked" />
            <delombok verbose="true" encoding="UTF-8" to="build/src-delomboked" from="src">
                <format value="suppressWarnings:skip" />
            </delombok>
        <mkdir dir="build/api" />
    <javadoc sourcepath="build/src-delomboked" defaultexcludes="yes" destdir="build/api" />
</target>
```


Instead of a `from` attribute, you can also nest `<fileset>` nodes. 
The `delombok` supports `sourcepath`, `classpath`, and `modulepath` as parameter or as nested element, or as nested `refid` element, similar to the `javac` task.


除了 `from` 属性，您还可以嵌套 `<fileset>` 节点。
`delombok` 支持 `sourcepath`、 `classpath` 和 `modulepath` 作为参数或嵌套元素，或嵌套的 `refid` 元素，类似于 `javac` 任务。


## Running delombok in maven _在 Maven 中运行 delombok_


Anthony Whitford has written a [maven plugin](https://github.com/awhitford/lombok.maven) for delomboking your source code.


Anthony Whitford 编写了一个 [maven 插件]() 来对您的源代码进行 delomboking 。


## Running delombok in sbt _在 sbt 中运行 delombok_


Yang Bo has written an [sbt plugin](https://github.com/ThoughtWorksInc/sbt-delombok) for delomboking your source code.


Yang Bo 编写了一个 [sbt plugin]() 用于对您的源代码进行 delomboking 。


## Limitations _限制_


Delombok tries to preserve your code as much as it can, but comments may move around a little bit, especially comments that are in the middle of a syntax node. 
For example, any comments appearing in the middle of a list of method modifiers, such as `public /*comment*/ static ...` will move towards the front of the list of modifiers. 
In practice, any java source parsing tool will not be affected.


Delombok 尝试尽可能多地保留您的代码，但注释可能会移动一点，尤其是位于语法节点中间的注释。
例如，任何出现在方法修饰符列表中间的注释，例如 `public /*comment*/ static ...` ，都会移到修饰符列表的前面。
实际上，任何 java 源解析工具都不会受到影响。


To keep any changes to your code style to a minimum, delombok just copies a source file directly without changing any of it if the source file contains no lombok transformations.


为了尽量减少对代码样式的任何更改，如果源文件不包含 lombok 转换， delombok 只会直接复制源文件而不更改其中的任何内容。

