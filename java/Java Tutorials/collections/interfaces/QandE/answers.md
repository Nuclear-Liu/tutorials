# Answers to Questions and Exercises: _问题和练习的答案：_


## Questions _问题_


* Question: At the beginning of this lesson, you learned that the core collection interfaces are organized into two distinct inheritance trees. 
  One interface in particular is not considered to be a true `Collection`, and therefore sits at the top of its own tree. 
  What is the name of this interface?

  Answer: `Map`

* 问题：在本课开始时，您了解到核心集合接口被组织成两个不同的继承树。
  特别是一个接口不被认为是一个真正的 `Collection` ，因此它位于它自己的树的顶部。
  这个接口叫什么名字？

  答案： `Map`

* Question: Each interface in the collections framework is declared with the `<E>` syntax, which tells you that it is generic. 
  When you declare a `Collection` instance, what is the advantage of specifying the type of objects that it will contain?

  Answer: Specifying the type allows the compiler to verify (at compile time) that the type of object you put into the collection is correct, thus reducing errors at runtime.

* 问题：集合框架中的每个接口都使用 `<E>` 语法声明，这告诉您它是通用的。
  当您声明一个 `Collection` 实例时，指定它将包含的对象类型有什么好处？

  答案：指定类型允许编译器（在编译时）验证您放入集合中的对象类型是否正确，从而减少运行时的错误。

* Question: What interface represents a collection that does not allow duplicate elements?

  Answer: `Set`

* 问题：什么接口代表一个不允许重复元素的集合？

  答案： `Set`

* Question: What interface forms the root of the collections hierarchy?

  Answer: `Collection`

* 问题：什么接口构成了集合层次结构的根？

  答案： `Collection`

* Question: What interface represents an ordered collection that may contain duplicate elements?

  Answer: `List`

* 问题：什么接口表示可能包含重复元素的有序集合？

  答案： `List`

* Question: What interface represents a collection that holds elements prior to processing?

  Answer: `Queue`

* 问题：什么接口表示在处理之前保存元素的集合？

  答案： `Queue`

* Question: What interface represents a type that maps keys to values?

  Answer: `Map`

* 问题：什么接口表示将键映射到值的类型？

  答案： `Map`

* Question: What interface represents a double-ended queue?

  Answer: `Deque`

* 问题：什么接口代表双端队列？

  答案： `Deque`

* Question: Name three different ways to iterate over the elements of a `List`.

  Answer: You can iterate over a `List` using streams, the enhanced `for` statement, or iterators.

* 问题：列举三种不同的方式来迭代 `List`的元素。

  答案：您可以使用流、增强的 `for` 语句或迭代器来迭代 `List` 。

* Question: True or False: Aggregate operations are mutative operations that modify the underlying collection.

  Answer: False. 
  Aggregate operations do not mutate the underlying collection. 
  In fact, you must be careful to never mutate a collection while invoking its aggregate operations. 
  Doing so could lead to concurrency problems should the stream be changed to a parallel stream at some point in the future. 

* 问题：对或错：聚合操作是修改基础集合的可变操作。

  答案：错误。
  聚合操作不会改变底层集合。
  事实上，在调用集合操作时，您必须小心不要改变集合。
  如果将来某个时间将流更改为并行流，这样做可能会导致并发问题。


## Exercises _练习_


* Exercise: Write a program that prints its arguments in random order. 
  Do not make a copy of the argument array. 
  Demonstrate how to print out the elements using both streams and the traditional enhanced for statement.
  
  Answer: 

* 练习：编写一个以随机顺序打印其参数的程序。
  不要复制参数数组。
  演示如何使用流和传统的增强 for 语句打印出元素。

  答案：

    ```java
    import java.util.*;
    
    public class Ran {
    
        public static void main(String[] args) {
            
            // Get and shuffle the list of arguments
            List<String> argList = Arrays.asList(args);
            Collections.shuffle(argList);
    
            // Print out the elements using JDK 8 Streams
            argList.stream()
            .forEach(e->System.out.format("%s ",e));
    
            // Print out the elements using for-each
            for (String arg: argList) {
                System.out.format("%s ", arg);
            }
    
            System.out.println();
        }
    }
    ```

* Exercise: Take the [`FindDups`](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/FindDups.java) example and modify it to use a `SortedSet` instead of a `Set`. 
  Specify a `Comparator` so that case is ignored when sorting and identifying set elements.

  Answer: 

* 练习：以 [`FindDups`](./../examples/FindDups.java) 为例，修改它以使用 `SortedSet` 而不是 `Set`。
  指定一个 `Comparator` ，以便在排序和识别集合元素时忽略大小写。

  答案：

    ```java
    import java.util.*;
    
    public class FindDups {
    
        static final Comparator<String> IGNORE_CASE_ORDER
                = new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        };
    
        public static void main(String[] args) {
            
            SortedSet<String> s = new TreeSet<String>(IGNORE_CASE_ORDER);
            for (String a : args) {
                s.add(a);
            }
            System.out.println(s.size() + " distinct words: " + s);
        }
    }
    ```

* Exercise: Write a method that takes a `List<String>` and applies `String.trim` to each element.

  Answer:
  
  The enhanced `for` statement does not allow you to modify the `List`. 
  Using an instance of the `Iterator` class allows you to delete elements, but not replace an existing element or add a new one. 
  That leaves `ListIterator`: 

* 练习：编写一个采用 `List<String>` 并将 `String.trim` 应用于每个元素的方法。

  答案：

  增强的 `for` 语句不允许您修改 `List` 。
  使用 `Iterator` 类的实例允许您删除元素，但不能替换现有元素或添加新元素。
  这留下了 `ListIterator` ：

    ```java
    import java.util.*;
    
    public class ListTrim {
        static void listTrim(List<String> strings) {
            for (ListIterator<String> lit = strings.listIterator(); lit.hasNext(); ) {
                lit.set(lit.next().trim());
            }
        }
    
        public static void main(String[] args) {
            List<String> l = Arrays.asList(" red ", " white ", " blue ");
            listTrim(l);
            for (String s : l) {
                System.out.format("\"%s\"%n", s);
            }
        }
    }
    ```

* Exercise: Consider the four core interfaces, `Set`, `List`, `Queue`, and `Map`. 
  For each of the following four assignments, specify which of the four core interfaces is best-suited, and explain how to use it to implement the assignment.

  Answers:

* 练习：考虑四个核心接口，`Set`、`List`、`Queue`和`Map`。
  对于以下四个分配中的每一个，指定四个核心接口中的哪一个最适合，并说明如何使用它来实现分配。

  答案：

  * Whimsical Toys Inc (WTI) needs to record the names of all its employees. 
    Every month, an employee will be chosen at random from these records to receive a free toy.

    Use a `List`. 
    Choose a random employee by picking a number between `0` and `size()-1`.

  * Whimsical Toys Inc (WTI) 需要记录其所有员工的姓名。
    每个月，都会从这些记录中随机选择一名员工来获得免费玩具。

    使用 `List` 。
    通过选择一个介于 `0` 和 `size()-1` 之间的数字来随机选择一名员工。

  * WTI has decided that each new product will be named after an employee — but only first names will be used, and each name will be used only once. 
    Prepare a list of unique first names.

    Use a `Set`. 
    Collections that implement this interface don't allow the same element to be entered more than once.

  * WTI 已决定每个新产品都将以员工的名字命名——但只会使用名字，而且每个名字只能使用一次。
    准备一个唯一名字的列表。

    使用 `Set` 。
    实现此接口的集合不允许多次输入相同的元素。

  * WTI decides that it only wants to use the most popular names for its toys. 
    Count up the number of employees who have each first name.

    Use a `Map`, where the keys are first names, and each value is a count of the number of employees with that first name.

  * WTI 决定只想为其玩具使用最流行的名称。
    计算拥有每个名字的员工人数。

    使用 `Map` ，其中键是名字，每个值是具有该名字的员工数量的计数。

  * WTI acquires season tickets for the local lacrosse team, to be shared by employees. 
    Create a waiting list for this popular sport.

    Use a `Queue`. 
    Invoke `add()` to add employees to the waiting list, and `remove()` to remove them.

  * WTI获取的季票为当地曲棍球队，由员工共享。
    为这项受欢迎的运动创建一个等候名单。

    使用 `Queue` 。
    调用 `add()` 将员工添加到等候名单，并调用 `remove()` 删除他们。
