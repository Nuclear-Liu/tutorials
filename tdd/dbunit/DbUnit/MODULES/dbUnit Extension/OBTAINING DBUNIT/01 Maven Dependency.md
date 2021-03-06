## Maven Dependency

```xml
<dependency>
    <groupId>org.dbunit</groupId>
    <artifactId>dbunit</artifactId>
    <version>${dbunitVersion}</version>
    <scope>test</scope>
</dependency>
```

Of course, set the correct version - either set a property as in the example or edit it directly.

## Releases

dbUnit releases are in [Maven Central]().

## Snapshots

[The Sonatype OSS snapshot repository has the dbUnit snapshots](). 
To use it, define the repository:

```xml
<repository>
    <id>sonatype-oss-snapshots</id>
    <name>Sonatype OSS Snapshots Repo</name>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
</repository>
```

Note: it is a better practice to add the repo to your artifact manager, such as Nexus, or define in `settings.xml`, rather than add the repo to a POM.

### `settings.xml` How-To

Official Maven documentation on repositories in `settings.xml`: [https://maven.apache.org/settings.html#Repositories]()

Stack Overflow answer showing the Sonatype OSS Snapshot repo definition in `settings.xml`: [http://stackoverflow.com/questions/7715321/how-to-download-snapshot-version-from-maven-snapshot-repository#7717234]()
