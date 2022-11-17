# `DeviceCtrl` 提供用于访问基础硬件信息的接口

* 构造
  * `DeviceCtrl(long nativeDevice)` 
  * `DeviceCtrl(int deviceNumber, String description, AccessMode mode)` 
* 属性
  * `DeviceNumber` 获取基础设备的标识号
  * `Description` 获取基础设备的说明
  * `AccessMode` 获取对基础设备的访问权限([`AccessMode`](./../enumerations/AccessMode.md))
  * `ProductId` 获取基础设备的产品 ID
  * `BoardId` 获取基础设备的主板 ID
  * `BoardVersion` 获取基础设备的硬件版本
  * `DriverVersion` 获取基础设备的驱动进程版本
  * `DllVersion` 获取基础设备的 DLL 版本
* 方法
  * `Dispose` 销毁实例
  * `Refresh` 当低级设备属性更改时，从设备重新加载相关属性。它仅对选择模式读取的设备有效。
* 事件
  * `PropertyChanged` 在低级别设备的属性更改时发生。它仅对使用模式读取打开的设备有效。可以通过调用 `Refresh` 方法来重新加载最新的属性值
    
  * `Reconnected` 当所选设备中断一段时间后重新连接到系统时发生
    
  * `Removed` 在所选设备从系统中删除时发生
