## Export task with custom query _使用自定义查询导出任务_

```text
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
      <queries>
        <query implementation="org.dbunit.ant.Query">
          <name>myQueryName</name>
          <sql>select * from MYTAB</sql>
        </query>
      </queries>
    </configuration>
  </execution>
[...]
</executions>
```
