## Java Objects Serialization *Java 对象序列化*

Java objects serialization is enabled by default for columns of type `OTHER`, using standard Java serialization/deserialization semantics.


默认情况下，使用标准的 Java 序列化反序列化语义，为“OTHER”类型的列启用 Java 对象序列化。

---

To disable this feature set the system property `h2.serializeJavaObject=false` (default: true).


要禁用此功能，请设置系统属性 `h2.serializeJavaObject=false` （默认值：true）。

---

Serialization and deserialization of java objects is customizable both at system level and at database level providing a [JavaObjectSerializer]() implementation:


java 对象的序列化和反序列化在系统级和数据库级都可定制，提供 [JavaObjectSerializer]() 实现：

---

* At system level set the system property `h2.javaObjectSerializer` with the Fully Qualified Name of the `JavaObjectSerializer` interface implementation.
  It will be used over the entire JVM session to (de)serialize java objects being stored in column of type OTHER.
  Example `h2.javaObjectSerializer=com.acme.SerializerClassName`.
* At database level execute the SQL statement `SET JAVA_OBJECT_SERIALIZER 'com.acme.SerializerClassName'` or append `;JAVA_OBJECT_SERIALIZER='com.acme.SerializerClassName'` to the database URL: `jdbc:h2:~/test;JAVA_OBJECT_SERIALIZER='com.acme.SerializerClassName'`.


* 在系统级别，使用 `JavaObjectSerializer` 接口实现的完全限定名称设置系统属性 `h2.javaObjectSerializer` 。
  它将在整个 JVM 会话中用于（反）序列化存储在 OTHER 类型列中的 Java 对象。
  示例 `h2.javaObjectSerializer=com.acme.SerializerClassName` 。
* 在数据库级别执行 SQL 语句 `SET JAVA_OBJECT_SERIALIZER 'com.acme.SerializerClassName'` 或附加 `;JAVA_OBJECT_SERIALIZER='com.acme.SerializerClassName'` 到数据库 URL： `jdbc:h2:~/test;JAVA_OBJECT_SERIALIZER='com.acme.SerializerClassName'` 。

---

Please note that this SQL statement can only be executed before any tables are defined.


请注意，此 SQL 语句只能在定义任何表之前执行。

---
