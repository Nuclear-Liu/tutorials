## Usage _用法_

The execution of this plugin's mojo can be bound to a phase of build lifecycle. 
Use the dependencies in plugin to specify the artifact that has your JDBC driver.


这个插件的 mojo 的执行可以绑定到构建生命周期的一个阶段。
使用插件中的依赖项来指定具有 JDBC 驱动程序的工件。

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
            <groupId></groupId>
            <artifactId></artifactId>
            <version></version>
          </dependency>
        </dependencies>
        
        <!-- common configurations -->
        <configuration>
          <driver></driver>
          <url></url>
          <username></username>
          <password><password>
          [....]
        </configuration>
        
        </executions>
          <execution>
            <phase></phase>
            <goals>
              <goal></goal>
            </goals>
            <!-- specific configurations -->
            <configuration>
              [...]
            </configuration>
          </execution>
          <execution>
            <phase></phase>
            <goals>
              <goal></goal>
            </goals>
            <!-- specific configurations -->
            <configuration>
              [...]
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

The configuration element can contain all properties and features that are known to the DatabaseConfig class (see http://dbunit.sourceforge.net/properties.html ). 
It allows to specify the short name of the properties in the same way it works for the ant task: http://dbunit.sourceforge.net//anttask.html Note that there are still the old and deprecated configuration elements (not nested in the `dbconfig` element but specified explicitly beneath the configuration). 
If you specify your properties in both ways then the `dbconfig` value will always have precedence over the old ones.


配置元素可以包含 DatabaseConfig 类已知的所有属性和功能（请参阅 http://dbunit.sourceforge.net/properties.html ）。
它允许以与 ant 任务相同的方式指定属性的短名称： http://dbunit.sourceforge.net//anttask.html 请注意，仍然存在旧的和已弃用的配置元素（未嵌套在 `dbconfig` 元素中，但已指定明确在配置之下）。
如果您以两种方式指定属性，则 `dbconfig` 值将始终优先于旧值。

---

```text
[...]
<configuration>
  <dbconfig>
      <property>
        <name>datatypeFactory</name>
        <value>org.dbunit.ext.hsqldb.HsqldbDataTypeFactory</value>
      </property>
      <property>
        <name>tableType</name>
        <value>TABLE,SYNONYM</value>
      </property>
      <property>
        <name>batchedStatements</name>
        <value>true</value>
      </property>
      <property>
        <name>caseSensitiveTableNames</name>
        <value>true</value>
      </property>
  </dbconfig>
</configuration>
[...]
```
