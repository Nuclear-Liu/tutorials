# Questions and Exercises: Interfaces _问题和练习：接口_


## Questions _问题_


* At the beginning of this lesson, you learned that the core collection interfaces are organized into two distinct inheritance trees. 
  One interface in particular is not considered to be a true `Collection`, and therefore sits at the top of its own tree. 
  What is the name of this interface?

* 在本课开始时，您了解到核心集合接口被组织成两个不同的继承树。
  特别是一个接口不被认为是一个真正的 `Collection` ，因此它位于它自己的树的顶部。
  这个接口叫什么名字？

* Each interface in the collections framework is declared with the `<E>` syntax, which tells you that it is generic. 
  When you declare a `Collection` instance, what is the advantage of specifying the type of objects that it will contain?

* 集合框架中的每个接口都使用 `<E>` 语法声明，这告诉您它是通用的。
  当您声明一个 `Collection` 实例时，指定它将包含的对象类型有什么好处？

* What interface represents a collection that does not allow duplicate elements?

* 什么接口代表不允许重复元素的集合？

* What interface forms the root of the collections hierarchy?

* 什么接口构成了集合层次结构的根？

* What interface represents an ordered collection that may contain duplicate elements?

* 什么接口表示可能包含重复元素的有序集合？

* What interface represents a collection that holds elements prior to processing?

* 什么接口代表在处理之前保存元素的集合？

* What interface represents a type that maps keys to values?

* 什么接口表示将键映射到值的类型？

* What interface represents a double-ended queue?

* 什么接口代表双端队列？

* Name three different ways to iterate over the elements of a `List`.

* 列举三种不同的方式来迭代 `List` 的元素。

* True or False: Aggregate operations are mutative operations that modify the underlying collection.

* 对或错：聚合操作是修改基础集合的可变操作。


## Exercises _练习_


* Write a program that prints its arguments in random order. 
  Do not make a copy of the argument array. 
  Demonstrate how to print out the elements using both streams and the traditional enhanced for statement.

* 编写一个以随机顺序打印其参数的程序。
  不要复制参数数组。
  演示如何使用流和传统的增强 for 语句打印出元素。

* Take the [FindDups](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/FindDups.java) example and modify it to use a `SortedSet` instead of a `Set`. 
  Specify a `Comparator` so that case is ignored when sorting and identifying set elements.

* 以 [FindDups](./../examples/FindDups.java) 示例为例，将其修改为使用 `SortedSet` 而不是 `Set` 。
  指定一个 `Comparator` ，以便在排序和识别集合元素时忽略大小写。

* Write a method that takes a `List<String>` and applies [`String.trim`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#trim--) to each element.

* 编写一个采用 `List<String>` 并将 [`String.trim`](https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#trim--) 应用于每个元素的方法。

* Consider the four core interfaces, `Set`, `List`, `Queue`, and `Map`. 
  For each of the following four assignments, specify which of the four core interfaces is best-suited, and explain how to use it to implement the assignment.

* 考虑四个核心接口， `Set`、 `List`、 `Queue` 和 `Map` 。
  For each of the following four assignments, specify which of the four core interfaces is best-suited, and explain how to use it to implement the assignment.

   * Whimsical Toys Inc (WTI) needs to record the names of all its employees. 
     Every month, an employee will be chosen at random from these records to receive a free toy.
   
   * Whimsical Toys Inc (WTI) 需要记录其所有员工的姓名。
     每个月，都会从这些记录中随机选择一名员工来获得免费玩具。
   
   * WTI has decided that each new product will be named after an employee but only first names will be used, and each name will be used only once. 
     Prepare a list of unique first names.
      
   * WTI 已决定每个新产品都将以员工的名字命名，但只使用名字，并且每个名字只能使用一次。
     准备一个唯一名字的列表。
   
   * WTI decides that it only wants to use the most popular names for its toys. 
     Count up the number of employees who have each first name.
      
   * WTI 决定只想为其玩具使用最流行的名称。
     计算拥有每个名字的员工人数。
   
   * WTI acquires season tickets for the local lacrosse team, to be shared by employees. 
     Create a waiting list for this popular sport.
   
   * WTI获取的季票为当地曲棍球队，由员工共享。
     为这项受欢迎的运动创建一个等候名单。


[Check your answers.](https://docs.oracle.com/javase/tutorial/collections/interfaces/QandE/answers.html)


[Check your answers.](./answers.md)
