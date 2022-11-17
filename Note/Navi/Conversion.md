# `Conversion` 提供接口来管理模拟输入、输出的缓冲转换参数，如起始信道、信道数等

用于**管理 A/D 或 D/A 转换参数**，如起始信道、信道数、传唤时钟频率和时钟源等。

* 属性
  * `ClockSource` 获取或设置缓冲模拟输入、输出的时钟源
    
    属性类型：[`SignalDrop`]()
    
    缓冲模拟输入、输出的时钟源可以是内置信号或外部信号，具体取决于设备的功能。
    
    > **备注：**
    > 
    > * `AiFeature.ConvertClockSource` 属性列出了 A/D 转换支持的所有时钟源
    > * `AiFeature.ConvertClockSource` 属性列出了 D/A 转换支持的所有时钟源
    
  * `ClockRate` 获取或设置换从模拟输入、输出的每个信道的时钟速率(以 `Hz` 为单位)
    
    属性类型：`double`
    
    > **备注：**
    > 时钟速率决定了模数(A/D)或数模(D/A)转换发生的频率。
    > 它是每个信道的采样时钟频率。
    > 
    > 时钟速率的最大值取决于硬件。(参考 `AiFeatures.ConvertClockRange` 或 `AoFeatures.ConvertClockRange` 配置时钟频率)
    > 
    > 每个设备都有一个有效的时钟范围，这个范围与设备的 `AiFeatures.SamplingMethod` （或 `AoFeatures.SamplingMethod` 的 D/A 转换）有一定的关系。
    
  * `ChannelStart` 获取或设置要由缓冲模拟输入、输出扫描的开始信道的信道号
    
    属性类型： 32-bit integer
    
    > **备注：**
    > 信道开始的物理信道号。
    >
    > 参考： `AiFeatures.ChannelCountMax` `AoFeatures.ChannelCountMax`
    
  * `ChannelCount` 获取或设置要由缓冲模拟输入、输出扫面的信道个数。
    
    属性类型： 32-bit integer
    
    > **备注：**
    > 信道计数是逻辑信道的计数。
    > 信道计数的值范围取决于所选设备的连接类型。
    