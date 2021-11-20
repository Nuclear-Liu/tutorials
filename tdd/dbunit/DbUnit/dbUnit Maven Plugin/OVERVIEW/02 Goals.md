## Plugin Documentation

Goals available for this plugin:

| Goal | Description |
| ---- | ---- |
| [dbunit:compare]() | Execute DbUnit Compare operation. |
| [dbunit:export]() | Execute DbUnit Export operation. |
| [dbunit:help]() | Display help information on dbunit-maven-plugin. <br/> Call `mvn dbunit:help -Ddetail=true -Dgoal=<goal-name>` to display parameter details. |
| [dbunit:operation]() | Execute DbUnit's Database Operation with an external dataset file. |

### System Requirements

The following specifies the minimum requirements to run this Maven plugin:

|  |  |
| ---- | ---- |
| Maven | `2.0` |
| JDK | `${project.build.java.target}` |
| Memory | No minimum requirement. |
| Disk Space | No minimum requirement. |

### Usage

You should specify the version in your project's plugin configuration:

```xml
<project>
  ...
  <build>
    <!-- To define the plugin version in your parent POM -->
    <pluginManagement>
      <plugins>
        <plugin>
          <groupId>org.dbunit</groupId>
          <artifactId>dbunit-maven-plugin</artifactId>
          <version>1.1.1-SNAPSHOT</version>
        </plugin>
        ...
      </plugins>
    </pluginManagement>
    <!-- To use the plugin goals in your POM or parent POM -->
    <plugins>
      <plugin>
        <groupId>org.dbunit</groupId>
        <artifactId>dbunit-maven-plugin</artifactId>
        <version>1.1.1-SNAPSHOT</version>
      </plugin>
      ...
    </plugins>
  </build>
  ...
</project>
```

For more information, see "Guide to Configuring Plug-ins"
