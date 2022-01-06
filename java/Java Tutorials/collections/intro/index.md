# Lesson: Introduction to Collections _集合简介_


A collection — sometimes called a container — is simply an object that groups multiple elements into a single unit. 
Collections are used to store, retrieve, manipulate, and communicate aggregate data. 
Typically, they represent data items that form a natural group, such as a poker hand (a collection of cards), a mail folder (a collection of letters), or a telephone directory (a mapping of names to phone numbers). 
If you have used the Java programming language — or just about any other programming language — you are already familiar with collections.


集合（有时称为容器）只是一个将多个元素组合成一个单元的对象。
集合用于存储、检索、操作和交流聚合数据。
通常，它们表示形成自然组的数据项，例如扑克手（卡片的集合）、邮件文件夹（信件的集合）或电话簿（姓名到电话号码的映射）。
如果您使用过 Java 编程语言 —— 或几乎任何其他编程语言 —— 您已经熟悉集合。


## What Is a Collections Framework? _什么是集合框架？_


A collections framework is a unified architecture for representing and manipulating collections. 
All collections frameworks contain the following:


集合框架是用于表示和操作集合的统一架构。
所有集合框架都包含以下内容：


* **Interfaces**: These are abstract data types that represent collections. 
  Interfaces allow collections to be manipulated independently of the details of their representation. 
  In object-oriented languages, interfaces generally form a hierarchy.

* **Interfaces**: 这些是代表集合的抽象数据类型。
  接口允许独立于其表示的细节来操作集合。
  在面向对象的语言中，接口通常形成层次结构。

* **Implementations**: These are the concrete implementations of the collection interfaces. 
  In essence, they are reusable data structures.

* **Implementations**: 这些是集合接口的具体实现。
  本质上，它们是可重用的数据结构。

* **Algorithms**: These are the methods that perform useful computations, such as searching and sorting, on objects that implement collection interfaces. 
  The algorithms are said to be polymorphic: that is, the same method can be used on many different implementations of the appropriate collection interface. 
  In essence, algorithms are reusable functionality.

* **Algorithms**: 这些是对实现集合接口的对象执行有用计算（例如搜索和排序）的方法。
  这些算法被称为是多态的：也就是说，相同的方法可以用于适当集合接口的许多不同实现。
  本质上，算法是可重用的功能。


Apart from the Java Collections Framework, the best-known examples of collections frameworks are the C++ Standard Template Library (STL) and Smalltalk's collection hierarchy. 
Historically, collections frameworks have been quite complex, which gave them a reputation for having a steep learning curve. 
We believe that the Java Collections Framework breaks with this tradition, as you will learn for yourself in this chapter.


除了 Java 集合框架，集合框架最著名的例子是 C++ 标准模板库 (STL) 和 Smalltalk 的集合层次结构。
从历史上看，集合框架非常复杂，这使它们以具有陡峭的学习曲线而闻名。
我们相信 Java 集合框架打破了这一传统，您将在本章中自己学习。


## Benefits of the Java Collections Framework _Java 集合框架的好处_


The Java Collections Framework provides the following benefits:


Java 集合框架提供以下好处：


* **Reduces programming effort**: By providing useful data structures and algorithms, the Collections Framework frees you to concentrate on the important parts of your program rather than on the low-level "plumbing" required to make it work. 
  By facilitating interoperability among unrelated APIs, the Java Collections Framework frees you from writing adapter objects or conversion code to connect APIs.

* **减少编程工作**: 通过提供有用的数据结构和算法，集合框架让您可以专注于程序的重要部分，而不是使其工作所需的低级“管道”。
  通过促进无关 API 之间的互操作性，Java 集合框架使您无需编写适配器对象或转换代码来连接 API。

* **Increases program speed and quality**: This Collections Framework provides high-performance, high-quality implementations of useful data structures and algorithms. 
  The various implementations of each interface are interchangeable, so programs can be easily tuned by switching collection implementations. 
  Because you're freed from the drudgery of writing your own data structures, you'll have more time to devote to improving programs' quality and performance.

* **提高程序速度和质量**: 这个集合框架提供了有用的数据结构和算法的高性能、高质量的实现。
  每个接口的各种实现是可以互换的，因此可以通过切换集合实现轻松调整程序。
  因为您从编写自己的数据结构的苦差事中解脱出来，所以您将有更多时间致力于提高程序的质量和性能。

* **Allows interoperability among unrelated APIs**: The collection interfaces are the vernacular by which APIs pass collections back and forth. 
  If my network administration API furnishes a collection of node names and if your GUI toolkit expects a collection of column headings, our APIs will interoperate seamlessly, even though they were written independently.

* **允许无关 API 之间的互操作性**: 集合接口是 API 来回传递集合的术语。
  如果我的网络管理 API 提供一组节点名称，并且如果您的 GUI 工具包需要一组列标题，我们的 API 将无缝互操作，即使它们是独立编写的。

* **Reduces effort to learn and to use new APIs**: Many APIs naturally take collections on input and furnish them as output. 
  In the past, each such API had a small sub-API devoted to manipulating its collections. 
  There was little consistency among these ad hoc collections sub-APIs, so you had to learn each one from scratch, and it was easy to make mistakes when using them. 
  With the advent of standard collection interfaces, the problem went away.

* **减少学习和使用新 API 的工作量**: 许多 API 自然地将集合作为输入并将其提供为输出。
  过去，每个这样的 API 都有一个专门用于操作其集合的小型子 API。
  这些临时集合子 API 之间几乎没有一致性，因此您必须从头开始学习每个 API，并且在使用它们时很容易出错。
  随着标准集合接口的出现，问题就迎刃而解了。

* **Reduces effort to design new APIs**: This is the flip side of the previous advantage. 
  Designers and implementers don't have to reinvent the wheel each time they create an API that relies on collections; instead, they can use standard collection interfaces.

* **减少设计新 API 的工作量**: 这是之前优势的另一面。
  设计者和实施者不必在每次创建依赖于集合的 API 时重新发明轮子；相反，他们可以使用标准的集合接口。

* **Fosters software reuse**: New data structures that conform to the standard collection interfaces are by nature reusable. 
  The same goes for new algorithms that operate on objects that implement these interfaces.

* **促进软件重用**: 符合标准集合接口的新数据结构本质上是可重用的。
  对实现这些接口的对象进行操作的新算法也是如此。
