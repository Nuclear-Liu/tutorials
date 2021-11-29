## Clean-Insert Example _清洁插入示例_

The following example cleans and populates database with an external DbUnit dataset


以下示例使用外部 DbUnit 数据集清理和填充数据库

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
          <password>a.password<password>
          [....]
        </configuration>
        
        </executions>
          <execution>
            <phase>test-compile</phase>
            <goals>
              <goal>operation</goal>
            </goals>
            <!-- specific configurations -->
            <configuration>
              <type>CLEAN_INSERT</type>
              <sources>
                <src>src/test/data/insert.xml</src>
              </sources>
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
