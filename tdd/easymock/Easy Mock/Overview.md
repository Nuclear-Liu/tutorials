## Why _为什么_

> Great testing includes isolation


> 伟大的测试包括隔离


Most parts of a software system do not work in isolation, but collaborate with other parts to get their job done.


软件系统的大多数部分并不是孤立地工作，而是与其他部分协作完成工作。


In a lot of cases, we do not care about using real collaborators implementation in unit testing, as we trust these collaborators.


在很多案例中，我们并不关心在单元测试中使用真正的合作者实现，因为我们信任这些合作者。


`Mock Object`s replace collaborators of the unit under test.


`Mock Object` 替代单元测试下的合作者。


## How _如何_

> Isolation involves Mock Objects


> 隔离涉及 `Mock Object`


To test a unit in isolation or mount a sufficient environment, we have to simulate the collaborators in the test.


为了隔离一个测试单元或安装一个足够的环境，我们必须模拟测试中的合作者。


A `Mock Object` is a test-oriented replacement for a collaborator. 
It is configured to simulate the object that it replaces in a simple way.


`Mock Object` 是为合作者面向测试的替代品。
它被配置为以简单的方式模拟它替换的对象。


In contrast to a `stub`, a `Mock Object` also verifies whether it is used as expected.


与 `stub` 相比， `Mock Object` 还验证它是否按预期使用。


## What _什么_

> EasyMock makes mocking easier


> EasyMock 使模拟更容易


EasyMock has been the first dynamic `Mock Object` generator, relieving users of hand-writing `Mock Object`s, or generating code for them.


EasyMock 是第一个动态 `Mock Object` 生成器，减轻了用户手写 `Mock Object` 或为其生成代码的麻烦。


EasyMock provides `Mock Object`s by generating them on the fly using Java proxy mechanism.


EasyMock 通过使用 Java 代理机制动态生成它们来提供 `Mock Object` 。
