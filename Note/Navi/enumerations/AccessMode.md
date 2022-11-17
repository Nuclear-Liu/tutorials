# `AccessMode` 定义用于打开设备的有效访问模式

* 成员
  * `ModeRead` 以只读模式打开设备；在此模式下无法更改任何内容，并且可用功能有限，多个用户可以同时在此模式下打开设备
  * `ModeWrite` 使用读写模式打开设备；应用拥有对设备的完全控制权，只有一个应用可以同时在此模式下打开设备

> **备注：**
> 
> `AccessMode` 列出支持的访问模式。
> * `ModeRead` 所选设备的所有属性都无法改变，只读权限，一些可用的功能会受到限制，这种模式允许多个应用同时使用 `ModeRead` 打开设备
> * `ModeWrite` 对设备的独占控制权，另外的应用不能同时用 `ModeWrite` 打开设备