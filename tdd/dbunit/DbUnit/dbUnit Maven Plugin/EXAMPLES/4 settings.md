## Settings.xml

You can hide username/password in your settings.xml, ensure to configure "settingsKey" in your pom's configuration, otherwise it will use your database's url as lookup key

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
