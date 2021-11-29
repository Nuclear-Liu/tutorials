## Plugin Documentation _插件文档_

Goals available for this plugin:


此插件可用的目标：

---

| Goal | Description |
| ---- | ---- |
| [dbunit:compare]() | Execute DbUnit Compare operation. |
| [dbunit:export]() | Execute DbUnit Export operation. |
| [dbunit:help]() | Display help information on dbunit-maven-plugin. <br/> Call `mvn dbunit:help -Ddetail=true -Dgoal=<goal-name>` to display parameter details. |
| [dbunit:operation]() | Execute DbUnit's Database Operation with an external dataset file. |


| 目标 | 描述 |
| ---- | ---- |
| [dbunit:compare]() | 执行 DbUnit 比较操作。 |
| [dbunit:export]() | 执行 DbUnit 导出操作。 |
| [dbunit:help]() | 显示有关 dbunit-maven-plugin 的帮助信息。 <br/> 调用 `mvn dbunit:help -Ddetail=true -Dgoal=<goal-name>` 以显示参数详细信息。 |
| [dbunit:operation]() | 使用外部数据集文件执行 DbUnit 的数据库操作。 |

---

### System Requirements _系统要求_

The following specifies the minimum requirements to run this Maven plugin:


以下指定了运行此 Maven 插件的最低要求：

---

| Maven | JDK | Memory | Disk Space |
| ---- | ---- | ---- | ---- |
| `2.0` | `${project.build.java.target}` | No minimum requirement. | No minimum requirement. |

### Usage _用法_

You should specify the version in your project's plugin configuration:


您应该在项目的插件配置中指定版本：

---

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


更多信息请参见“插件配置指南”

---
