## Export full database Example _导出完整数据库示例_

The following example exports all tables to a dataset file after test phase.


以下示例在测试阶段后将所有表导出到数据集文件。

---

```text
<project>
  [...]
  <build>
    [...]
    <plugins>
      [...]
      <plugin>
        <groupId>org.dbunit</groupId>
        <artifactId>dbunit-maven-plugin</artifactId>
        <version>1.1.1-SNAPSHOT</version>
        
        <!--jar file that has the jdbc driver -->
        <dependencies>
          <dependency>
            <groupId>your.vendor.group</groupId>
            <artifactId>your.vendor.artifactId</artifactId>
            <version>x.y</version>
          </dependency>
        </dependencies>
        
        <!-- common configurations -->
        <configuration>
          <driver>com.vendor.jdbc.Driver</driver>
          <url>jdbc:vendor:mydatabase</url>
          <username>a.username</username>
          <password>a.password</password>
          [....]
        </configuration>
        
        <executions>
          <execution>
            <phase>test</phase>
            <goals>
              <goal>export</goal>
            </goals>
            <!-- specific configurations -->
            <configuration>
              <format>xml</format>
              <dest>target/dbunit/export.xml</dest>
            </configuration>
          </execution>
          [...]
        </executions>
        
        [...]
      </plugin>
      [...]
    </plugins>
    [...]
  </build>
  [...]
</project>
```
