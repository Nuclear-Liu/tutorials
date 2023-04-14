# Summary of Interfaces _接口总结_


The core collection interfaces are the foundation of the Java Collections Framework.


核心集合接口是 Java 集合框架的基础。


The Java Collections Framework hierarchy consists of two distinct interface trees:


Java Collections Framework 层次结构由两个不同的接口树组成：


* The first tree starts with the `Collection` interface, which provides for the basic functionality used by all collections, such as `add` and `remove` methods. 
  Its subinterfaces — `Set`, `List`, and `Queue` — provide for more specialized collections.

* 第一个树以 `Collection` 接口开始，它提供了所有集合使用的基本功能，例如 `add` 和 `remove` 方法。
  它的子接口 —— `Set`、 `List` 和 `Queue` —— 提供了更专业的集合。

* The `Set` interface does not allow duplicate elements. 
  This can be useful for storing collections such as a deck of cards or student records. 
  The `Set` interface has a subinterface, `SortedSet`, that provides for ordering of elements in the set.

* `Set` 接口不允许重复元素。
  这对于存储诸如一副卡片或学生记录之类的集合很有用。
  `Set` 接口有一个子接口 `SortedSet` ，它提供集合中元素的排序。

* The `List` interface provides for an ordered collection, for situations in which you need precise control over where each element is inserted. 
  You can retrieve elements from a `List` by their exact position.

* `List` 接口提供了一个有序集合，用于需要精确控制每个元素的插入位置的情况。
  您可以通过它们的确切位置从 `List` 中检索元素。

* The `Queue` interface enables additional insertion, extraction, and inspection operations. 
  Elements in a `Queue` are typically ordered in on a FIFO basis.

* `Queue` 接口支持额外的插入、提取和检查操作。
  `Queue` 中的元素通常以 FIFO 为基础进行排序。

* The `Deque` interface enables insertion, deletion, and inspection operations at both the ends. 
  Elements in a `Deque` can be used in both LIFO and FIFO.

* `Deque` 接口支持两端的插入、删除和检查操作。
  `Deque` 中的元素可以在 LIFO 和 FIFO 中使用。

* The second tree starts with the `Map` interface, which maps keys and values similar to a `Hashtable`.

* 第二棵树以 `Map` 接口开始，它映射类似于 `Hashtable` 的键和值。

* `Map`'s subinterface, `SortedMap`, maintains its key-value pairs in ascending order or in an order specified by a `Comparator`.

* `Map` 的子接口 `SortedMap` 以升序或按 `Comparator` 指定的顺序维护其键值对。


These interfaces allow collections to be manipulated independently of the details of their representation.


这些接口允许独立于其表示的细节来操作集合。
