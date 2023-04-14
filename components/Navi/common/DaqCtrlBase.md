# `DaqCtrlBase` DAQNavi 设备组件的基类（组件通用的基本功能）

* 构造
  * `DaqCtrlBase` 初始化 `DaqCtrlBase` 类的新实例
* 属性
  * `SelectedDevice` 获取所选设备的信息或选择具有指定信息的设备
    
    属性类型： [`DeviceInformation`](./../structures/DeviceInformation.md)
    
    * 获取 `SelectedDevice` 属性将返回 `DeviceInformation` 的对象，其中包含所选设备的信息；在获取属性时没有选择任何设备，则返回的设备信息的设备编号将为 `-1` ，返回的设备信息的 `DeviceInformation` 将为 `No Device` ，表示设备无效
    * 设置 `SelectedDevice` 属性将关闭当前选定的设备，并打开由 `DeviceInformation` 对象指定的设备，该对象包含要选择的设备的信息
    
  * `State` 获取指示组件状态的控件状态 [`ControlState`](./../enumerations/ControlState.md) 枚举值
    
    属性类型： [`ControlState`](./../enumerations/ControlState.md)
    
    * 组件的状态，例如： `Uninited` `Idle` `Ready` `Running` 等；有关详细信息，参见 [`ControlState`](./../enumerations/ControlState.md)
    
  * `Initialized` 获取一个值，该值指示是否已成功选择设备
    
    属性类型： `bool`
    
    * `true`: 已成功选择设备，否则为 `false`
    
  * `Device` 获取与后备硬件接口的 [`DeviceCtrl`](./DeviceCtrl.md) 类的对象
    
    属性类型： [`DeviceCtrl`](./DeviceCtrl.md)
    
    与后备硬件接口的设备对象
    
  * `SupportedDevices` 获取支持指定功能的所有设备
    
    属性类型： [`DeviceTreeNode`](./../structures/DeviceTreeNode.md) 数组
    
    一个数组，其中包含系统中支持指定功能的所有设备。
    
  * `SupportedModes` 获取所有支持的访问模式
    
    属性类型： [`AccessMode`](./../enumerations/AccessMode.md) 数组
    
* 方法
  * `LoadProfile` 加载配置文件初始化基础设备
    
    方法使用指定的配置文档初始化基础设施。
    
  * `Cleanup` 关闭所选设备并释放分配的资源
    
    如果在调用此方法时正在运行任何功能，则该功能将被停止，然后将释放该功能使用的所有资源。
    只释放分配的资源，调用该方法后可以复用实例本身，可以选择设备，通过 `SelectedDevices` 再次与该实例捆绑在一起。
    
  * `Dispose` 销毁实例
    
    如果调用此方法时有任何功能正在运行，则该功能将被停止，然后将释放该功能使用的所有资源，最后将销毁该实例。
