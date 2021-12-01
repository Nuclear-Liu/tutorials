# Advanced _高级_

## Serializing Mocks _序列化模拟_

Mocks can be serialized at any time during their life. 
However, there are some obvious constraints:


Mock 可以在其生命周期中的任何时间序列化。
但是，有一些明显的限制：

---

* All used matchers should be serializable (all genuine EasyMock ones are)
* Recorded parameters should also be serializable


* 所有使用过的匹配器都应该是可序列化的（所有真正的 EasyMock 都是）
* 记录的参数也应该是可序列化的

---

## Multithreading _多线程_

During recording, a mock is **not** thread-safe. 
So a giving mock (or mocks linked to the same `IMocksControl`) can only be recorded from a single thread. 
However, different mocks can be recorded simultaneously in different threads.


在录制期间，模拟不是线程安全的。
所以一个给定的模拟（或链接到同一个 `IMocksControl` 的模拟）只能从单个线程记录。
但是，不同的模拟可以同时记录在不同的线程中。

---

During the replay phase, mocks are by default thread-safe. 
This can be change for a given mock if `makeThreadSafe(mock, false)` is called during the recording phase. 
This can prevent deadlocks in some rare situations.


在重放阶段，默认情况下模拟是线程安全的。
如果在录制阶段调用 `makeThreadSafe(mock, false)` ，则这可以更改给定的模拟。
这可以防止在某些罕见情况下出现死锁。

---

Finally, calling `checkIsUsedInOneThread(mock, true)` on a mock will make sure the mock is used in only one thread and throw an exception otherwise. 
This can be handy to make sure a thread-unsafe mocked object is used correctly.


最后，在模拟上调用 `checkIsUsedInOneThread(mock, true)` 将确保模拟只在一个线程中使用，否则抛出异常。
这可以方便地确保正确使用线程不安全的模拟对象。

---

## OSGi

EasyMock jar can be used as an OSGi bundle. 
It exports `org.easymock`, `org.easymock.internal` and `org.easymock.internal.matchers` packages. 
However, to import the two latter, you need to specify the `poweruser` attribute at `true` (`poweruser=true`). 
These packages are meant to be used to extend EasyMock so they usually don't need to be imported.


EasyMock jar 可以用作 OSGi 包。
它导出 `org.easymock`、`org.easymock.internal` 和 `org.easymock.internal.matchers` 包。
但是，要导入后两者，您需要在 `true` (`poweruser=true`) 处指定 `poweruser` 属性。

---

## Backward Compatibility _向后兼容_

EasyMock 3 still has a Class Extension project (although deprecated) to allow an easier migration from EasyMock 2 to EasyMock 3. 
It is a source not a binary compatibility. 
So the code will need to be recompiled.


EasyMock 3 仍然有一个类扩展项目（虽然已弃用），以允许更轻松地从 EasyMock 2 迁移到 EasyMock 3。
它是一个来源，而不是二进制兼容性。
所以需要重新编译代码。

---

EasyMock 2.1 introduced a callback feature that has been removed in EasyMock 2.2, as it was too complex. 
Since EasyMock 2.2, the `IAnswer` interface provides the functionality for callbacks.


EasyMock 2.1 引入了回调功能，在 EasyMock 2.2 中已删除，因为它太复杂了。
从 EasyMock 2.2 开始， `IAnswer` 接口提供回调功能。

---
