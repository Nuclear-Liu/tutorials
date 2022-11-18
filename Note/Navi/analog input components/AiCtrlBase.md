# `AiCtrlBase` 模拟输入功能组件的基类

`AiCtrlBase` `extends` [`DaqCtrlBase`](./../common/DeviceCtrl.md)

将模拟输入的功能分为： 即时模拟输入和缓冲模拟输入两种模式，便于执行各种模拟输入。

* 构造
  * `AiCtrlBase()`
* 属性
  * `Features` 获取 `AiFeatures` 类的对象，该对象管理所选设备支持的所有模拟输入功能
    
    类的对象；可以通过此对象获取所选设备的模拟输入相关功能。如果未选择任何设备，则从此对象获取的特征将为默认值；
    
  * `ChannelCount` 获取可用于获取模拟输入样本的逻辑信道计数；此值取决于设备的信道连接类型
    
    所选设备的逻辑信道计数。
    
    > **备注：**
    > 
    > 此属性值将受设备的信道连接类型的影响。
    > **如果未选择任何设备，将返回 `0` 。**
    
  * `Channels` 获取所选设备的所有模拟输入物理信道的集合
    
    属性类型： [`AiChannel`]() 数组
    
    获取所选设备的所有模拟输入物理信道的集合。
    
    > **备注：**
    > 
    > 每个 `AiChannel` 对象，代表物理信道，使用此对象可以设置信道属性。
    > 
    > **未选择任何设备返回值为 `NULL`**
    
  * `SelectedDevice` 获取所选设备的信息或选择具体指定信息的设备
  * `State` 获取指示 [`ControlState`]() 的控件状态枚举值
  * `Initialized` 获取一个值，该值指示是否已成功选择设备
  * `Device` 获取与后备硬件接口的 `DeviceCtrl` 类的对象
  * `SupportedDevices` 获取支持指定函数的所有设备
  * `SupportedModes` 获取所有支持的访问模式
* 方法
  * `LoadProfile` 加载配置文档以初始化基础设备
  * `Cleanup` 关闭所选设备并释放分配的资源
  * `Dispose` 销毁实例
* 事件
  * ``
