## `Settings.xml`

You can hide username/password in your `settings.xml`, ensure to configure "`settingsKey`" in your `pom`'s configuration, otherwise it will use your database's url as lookup key


你可以在你的 `settings.xml` 中隐藏 username/password ，确保在 `pom` 的配置中配置“`settingsKey`”，否则它将使用你数据库的url作为查找键

---

```text
<settings>

  ....

  <servers>
  
    <server>
     <id>sensibleKey</id>
     <username>your-username</username>
     <password>your-password</password>
    </server>

   </servers>
 
 </settings>
```
