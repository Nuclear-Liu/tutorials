## Overview

Building DbUnit is quite simple. You need the following software installed:

    Java SE SDK 8+
    Maven 3
    Git client

Once you have them all, you can build DbUnit by just typing mvn! The items below describe every step.

## Generating the JAR

    Install Java SE SDK and Maven.
    Obtain DbUnit code, either current or released source (see Quick Links on left menu)
    On the root directory, simply type mvn in the command line. (If you need to clean up the binaries, run mvn clean install instead.) The jar file will be generated in the target directory.

## Creating the site

Run mvn site in the command line; the site will be available on target/site/index.html. Note that you might get an OutOfMemoryExceptionError; if that happens, you must increase the heap size through the MAVEN_OPTS variable (for instance, on Unix systems, you could run MAVEN_OPTS=-mx512M mvn site).

## Updating the repository and site

Once new code is committed, it is necessary to update the Maven repository with new snapshots, and also update the site. 
These 2 tasks can be done with a simple command:

`mvn clean source:jar javadoc:jar deploy site site:deploy`
