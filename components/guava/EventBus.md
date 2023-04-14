# EventBus

EventBus 允许组件之间的发布-订阅式通信，不需要组件之间显式地注册（从而可以彼此识别）。

专门用于显式注册来取代传统的 Java 进程内事件并发。它不是一个通用的发布-订阅系统，也不打算用于进程间通信。

* `@Subscribe` 修饰订阅者消费函数；
* `EventBus.register(Object)` 注册实践监听者；需要确保对象于事件生成器共享一个 `EventBus` 实例
* `EventBus.post(Object)` 将事件对象发售那个给 `EventBus`