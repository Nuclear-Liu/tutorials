## Custom Data Types Handler API *自定义数据类型处理程序 API*

It is possible to extend the type system of the database by providing your own implementation of minimal required API basically consisting of type identification and conversion routines.


可以通过提供您自己的最小所需 API 的实现来扩展数据库的类型系统，该 API 基本上由类型识别和转换例程组成。

---

In order to enable this feature, set the system property `h2.customDataTypesHandler` (default: null) to the fully qualified name of the class providing [CustomDataTypesHandler]() interface implementation.
The instance of that class will be created by H2 and used to:


为了启用此功能，请将系统属性 `h2.customDataTypesHandler`（默认值：null）设置为提供 [CustomDataTypesHandler]() 接口实现的类的完全限定名称。
该类的实例将由 H2 创建并用于：

---

* resolve the names and identifiers of extrinsic data types.
* convert values of extrinsic data types to and from values of built-in types.
* provide order of the data types.


* 解析外部数据类型的名称和标识符。
* 将外部数据类型的值与内置类型的值相互转换。
* 提供数据类型的顺序。

---

This is a system-level setting, i.e. affects all the databases.


这是系统级设置，即影响所有数据库。

---

__Note__:
Please keep in mind that this feature may not possibly provide the same ABI stability level as other features as it exposes many of the H2 internals.
You may be required to update your code occasionally due to internal changes in H2 if you are going to use this feature.


__注意__：
请记住，此功能可能无法提供与其他功能相同的 ABI 稳定性级别，因为它暴露了许多 H2 内部结构。
如果您打算使用此功能，由于 H2 中的内部更改，您可能需要偶尔更新您的代码。
