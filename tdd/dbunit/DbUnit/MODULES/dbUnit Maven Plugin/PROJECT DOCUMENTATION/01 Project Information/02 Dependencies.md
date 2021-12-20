## Project Dependencies

### compile

The following is a list of compile dependencies for this project. These dependencies are required to compile and run the application:

| GroupId | ArtifactId | Version | Type | Licenses |
| --- | --- | --- | --- | --- |
| `ant` | `ant` | `1.6.5` | jar | [The Apache Software License, Version 2.0]() |
| `commons-collections` | `commons-collections` | `3.2.2` | jar | [Apache License, Version 2.0]() |
| `org.apache.maven` | `maven-plugin-api` | `3.3.9` | jar | [Apache License, Version 2.0]() |
| `org.apache.maven` | `maven-settings` | `3.3.9` | jar | [Apache License, Version 2.0]() |
| `org.dbunit` | `dbunit` | `2.7.2` | jar | [GNU Lesser General Public License, Version 2.1]() |

### runtime

The following is a list of runtime dependencies for this project. 
These dependencies are required to run the application:

| GroupId | ArtifactId | Version | Type | Licenses |
| --- | --- | --- | --- | --- |
| `org.slf4j` | `slf4j-api` | `1.7.25` | jar | [MIT License]() |
| `org.slf4j` | `slf4j-simple` | `1.7.25` | jar | [MIT License]() |

### test

The following is a list of test dependencies for this project. 
These dependencies are only required to compile and run unit tests for the application:

| GroupId | ArtifactId | Version | Type | Licenses |
| --- | --- | --- | --- | --- |
| `hsqldb` | `hsqldb` | `1.8.0.7` | jar | [HSQLDB License]() |
| `junit` | `junit` | `4.12` | jar | [Eclipse Public License 1.0]() |

## Project Transitive Dependencies

The following is a list of transitive dependencies for this project. 
Transitive dependencies are the dependencies of the project dependencies.

### compile

The following is a list of compile dependencies for this project. 
These dependencies are required to compile and run the application:

| GroupId | ArtifactId | Version | Type | Licenses |
| --- | --- | --- | --- | --- |
| `javax.annotation` | `jsr250-api` | `1.0` | jar | [COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0]() |
| `javax.enterprise` | `cdi-api` | `1.0` | jar | [Apache License, Version 2.0]() |
| `javax.inject` | `javax.inject` | `1` | jar | [The Apache Software License, Version 2.0]() |
| `org.apache.commons` | `commons-lang3` | `3.4` | jar | [Apache License, Version 2.0]() |
| `org.apache.maven` | `maven-artifact` | `3.3.9` | jar | [Apache License, Version 2.0]() |
| `org.apache.maven` | `maven-model` | `3.3.9` | jar | [Apache License, Version 2.0]() |
| `org.codehaus.plexus` | `plexus-classworlds` | `2.5.2` | jar | [The Apache Software License, Version 2.0]() |
| `org.codehaus.plexus` | `plexus-component-annotations` | `1.5.5` | jar | [The Apache Software License, Version 2.0]() |
| `org.codehaus.plexus` | `plexus-utils` | `3.0.22` | jar | [The Apache Software License, Version 2.0]() |
| `org.eclipse.sisu` | `org.eclipse.sisu.inject` | `0.3.2` | jar | [Eclipse Public License, Version 1.0]() |
| `org.eclipse.sisu` | `org.eclipse.sisu.plexus` | `0.3.2` | jar | [Eclipse Public License, Version 1.0]() |

### test

The following is a list of test dependencies for this project. 
These dependencies are only required to compile and run unit tests for the application:

| GroupId | ArtifactId | Version | Type | Licenses |
| --- | --- | --- | --- | --- |
| `org.hamcrest` | `hamcrest-core` | `1.3` | jar | [New BSD License]() |

## Project Dependency Graph

### Dependency Tree

* org.dbunit:dbunit-maven-plugin:maven-plugin:1.1.1-SNAPSHOT [Information]
  * junit:junit:jar:4.12 (test) [Information]
    * org.hamcrest:hamcrest-core:jar:1.3 (test) [Information]
  * hsqldb:hsqldb:jar:1.8.0.7 (test) [Information]
  * org.dbunit:dbunit:jar:2.7.2 (compile) [Information]
  * org.slf4j:slf4j-api:jar:1.7.25 (runtime) [Information]
  * org.slf4j:slf4j-simple:jar:1.7.25 (runtime) [Information]
  * ant:ant:jar:1.6.5 (compile) [Information]
  * org.apache.maven:maven-plugin-api:jar:3.3.9 (compile) [Information]
    * org.apache.maven:maven-model:jar:3.3.9 (compile) [Information]
      * org.apache.commons:commons-lang3:jar:3.4 (compile) [Information]
    * org.apache.maven:maven-artifact:jar:3.3.9 (compile) [Information]
    * org.eclipse.sisu:org.eclipse.sisu.plexus:jar:0.3.2 (compile) [Information]
      * javax.enterprise:cdi-api:jar:1.0 (compile) [Information]
        * javax.annotation:jsr250-api:jar:1.0 (compile) [Information]
        * javax.inject:javax.inject:jar:1 (compile) [Information]
      * org.eclipse.sisu:org.eclipse.sisu.inject:jar:0.3.2 (compile) [Information]
      * org.codehaus.plexus:plexus-component-annotations:jar:1.5.5 (compile) [Information]
      * org.codehaus.plexus:plexus-classworlds:jar:2.5.2 (compile) [Information]
  * org.apache.maven:maven-settings:jar:3.3.9 (compile) [Information]
    * org.codehaus.plexus:plexus-utils:jar:3.0.22 (compile) [Information]
  * commons-collections:commons-collections:jar:3.2.2 (compile) [Information]

## Licenses

**Eclipse Public License, Version 1.0**: org.eclipse.sisu.inject, org.eclipse.sisu.plexus

**GNU Lesser General Public License, Version 2.1**: dbUnit Extension, dbUnit Maven Plugin

**Eclipse Public License 1.0**: JUnit

**MIT License**: SLF4J API Module, SLF4J Simple Binding

**Apache License, Version 2.0**: Apache Commons Collections, Apache Commons Lang, CDI APIs, Maven Artifact, Maven Model, Maven Plugin API, Maven Settings

**COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0**: JSR-250 Common Annotations for the JavaTM Platform

**New BSD License**: Hamcrest Core

**HSQLDB License**: HSQLDB

**The Apache Software License, Version 2.0**: Plexus :: Component Annotations, Plexus Classworlds, Plexus Common Utilities, ant, javax.inject

## Dependency File Details

| Filename | Size | Entries | Classes | Packages | Java Version | Debug Information |
| --- | --- | --- | --- | --- | --- | --- |
| ant-1.6.5.jar | 1 MB | 616 | 576 | 25 | 1.2 | Yes |
| commons-collections-3.2.2.jar | 588.3 kB |  |  |  |  |  |
| hsqldb-1.8.0.7.jar |  |  |  |  |  |  |
| jsr250-api-1.0.jar |  |  |  |  |  |  |
| cdi-api-1.0.jar |  |  |  |  |  |  |
| javax.inject-1.jar |  |  |  |  |  |  |
| junit-4.12.jar |  |  |  |  |  |  |
| commons-lang3-3.4.jar |  |  |  |  |  |  |
| maven-artifact-3.3.9.jar |  |  |  |  |  |  |
| maven-model-3.3.9.jar |  |  |  |  |  |  |
| maven-plugin-api-3.3.9.jar |  |  |  |  |  |  |
| maven-settings-3.3.9.jar |  |  |  |  |  |  |
| plexus-classworlds-2.5.2.jar |  |  |  |  |  |  |
| plexus-component-annotations-1.5.5.jar |  |  |  |  |  |  |
| plexus-utils-3.0.22.jar |  |  |  |  |  |  |
| dbunit-2.7.2.jar |  |  |  |  |  |  |
| org.eclipse.sisu.inject-0.3.2.jar |  |  |  |  |  |  |
| org.eclipse.sisu.plexus-0.3.2.jar |  |  |  |  |  |  |
| hamcrest-core-1.3.jar |  |  |  |  |  |  |
| slf4j-api-1.7.25.jar |  |  |  |  |  |  |
| slf4j-simple-1.7.25.jar |  |  |  |  |  |  |
| **Total** | **Size** | **Entries** | **Classes** | **Packages** | **Java Version** | **Debug Information** |
| 21 |  |  |  |  |  |  |
| compile: 16 |  |  |  |  |  |  |
| test: 3 |  |  |  |  |  |  |
| runtime: 2 |  |  |  |  |  |  |

## Dependency Repository Locations

| Repo ID | URL | Release | Snapshot |
| ---- | ---- | ---- | ---- |
| central | https://repo.maven.apache.org/maven2 | Yes | No |

Repository locations for each of the Dependencies.

| Artifact | nexus |
| ---- | ---- |
|  |  |
| org.apache.maven:maven-settings:jar:3.3.9 | &#x1F44D; |
| org.codehaus.plexus:plexus-classworlds:jar:2.5.2 | &#x1F44D; |
| org.codehaus.plexus:plexus-component-annotations:jar:1.5.5 | &#x1F44D; |
| org.codehaus.plexus:plexus-utils:jar:3.0.22 | &#x1F44D; |
| org.dbunit:dbunit:jar:2.7.2 | &#x1F44D; |
| org.eclipse.sisu:org.eclipse.sisu.inject:jar:0.3.2 | &#x1F44D; |
| org.eclipse.sisu:org.eclipse.sisu.plexus:jar:0.3.2 | &#x1F44D; |
| org.hamcrest:hamcrest-core:jar:1.3 | &#x1F44D; |
| org.slf4j:slf4j-api:jar:1.7.25 | &#x1F44D; |
| org.slf4j:slf4j-simple:jar:1.7.25 | &#x1F44D; |
| **Total** | **nexus** |
| 21 (compile: 16, test: 3, runtime: 2) | 21 |
