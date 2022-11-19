# `WaveformAiCtrl` 定义在缓冲模式下采集模拟数据的接口

`WaveformAiCtrl` `extends` [`AiCtrlBase`](./AiCtrlBase.md)

* 方法
  * `Prepare`
  * `Start`
  * `Stop`
  * `Release`
  * `GetData` (**重载**)获取已存储在内部缓冲区中的模拟数据
    
    可以返回原始数据（设备定义的格式）或缩放数据（根据信道的 `ValueRange` 从原始数据转换而来）。
    
    * `ErrorCode GetData(int count, short[] rawData)`
    * `ErrorCode GetData(int count, int[] rawData)`
    * `ErrorCode GetData(int count, double[] scaledData)`
      
      1. `count` 计数：(32-bit) 要读取的样本数
      2. Data:
        * `rawData` 
        * `scaledData` 缩放数据：(64-bit `double` 数组)缩放数据根据信道的值范围(`ValueRange`)从原始数据（设备格式）转换而来的缩放格式的数据
      
    * `ErrorCode GetData(int count, short[] rawData, int timeout, IntByRef returned)`
    * `ErrorCode GetData(int count, int[] rawData, int timeout, IntByRef returned)`
    * `ErrorCode GetData(int count, double[] scaledData, int timeout, IntByRef returned)`
      
      1. `count` 计数：(32-bit) 要读取的样本数
      2. Data:
          * `rawData`
          * `scaledData` 缩放数据：(64-bit `double` 数组)缩放数据根据信道的值范围(`ValueRange`)从原始数据（设备格式）转换而来的缩放格式的数据
      3. `timeout` 超时时间：(`ms`毫秒为单位)
          * `>0` 读取数据超时等待时间
          * `=0` 读取所有可用数据读入 `scaledData` 数组，并立即返回
          * `=-1` 读取的超时时间间隔将不会过去
      4. `returned` (32-bit `int` 指针)该整数接收读取的样本计数
      
      > **备注：**
      > 
      > 调用的时候确保 `scaledData` 足够大，可以接收所有必需的样本。
      > 
      > 该方法将从内部缓冲区读取最大计数的样本。
      > 如果可用数据小于指定计数，则该方法将最多等待超时毫秒以获取新数据，否则，该方法将读取样本计数并立刻返回。
      > 可用数据计数可从 `DataReady` 和 `Stopped` 事件参数 `args` 中获得。
      > `returned` 参数指示当方法返回时， `scaledData` 数组中读取了多少样本。
      
    * `ErrorCode GetData(int count, short[] rawData, int timeout, IntByRef returned, DoubleByRef startTime, IntByRef markCount, DataMark[] markBuf)`
    * `ErrorCode GetData(int count, int[] rawData, int timeout, IntByRef returned, DoubleByRef startTime, IntByRef markCount, DataMark[] markBuf)`
    * `ErrorCode GetData(int count, double[] scaledData, int timeout, IntByRef returned, DoubleByRef startTime, IntByRef markCount, DataMark[] markBuf)`
      
      1. `count`
      2. Data:
          * `rawData`
          * `scaledData`
      3. `timeout`
      4. `returned`
      5. `startTime`
      6. `markCount`
      7. `markBuf`

  * `LoadProfile`
  * `Cleanup`
  * `Dispose`
