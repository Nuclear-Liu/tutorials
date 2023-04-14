# JUnit 5 User Guide

Stefan Bechtold Sam Brannen Johannes Link Matthias Merdes Marc Philipp Juliette de Rancourt Christian Stein

- version 5.8.1

## 1. Overview

The goal of this document is to provide comprehensive reference documentation for programmers writing tests, extension authors, and engine authors as well as build tool and IDE vendors.

This document is also available as a [PDF download](https://junit.org/junit5/docs/current/user-guide/junit-user-guide-5.8.1.pdf).

### 1.1. What is JUnit 5?

Unlike previous versions of JUnit, JUnit 5 is composed of several different modules from three different sub-projects.

**JUnit 5** = **JUnit Platform** + **JUnit Jupiter** + **JUnit Vintage**

The JUnit Platform serves as a foundation for launching testing frameworks on the JVM. 
It also defines the TestEngine API for developing a testing framework that runs on the platform. 
Furthermore, the platform provides a Console Launcher to launch the platform from the command line and a JUnit 4 based Runner for running any TestEngine on the platform in a JUnit 4 based environment. 
First-class support for the JUnit Platform also exists in popular IDEs (see IntelliJ IDEA, Eclipse, NetBeans, and Visual Studio Code) and build tools (see Gradle, Maven, and Ant).

JUnit Jupiter is the combination of the new programming model and extension model for writing tests and extensions in JUnit 5. 
The Jupiter sub-project provides a TestEngine for running Jupiter based tests on the platform.

JUnit Vintage provides a TestEngine for running JUnit 3 and JUnit 4 based tests on the platform. 
It requires JUnit 4.12 or later to be present on the class/module path.

### 1.2. Supported Java Versions

JUnit 5 requires Java 8 (or higher) at runtime. 
However, you can still test code that has been compiled with previous versions of the JDK.

### 1.3. Getting Help

Ask JUnit 5 related questions on Stack Overflow or chat with us on Gitter.

### 1.4. Getting Started

#### 1.4.1. Downloading JUnit Artifacts

To find out what artifacts are available for download and inclusion in your project, refer to Dependency Metadata. 
To set up dependency management for your build, refer to Build Support and the Example Projects.

#### 1.4.2. JUnit 5 Features

To find out what features are available in JUnit 5 and how to use them, read the corresponding sections of this User Guide, organized by topic.

* Writing Tests in JUnit Jupiter

* Migrating from JUnit 4 to JUnit Jupiter

* Running Tests

* Extension Model for JUnit Jupiter

* Advanced Topics

    * JUnit Platform Launcher API

    * JUnit Platform Test Kit


#### 1.4.3. Example Projects

To see complete, working examples of projects that you can copy and experiment with, the junit5-samples repository is a good place to start. 
The junit5-samples repository hosts a collection of sample projects based on JUnit Jupiter, JUnit Vintage, and other testing frameworks. 
You’ll find appropriate build scripts (e.g., build.gradle, pom.xml, etc.) in the example projects. 
The links below highlight some of the combinations you can choose from.

* For Gradle and Java, check out the junit5-jupiter-starter-gradle project.

* For Gradle and Kotlin, check out the junit5-jupiter-starter-gradle-kotlin project.

* For Gradle and Groovy, check out the junit5-jupiter-starter-gradle-groovy project.

* For Maven, check out the junit5-jupiter-starter-maven project.

* For Ant, check out the junit5-jupiter-starter-ant project.


