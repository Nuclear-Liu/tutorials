# Delombok


## Overview


Normally, lombok adds support for all the lombok features directly to your IDE and compiler by plugging into them.


However, lombok doesn't cover all tools. 
For example, lombok cannot plug into javadoc, nor can it plug into the Google Widget Toolkit, both of which run on java sources. 
Delombok still allows you to use lombok with these tools by preprocessing your java code into java code with all of lombok's transformations already applied.


Delombok can of course also help understand what's happening with your source by letting you look at exactly what lombok is doing 'under the hood'.


Delombok's standard mode of operation is that it copies an entire directory into another directory, recursively, skipping class files, and applying lombok transformations to any java source files it encounters.


Delombok's output format can be configured with command line options (use `--format-help` for a complete list). 
A few such options are automatically scanned from input if possible (such as indent). 
If delombok's formatting is not conforming to your preferred code style, have a look!


## Running delombok on the command line


Delombok is included in `lombok.jar`. 
To use it, all you need to run on the command line is:


```shell
java -jar lombok.jar delombok src -d src-delomboked
```


Which will duplicate the contents of the `src` directory into the `src-delomboked` directory, which will be created if it doesn't already exist, but delomboked of course. 
Delombok on the command line has a few more options; use the `--help` parameter to see more options.


To let delombok print the transformation result of a single java file directly to standard output, you can use:


```shell
java -jar lombok.jar delombok -p MyJavaFile.java
```


The `-classpath`, `-sourcepath`, and `--module-path` options of javac are replicated as `--classpath`, `--sourcepath`, and `--module-path` in delombok.


## Running delombok in ant


`lombok.jar` includes an ant task which can apply delombok for you. 
For example, to create javadoc for your project, your `build.xml` file would look something like:


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
The `delombok` supports `sourcepath`, `classpath`, and `modulepath` as parameter or as nested element, or as nested refid element, similar to the `javac` task.


## Running delombok in maven


Anthony Whitford has written a [maven plugin]() for delomboking your source code.


## Running delombok in sbt


Yang Bo has written an [sbt plugin]() for delomboking your source code.


## Limitations


Delombok tries to preserve your code as much as it can, but comments may move around a little bit, especially comments that are in the middle of a syntax node. 
For example, any comments appearing in the middle of a list of method modifiers, such as `public /*comment*/ static ...` will move towards the front of the list of modifiers. 
In practice, any java source parsing tool will not be affected.


To keep any changes to your code style to a minimum, delombok just copies a source file directly without changing any of it if the source file contains no lombok transformations.

