# The Set Interface


A [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) is a [`Collection`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html) that cannot contain duplicate elements. 
It models the mathematical set abstraction. 
The `Set` interface contains only methods inherited from `Collection` and adds the restriction that duplicate elements are prohibited. 
`Set` also adds a stronger contract on the behavior of the `equals` and `hashCode` operations, allowing `Set` instances to be compared meaningfully even if their implementation types differ. 
Two `Set` instances are equal if they contain the same elements.


[`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) 是一个不能包含重复元素的 [`Collection`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html)。
它对数学集合抽象进行建模。
`Set` 接口只包含继承自 `Collection` 的方法，并添加了禁止重复元素的限制。
`Set` 还为 `equals` 和 `hashCode` 操作的行为添加了更强的契约，即使它们的实现类型不同，也允许有意义地比较 `Set` 实例。
如果两个 `Set` 实例包含相同的元素，则它们是相等的。


The Java platform contains three general-purpose `Set` implementations: `HashSet`, `TreeSet`, and `LinkedHashSet`. 
[`HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html), which stores its elements in a hash table, is the best-performing implementation; however it makes no guarantees concerning the order of iteration. 
[`TreeSet`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html), which stores its elements in a red-black tree, orders its elements based on their values; it is substantially slower than `HashSet`. 
[`LinkedHashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html), which is implemented as a hash table with a linked list running through it, orders its elements based on the order in which they were inserted into the set (insertion-order). 
`LinkedHashSet` spares its clients from the unspecified, generally chaotic ordering provided by `HashSet` at a cost that is only slightly higher.


Java 平台包含三个通用的 `Set` 实现： `HashSet`、 `TreeSet` 和 `LinkedHashSet` 。
[`HashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html) 将其元素存储在哈希表中，是性能最好的实现；但是它不保证迭代的顺序。
[`TreeSet`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html) 将其元素存储在红黑树中，根据元素的值对其进行排序；它比 `HashSet` 慢得多。
[`LinkedHashSet`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashSet.html) 实现为一个哈希表，其中包含一个链表，它根据元素插入集合的顺序（插入顺序）对其元素进行排序。
`LinkedHashSet` 将它的客户端从 `HashSet` 提供的未指定的、通常混乱的排序中解放出来，但代价只是略高一些。


Here's a simple but useful `Set` idiom. 
Suppose you have a `Collection`, `c`, and you want to create another `Collection` containing the same elements but with all duplicates eliminated. 
The following one-liner does the trick.


这是一个简单但有用的 `Set` 习语。
假设您有一个 `Collection`， `c`，并且您想创建另一个包含相同元素但消除所有重复项的 `Collection` 。
下面的单行代码可以解决问题。


`Collection<Type> noDups = new HashSet<Type>(c);`


It works by creating a `Set` (which, by definition, cannot contain duplicates), initially containing all the elements in `c`. 
It uses the standard conversion constructor described in the [The Collection Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/collection.html) section.


它的工作原理是创建一个 `Set` （根据定义，它不能包含重复项），最初包含 `c` 中的所有元素。
它使用 [The Collection Interface](collection.md) 部分中描述的标准转换构造函数。


Or, if using JDK 8 or later, you could easily collect into a `Set` using aggregate operations: 


或者，如果使用 JDK 8 或更高版本，您可以使用聚合操作轻松地收集到一个 `Set` 中： 


```
c.stream()
.collect(Collectors.toSet()); // no duplicates
```


Here's a slightly longer example that accumulates a `Collection` of names into a `TreeSet`: 


这是一个稍长的示例，它将名称的 `Collection` 累积到 `TreeSet` 中：


```
Set<String> set = people.stream()
.map(Person::getName)
.collect(Collectors.toCollection(TreeSet::new));
```


And the following is a minor variant of the first idiom that preserves the order of the original collection while removing duplicate elements:


以下是第一个惯用语的一个次要变体，它在删除重复元素的同时保留了原始集合的顺序：


```
Collection<Type> noDups = new LinkedHashSet<Type>(c);
```


The following is a generic method that encapsulates the preceding idiom, returning a `Set` of the same generic type as the one passed.


下面是一个通用方法，它封装了前面的习语，返回一个与传递的通用类型相同的 `Set` 。


```
public static <E> Set<E> removeDups(Collection<E> c) {
    return new LinkedHashSet<E>(c);
}
```


## Set Interface Basic Operations _Set 接口基本操作_


The `size` operation returns the number of elements in the `Set` (its cardinality). 
The `isEmpty` method does exactly what you think it would. 
The `add` method adds the specified element to the `Set` if it is not already present and returns a boolean indicating whether the element was added. 
Similarly, the `remove` method removes the specified element from the `Set` if it is present and returns a boolean indicating whether the element was present. 
The `iterator` method returns an `Iterator` over the `Set`.


`size` 操作返回 `Set` 中元素的数量（它的基数）。
`isEmpty` 方法完全符合您的想法。
`add` 方法将指定的元素添加到 `Set` （如果它不存在）并返回一个布尔值，指示该元素是否已添加。
类似地， `remove` 方法从 `Set` 中删除指定的元素（如果它存在）并返回一个布尔值，指示该元素是否存在。
`iterator` 方法在 `Set` 上返回一个 `Iterator` 。


The following [program](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/FindDups.java) prints out all distinct words in its argument list. 
Two versions of this program are provided. 
The first uses JDK 8 aggregate operations. 
The second uses the for-each construct.


下面的 [program](examples/FindDups.java) 打印出其参数列表中的所有不同单词。
提供了该程序的两个版本。
第一个使用 JDK 8 聚合操作。
第二个使用 for-each 结构。


Using JDK 8 Aggregate Operations: 


使用 JDK 8 聚合操作：


```java
import java.util.*;
import java.util.stream.*;

public class FindDups {
    public static void main(String[] args) {
        Set<String> distinctWords = Arrays.asList(args).stream()
		.collect(Collectors.toSet()); 
        System.out.println(distinctWords.size()+ 
                           " distinct words: " + 
                           distinctWords);
    }
}
```


Using the `for-each` Construct:


使用 `for-each` 结构：


```java
import java.util.*;

public class FindDups {
    public static void main(String[] args) {
        Set<String> s = new HashSet<String>();
        for (String a : args)
               s.add(a);
        System.out.println(s.size() + " distinct words: " + s);
    }
}
```


Now run either version of the program.


现在运行该程序的任一版本。


```
java FindDups i came i saw i left
```


The following output is produced:


产生以下输出：


```text
4 distinct words: [left, came, saw, i]
```


Note that the code always refers to the `Collection` by its interface type (`Set`) rather than by its implementation type. 
This is a _strongly_ recommended programming practice because it gives you the flexibility to change implementations merely by changing the constructor. 
If either of the variables used to store a collection or the parameters used to pass it around are declared to be of the `Collection`'s implementation type rather than its interface type, all such variables and parameters must be changed in order to change its implementation type.


请注意，代码始终通过其接口类型（ `Set` ）而不是其实现类型来引用 `Collection` 。
这是*强烈*推荐的编程实践，因为它使您可以灵活地仅通过更改构造函数来更改实现。
如果用于存储集合的变量或用于传递集合的参数中的任何一个被声明为“集合”的实现类型而不是其接口类型，则必须更改所有此类变量和参数以更改其实现类型。


Furthermore, there's no guarantee that the resulting program will work. 
If the program uses any nonstandard operations present in the original implementation type but not in the new one, the program will fail. 
Referring to collections only by their interface prevents you from using any nonstandard operations.


此外，无法保证生成的程序会起作用。
如果程序使用原始实现类型中存在的任何非标准操作，但在新实现类型中没有，程序将失败。
仅通过其接口引用集合可防止您使用任何非标准操作。


The implementation type of the `Set` in the preceding example is `HashSet`, which makes no guarantees as to the order of the elements in the `Set`. 
If you want the program to print the word list in alphabetical order, merely change the `Set`'s implementation type from `HashSet` to `TreeSet`. 
Making this trivial one-line change causes the command line in the previous example to generate the following output.


前面例子中 `Set` 的实现类型是 `HashSet` ，不保证 `Set` 中元素的顺序。
如果您希望程序按字母顺序打印单词列表，只需将 `Set` 的实现类型从 `HashSet` 更改为 `TreeSet` 。
进行这种微不足道的单行更改会导致上一个示例中的命令行生成以下输出。


```text
java FindDups i came i saw i left

4 distinct words: [came, i, left, saw]
```


## Set Interface Bulk Operations _Set 接口批量操作_


Bulk operations are particularly well suited to `Set`s; when applied, they perform standard set-algebraic operations. 
Suppose `s1` and `s2` are sets. 
Here's what bulk operations do:


批量操作特别适合 `Set` ；应用时，它们执行标准的集合代数运算。
假设 `s1` 和 `s2` 是集合。
以下是批量操作的作用：


* `s1.containsAll(s2)` — returns `true` if `s2` is a **subset** of `s1`. (`s2` is a subset of `s1` if set `s1` contains all of the elements in `s2`.)

* `s1.containsAll(s2)` — 如果 `s2` 是 `s1` 的子集，则返回 `true` 。 （ `s2` 是 `s1` 的子集，如果集合 `s1` 包含 `s2` 中的所有元素。）

* `s1.addAll(s2)` — transforms `s1` into the union of `s1` and `s2`. (The union of two sets is the set containing all of the elements contained in either set.)

* `s1.addAll(s2)` — 将 `s1` 转换为 `s1` 和 `s2` 的并集。 （两个集合的并集是包含任一集合中包含的所有元素的集合。）

* `s1.retainAll(s2)` — transforms `s1` into the intersection of `s1` and `s2`. (The intersection of two sets is the set containing only the elements common to both sets.)

* `s1.retainAll(s2)` — 将 `s1` 转换为 `s1` 和 `s2` 的交集。 （两个集合的交集是只包含两个集合共有的元素的集合。）

* `s1.removeAll(s2)` — transforms `s1` into the (asymmetric) set difference of `s1` and `s2`. (For example, the set difference of `s1` minus `s2` is the set containing all of the elements found in `s1` but not in `s2`.)

* `s1.removeAll(s2)` — 将 `s1` 转换为 `s1` 和 `s2` 的（非对称）集差。 （例如，`s1` 减去 `s2` 的集合差是包含在 `s1` 中找到但不在 `s2` 中找到的所有元素的集合。）


To calculate the union, intersection, or set difference of two sets _nondestructively_ (without modifying either set), the caller must copy one set before calling the appropriate bulk operation. 
The following are the resulting idioms.


为了非破坏性地计算两个集合的并集、交集或集合差（不修改任何一个集合），调用者必须在调用适当的批量操作之前复制一个集合。
以下是由此产生的习语。


```text
Set<Type> union = new HashSet<Type>(s1);
union.addAll(s2);

Set<Type> intersection = new HashSet<Type>(s1);
intersection.retainAll(s2);

Set<Type> difference = new HashSet<Type>(s1);
difference.removeAll(s2);
```


The implementation type of the result `Set` in the preceding idioms is `HashSet`, which is, as already mentioned, the best all-around `Set` implementation in the Java platform. 
However, any general-purpose `Set` implementation could be substituted.


前面习语中结果 `Set` 的实现类型是 `HashSet` ，如前所述，它是Java平台中最好的全能 `Set` 实现。
然而，任何通用的 `Set` 实现都可以被替代。


Let's revisit the `FindDups` program. 
Suppose you want to know which words in the argument list occur only once and which occur more than once, but you do not want any duplicates printed out repeatedly. 
This effect can be achieved by generating two sets — one containing every word in the argument list and the other containing only the duplicates. 
The words that occur only once are the set difference of these two sets, which we know how to compute. 
Here's how [the resulting program](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/FindDups2.java) looks.


让我们重新审视 `FindDups` 程序。
假设您想知道参数列表中哪些词只出现一次，哪些词出现多次，但不希望重复打印任何重复项。
这种效果可以通过生成两个集合来实现 —— 一个包含参数列表中的每个单词，另一个只包含重复项。
只出现一次的词是这两个集合的集合差，我们知道如何计算。
这是 [the resulting program](examples/FindDups2.java) 的样子。


```java
import java.util.*;

public class FindDups2 {
    public static void main(String[] args) {
        Set<String> uniques = new HashSet<String>();
        Set<String> dups    = new HashSet<String>();

        for (String a : args)
            if (!uniques.add(a))
                dups.add(a);

        // Destructive set-difference
        uniques.removeAll(dups);

        System.out.println("Unique words:    " + uniques);
        System.out.println("Duplicate words: " + dups);
    }
}
```


When run with the same argument list used earlier (`i came i saw i left`), the program yields the following output.


当使用之前使用的相同参数列表（ `i came i saw i left` ）运行时，程序产生以下输出。


```text
Unique words:    [left, saw, came]
Duplicate words: [i]
```


A less common set-algebraic operation is the _symmetric set difference_ — the set of elements contained in either of two specified sets but not in both. 
The following code calculates the symmetric set difference of two sets nondestructively.


一个不太常见的集合代数运算是*对称集合差* —— 包含在两个指定集合中的任何一个中但不包含在两个指定集合中的元素集合。
下面的代码非破坏性地计算两个集合的对称集差。


```
Set<Type> symmetricDiff = new HashSet<Type>(s1);
symmetricDiff.addAll(s2);
Set<Type> tmp = new HashSet<Type>(s1);
tmp.retainAll(s2);
symmetricDiff.removeAll(tmp);
```


## Set Interface Array Operations _Set 接口数组操作_


The array operations don't do anything special for `Set`s beyond what they do for any other `Collection`. 
These operations are described in [The Collection Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/collection.html) section.


除了对任何其他 `Collection` 所做的操作之外，数组操作对 `Set` 没有任何特殊作用。
These operations are described in [The Collection Interface](collection.md) section.
