# `clas DaqCtrlBase` DAQNavi 设备组件的基类，实现了设备组件通用的基本功能

* 属性
  * `SelectedDevice`: 获取所选设备的信息或选择具有指定信息的设备。
    * `DeviceInformation getSelectedDevice()`
    * `void setSelectedDevice(DeviceInformation deviceInfo)`
  * `State`: 获取指示组件状态的控件状态枚举值。
    * `ControlState getState()`
  * `Initialized`: 获取一个值，该值指示是否已成功选择设备。
    * `boolean getInitialized()`
  * `Device`: 获取与后备硬件接口的 `DeviceCtrl` 类的对象
    * `DeviceCtrl getDevice()`
  * `SupportedDevices`: 获取支持指定功能的所有设备。
    * `ArrayList<DeviceTreeNode> getSupportedDevices()`
  * `SupportedModes`: 获取所有支持的访问模式。
    * `AccessMode[] getSupportedModes()`
* 方法
  * `ErrorCode LoadProfile(String fileName)`:加载配置文档以初始化基础设备

  * `public boolean CanEditProperty()`: 是否可编辑已选择设备的信息
  
  * `void Cleanup()`: 关闭所选设备并释放分配的资源

    如果调用此方法时有任何函数正在运行，则该函数被停止，然后释放该函数使用的所有资源。
    与 `Dispose()` 不同，只释放分配的资源，调用该方法后可以复用实例本身，可以选择设备，然后 `SelectedDevice` 再次与该实例捆绑在一起。

  * `void Dispose()`: 销毁示例
  
    如果调用此方法时有任何函数正在运行，则该函数被停止，然后释放该函数使用的所有资源，最后销毁该示例。

## `class AiCtrlBase extends DaqCtrlBase` 模拟输入功能组件的基类

模拟输入功能组件的基类，实现模拟输入组件通用的基本功能。
考虑到操作的不同，我们将模拟输入功能划分为两种操作模式：**即时AI(模拟输入)**、**缓冲AI(模拟输入)**

这两种操作模式可以方便执行各种模拟输入：
从单信道、多信道获取数据。
**数据格式**可以选择**原始**或**缩放**，获取模式可以是**同步**或**异步**，可以不是**触发**。

* 事件
  * ``
* 属性
  * `Features`: 获取 [`AiFeatures`]() 类的对象，该对象管理所选设备支持的所有模拟输入功能。
    * `AiFeatures getFeatures()`
  * `ChannelCount`: 获取可用于获取模拟输入样本的逻辑信道计数。此值取决于设备信道连接类型。
    * `int getChannelCount()`
  * `Channels`: 获取所选设备的所有模拟输入物理信道的集合。
    * `AiChannel[] getChannels()`
* 方法

### `public class InstantAiCtrl extends AiCtrlBase` : 定义即时模式模拟数据的接口

即时模拟输入是用于采样模拟数据的软件轮询模式。
数据转换由**软件命令触发**，数据将立即从 ADC 检索。

* 属性
  * `Cjc`: 获取管理 CJC 设置的 `CjcSetting` 类的对象
    * `CjcSetting getCjc()`
* 方法
  * `autoConvertClockRate`
    * `double getAutoConvertClockRate()`
    * `void setAutoConvertClockRate(double value)`
  * `autoConvertChannelStart`
    * `int getAutoConvertChannelStart()`
    * `void setAutoConvertChannelStart(int value)`
  * `autoConvertChannelCount`
    * `int getAutoConvertChannelCount()`
    * `void setAutoConvertChannelCount(int value)`

### `public class WaveformAiCtrl extends AiCtrlBase` 定义在缓冲模式下采集模拟数据的接口

缓冲模拟输入也称为快速模拟输入，它可以高速**传输大量数据**，因此即刻模式无法满足采集速度（采样频率）、数据量。
`WaveformAiCtrl` 提供了属性和方法来操作此批量数据保存和传输。
驱动进程将监视缓冲模拟输入转换的进度，并发送适合的事件来通知用户当前的转换状态，因此在 `WaveformAiCtrl` 类中还定义了缓冲模拟输入的一些事件。

* 属性
  * `Conversion`: 获取 [`Conversion`]() 类的对象，该对象管理 A/D 转换参数，如开始信道、信道计数等
  * `Record`: 获取 `Record` 类的对象，该对象管理数据缓冲区参数，如节长度、节计数等
  * `Trigger`: 获取 `Trigger` 类的对象，该类管理硬件触发器 0 的设置
  * `Trigger1`: 获取 `Trigger` 类的对象，该类管理硬件触发器 1 的设置
  * `Features`: 获取 `AiFeatures` 类的对象，该对象管理所选设备支持的所有模拟输入功能。（继承自 `AiCtrlBase`）
* 方法
  * `ErrorCode Prepare()`: 根据当前设置初始化所选设备以进行缓冲模拟输入操作。
  
    此方法成功执行后，所选设备处于启动缓冲模拟输入的就绪状态，然后可以通过调用 `Start` 方法启动缓冲模拟输入。

  * `ErrorCode Start()`: 从指定的模拟输入信道开始缓冲模拟数据采集。
  
    每次填满数据部分时都会引发 `DataReady` 事件，您可以调用 `GetData` 方法来读取这些新获取的数据。
    当采样到足够指定的数据计数（一个缓冲模拟输入）或调用 `Stop` 方法时，采集将停止。

  * `ErrorCode Stop()`: 停止缓冲模拟输入操作的运行。
  
    如果方法成功执行，将引发 `Stop` 事件。
    稍后可以通过调用 `Start` 方法再次启动缓冲模拟输入操作。

  * `void Release()`: 释放通过准备为缓冲模拟输入操作分配的所有资源。

    如果任何缓冲模拟输入操作仍在运行，此方法将自动停止该操作。

  * `ErrorCode GetData()`: 获取已存储在内部缓冲区中的模拟数据。

    重载
    * `ErrorCode GetData(int count, short[] rawData)`
    * `ErrorCode GetData(int count, int[] rawData)`
    * `ErrorCode GetData(int count, double[] scaledData)`
    
    * `ErrorCode GetData(int count, short[] rawData, int timeout, IntByRef returned)`
    * `ErrorCode GetData(int count, int[] rawData, int timeout, IntByRef returned)`
    * `ErrorCode GetData(int count, double[] scaledData, int timeout, IntByRef returned)`
    
    * `ErrorCode GetData(int count, short[] rawData, int timeout, IntByRef returned, DoubleByRef startTime, IntByRef markCount, DataMark[] markBuf)`
    * `ErrorCode GetData(int count, int[] rawData, int timeout, IntByRef returned, DoubleByRef startTime, IntByRef markCount, DataMark[] markBuf)`
    * `ErrorCode GetData(int count, double[] scaledData, int timeout, IntByRef returned, DoubleByRef startTime, IntByRef markCount, DataMark[] markBuf)`

* 事件
  * `DataReady`: 在填满数据节时发生
    * `void addDataReadyListener(BfdAiEventListener listener)`
    * `void removeDataReadyListener(BfAiEventListener listener)`
  * `Overrun`: 当内部缓冲区已满并且其中一些缓冲区已在流式处理模拟输入中被覆盖时发生
    * `void addOverrunListener(BfdAiEventListener listener)`
    * `void removeOverrunListener(BfdAiEventListener listener)`
  * `CacheOverflow`: 在硬件数据缓存溢出时发生。一般来说，这是因为采样速度比转换速度快。
    * `void addCacheOverflowListener(BfdAiEventListener listener)`
    * `void removeCacheOverflowListener(BfdAiEventListener listener)`
  * `Stopped`: 当通过 `Stop` 方法取消数据采集或在单个缓冲模拟输入中完成有限计数数据采集时发生。
    * `void addStoppedListener(BfdAiEventListener listener)`
    * `void removeStoppedListener(BfAiEventListener listener)`
  * `BurnOut`: 当检测到某些采样信道烧坏时发生。
    * `void addBurnOutListener(BfdAiEventListener listener)`
    * `void removeBurnOutListener(BfdAiEventListener listener)`
  * `TimeStampOverrun`: 当内部时间戳缓冲区已满且其中一些已被覆盖时发生。
    * `void addTimeStampOverrunListener(BfdAiEventListener listener)`
    * `void removeTimeStampOverrunListener(BfdAiEventListener listener)`
  * `TimeStampCacheOverflow`: 在硬件时间戳缓存溢出时发生。
    * `void addTimeStampCacheOverflowListener(BfdAiEventListener listener)`
    * `void removeTimeStampCacheOverflowListener(BfdAiEventListener listener)`
  * `MarkOverrun`: 当内部数据标记缓冲区已满并且其中一些已被覆盖时发生。
    * `void addMarkOverrunListener(BfdAiEventListener listener)`
    * `void removeMarkOverrunListener(BfdAiEventListener listener)`

### `public class BufferedAiCtrl extends AiCtrlBase`
