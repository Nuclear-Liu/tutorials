# `AiFeatures` 表示模拟输入功能的只读属性集合

* 属性
    * `Resolution` 获取 A/D 转换器的分辨率（以 `bit` 为单位
    * `DataSize` 获取所选设备数据大小（以字节为单位）
    * `DataMask` 获取所选设备的数据掩码
    * `TimestampResolution` 获取所选设备的时间戳分辨率（以秒为单位）
    * `ChannelCountMax` 获取所选设备的最大信道计数
    * `ConnectionTypes` 获取所选设备支持的连接类型
    * `OverallConnection` 获取一个值，该值指示是否必须将所选设备的所有模拟输入信道设置为相同的连接类型
    * `ValueRanges` 获取所选设备支持的值范围类型
    * `OverallValueRange` 获取一个值，该值指示所选设备的所有模拟输入信道是否必须设置为相同的值范围 (`ValueRange`)
    * `BurnoutReturnTypes` 获取信道烧坏时所选设备支持的返回值类型
    * `CouplingTypes` 获取所选这杯支持的耦合类型
    * `IepeTypes` 获取所选设备支持的 IEPE 类型
    * `ImpedanceTypes` 获取所选设备支持的阻抗类型
    * `FilterTypes` 获取所选设备支持的筛选器类型
    * `ThermoSupported` 获取一个值，该值指示所选设备是否支持温度测量
    * `CjcChannels` 获取支持测量的信道的信道号
    * `BufferedAiSupported` 获取一个值，该值指示所选设备是否支持缓冲模拟输入
    * `SamplingMethod` 获取缓冲区模拟输入中使用的所选设备支持的采样方法
    * `ChannelStartBase` 获取缓冲模拟输入中使用的开始信道的间隔
    * `ChannelCountBase` 获取缓冲模拟输入中使用的信道计数间隔
    * `ConvertClockSources` 获取缓冲模拟输入中使用的所选设备的支持的转换时钟源
    * `ConvertClockRange` 获取所选设备支持的转换始终范围，用于缓冲模拟输入
    * `TriggerCount` 获取缓冲模拟输入采样支持的触发器计数
    * `Retriggerable` 获取一个值，该值指示所选设备是否支持重新触发功能
    * `TriggerFilterTypes` 获取所选设备的触发器支持的筛选器类型
    * `TriggerSources` 获取所选设备的触发器 `0` 支持的信号源
    * `TriggerActions` 获取所选设备的触发器 `0` 支持的操作
    * `TriggerDelayRange` 获取触发器操作作为“延迟启动”或“延迟停止”时使用的触发器 `0` 的延迟计数范围
    * `Trigger1Sources` 获取所选设备的触发器 `1` 支持的信号源
    * `Trigger1Actions` 获取所选设备的触发器 `1` 支持的操作
    * `Trigger1DelayRange` 获取触发器 `1` 操作为“延迟启动”或“延迟停止”时使用的触发器 `1` 的延迟计数范围
    * `Trigger2Sources` 获取所选设备的触发器 `2` 支持的信号源
    * `Trigger2Actions` 获取所选设备的触发器 `2` 支持的操作
    * `Trigger2DelayRange` 获取触发器 `2` 操作为“延迟启动”或“延迟停止”时使用的触发器 `2` 的延迟计数范围
    * `Trigger3Sources` 获取所选设备的触发器 `3` 支持的信号源
    * `Trigger3Actions` 获取所选设备的触发器 `3` 支持的操作
    * `Trigger3DelayRange` 获取触发器 `3` 操作为“延迟启动”或“延迟停止”时使用的触发器 `3` 的延迟计数范围
* 属性
    * `` 



