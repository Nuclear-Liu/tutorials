# The Map Interface


A [`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) is an object that maps keys to values. 
A map cannot contain duplicate keys: 
Each key can map to at most one value. 
It models the mathematical _function_ abstraction. 
The `Map` interface includes methods for basic operations (such as `put`, `get`, `remove`, `containsKey`, `containsValue`, `size`, and `empty`), bulk operations (such as `putAll` and `clear`), and collection views (such as `keySet`, `entrySet`, and `values`).


[`Map`](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html) 是将键映射到值的对象。
map 不能包含重复的键：
每个键最多可以映射到一个值。
它对数学*函数*抽象建模。
`Map` 接口包括基本操作的方法（例如 `put`、 `get`、 `remove`、 `containsKey`、 `containsValue`、 `size` 和 `empty` ）、批量操作（例如 `putAll ` 和 `clear` ），以及集合视图（例如 `keySet`、 `entrySet` 和 `values`）。


The Java platform contains three general-purpose `Map` implementations: [`HashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html), [`TreeMap`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html), and [`LinkedHashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html). 
Their behavior and performance are precisely analogous to `HashSet`, `TreeSet`, and `LinkedHashSet`, as described in [The Set Interface](https://docs.oracle.com/javase/tutorial/collections/interfaces/set.html) section.


Java 平台包含三个通用的 `Map` 实现： [`HashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html) 、 [`TreeMap`](https://docs.oracle.com/javase/8/docs/api/java/util/TreeMap.html) 和 [`LinkedHashMap`](https://docs.oracle.com/javase/8/docs/api/java/util/LinkedHashMap.html) 。
它们的行为和性能与 `HashSet`、`TreeSet` 和 `LinkedHashSet` 非常相似，如 [The Set Interface](./set.md) 部分所述。


The remainder of this page discusses the `Map` interface in detail. 
But first, here are some more examples of collecting to `Map`s using JDK 8 aggregate operations. 
Modeling real-world objects is a common task in object-oriented programming, so it is reasonable to think that some programs might, for example, group employees by department: 


本页的其余部分详细讨论了 `Map` 接口。
但首先，这里有更多使用 JDK 8 聚合操作收集到 `Map` 的示例。
对现实世界的对象建模是面向对象编程中的一项常见任务，因此可以合理地认为某些程序可能，例如，按部门对员工进行分组：


```text
// Group employees by department
Map<Department, List<Employee>> byDept = employees.stream()
.collect(Collectors.groupingBy(Employee::getDepartment));
```


Or compute the sum of all salaries by department: 


或者按部门计算所有工资的总和：


```text
// Compute sum of salaries by department
Map<Department, Integer> totalByDept = employees.stream()
.collect(Collectors.groupingBy(Employee::getDepartment,
Collectors.summingInt(Employee::getSalary)));
```


Or perhaps group students by passing or failing grades:


或者也许通过及格或不及格的成绩对学生进行分组：


```text
// Partition students into passing and failing
Map<Boolean, List<Student>> passingFailing = students.stream()
.collect(Collectors.partitioningBy(s -> s.getGrade()>= PASS_THRESHOLD)); 
```


You could also group people by city: 


您还可以按城市对人员进行分组：


```text
// Classify Person objects by city
Map<String, List<Person>> peopleByCity
         = personStream.collect(Collectors.groupingBy(Person::getCity));
```


Or even cascade two collectors to classify people by state and city: 


甚至级联两个收集器以按州和城市对人进行分类：


```text
// Cascade Collectors 
Map<String, Map<String, List<Person>>> peopleByStateAndCity
  = personStream.collect(Collectors.groupingBy(Person::getState,
  Collectors.groupingBy(Person::getCity)))
```


Again, these are but a few examples of how to use the new JDK 8 APIs. 
For in-depth coverage of lambda expressions and aggregate operations see the lesson entitled [Aggregate Operations](https://docs.oracle.com/javase/tutorial/collections/streams/index.html). 


同样，这些只是如何使用新的 JDK 8 API 的几个示例。
有关 lambda 表达式和聚合操作的深入介绍，请参阅标题为 [Aggregate Operations](./../streams/index.md) 的课程。 


## Map Interface Basic Operations _Map 接口基本操作_


The basic operations of `Map` (`put`, `get`, `containsKey`, `containsValue`, `size`, and `isEmpty`) behave exactly like their counterparts in `Hashtable`. 
The [following program](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/Freq.java) generates a frequency table of the words found in its argument list. 
The frequency table maps each word to the number of times it occurs in the argument list.


`Map` 的基本操作（ `put`、 `get`、 `containsKey`、 `containsValue`、 `size` 和 `isEmpty`）的行为与 `Hashtable` 中的对应项完全相同。
[following program](./examples/Freq.java) 生成在其参数列表中找到的单词的频率表。
频率表将每个单词映射到它在参数列表中出现的次数。


```java
import java.util.*;

public class Freq {
    public static void main(String[] args) {
        Map<String, Integer> m = new HashMap<String, Integer>();

        // Initialize frequency table from command line
        for (String a : args) {
            Integer freq = m.get(a);
            m.put(a, (freq == null) ? 1 : freq + 1);
        }

        System.out.println(m.size() + " distinct words:");
        System.out.println(m);
    }
}
```


The only tricky thing about this program is the second argument of the `put` statement. 
That argument is a conditional expression that has the effect of setting the frequency to one if the word has never been seen before or one more than its current value if the word has already been seen. 
Try running this program with the command:


这个程序唯一棘手的地方是 `put` 语句的第二个参数。
该参数是一个条件表达式，如果该词以前从未见过，则它具有将频率设置为 1 的效果，或者如果该词已经见过，则将其设置为比其当前值高 1 的效果。
尝试使用以下命令运行此程序：


`java Freq if it is to be it is up to me to delegate`


The program yields the following output.


该程序产生以下输出。


```text
8 distinct words:
{to=3, delegate=1, be=1, it=2, up=1, if=1, me=1, is=2}
```


Suppose you'd prefer to see the frequency table in alphabetical order. 
All you have to do is change the implementation type of the `Map` from `HashMap` to `TreeMap`. 
Making this four-character change causes the program to generate the following output from the same command line.


假设您希望按字母顺序查看频率表。
您所要做的就是将 `Map` 的实现类型从 `HashMap` 更改为 `TreeMap`。
进行这四个字符的更改会导致程序从同一命令行生成以下输出。


```text
8 distinct words:
{be=1, delegate=1, if=1, is=2, it=2, me=1, to=3, up=1}
```


Similarly, you could make the program print the frequency table in the order the words first appear on the command line simply by changing the implementation type of the map to `LinkedHashMap`. 
Doing so results in the following output.


类似地，您可以通过将映射的实现类型更改为 `LinkedHashMap` ，使程序按照单词在命令行中首次出现的顺序打印频率表。
这样做会产生以下输出。


```text
8 distinct words:
{if=1, it=2, is=2, to=3, be=1, up=1, me=1, delegate=1}
```


This flexibility provides a potent illustration of the power of an interface-based framework.


这种灵活性有力地说明了基于接口的框架的强大功能。


Like the [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) and [`List`](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) interfaces, `Map` strengthens the requirements on the `equals` and `hashCode` methods so that two `Map` objects can be compared for logical equality without regard to their implementation types. 
Two `Map` instances are equal if they represent the same key-value mappings.


与 [`Set`](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) 和 [`List`](https://docs.oracle.com/javase/8/docs/api/java/util/List.html) 接口一样， `Map` 加强了对 `equals` 和 `hashCode` 方法的要求，以便可以不考虑比较两个 `Map` 对象的逻辑相等性到他们的实现类型。
如果两个 `Map` 实例表示相同的键值映射，则它们是相等的。


By convention, all general-purpose `Map` implementations provide constructors that take a `Map` object and initialize the new `Map` to contain all the key-value mappings in the specified `Map`. 
This standard `Map` conversion constructor is entirely analogous to the standard `Collection` constructor: 
It allows the caller to create a `Map` of a desired implementation type that initially contains all of the mappings in another `Map`, regardless of the other `Map`'s implementation type. 
For example, suppose you have a `Map`, named `m`. 
The following one-liner creates a new `HashMap` initially containing all of the same key-value mappings as `m`.


按照惯例，所有通用的 `Map` 实现都提供了构造函数，它接受一个 `Map` 对象并初始化新的 `Map` 以包含指定 `Map` 中的所有键值映射。
这个标准的 `Map` 转换构造函数完全类似于标准的 `Collection` 构造函数：
它允许调用者创建所需实现类型的 `Map` ，该映射最初包含另一个 `Map` 中的所有映射，而不管其他 `Map` 的实现类型如何。
例如，假设您有一个名为 `m` 的 `Map` 。
下面的单行代码创建了一个新的 `HashMap` ，最初包含所有与 `m` 相同的键值映射。


`Map<K, V> copy = new HashMap<K, V>(m);`


## Map Interface Bulk Operations _Map 接口批量操作_


The `clear` operation does exactly what you would think it could do: 
It removes all the mappings from the `Map`. 
The `putAll` operation is the `Map` analogue of the `Collection` interface's `addAll` operation. 
In addition to its obvious use of dumping one `Map` into another, it has a second, more subtle use. 
Suppose a `Map` is used to represent a collection of attribute-value pairs; the `putAll` operation, in combination with the `Map` conversion constructor, provides a neat way to implement attribute map creation with default values. 
The following is a static factory method that demonstrates this technique.


`clear` 操作完全符合您的预期：
它从 `Map` 中删除所有映射。
`putAll` 操作是 `Collection` 接口的 `addAll` 操作的 `Map` 类似物。
除了将一个 `Map` 转储到另一个中的明显用途之外，它还有第二种更微妙的用途。
假设一个 `Map` 用来表示一个属性值对的集合； `putAll` 操作与 `Map` 转换构造函数相结合，提供了一种使用默认值实现属性映射创建的简洁方法。
以下是演示此技术的静态工厂方法。


```text
static <K, V> Map<K, V> newAttributeMap(Map<K, V>defaults, Map<K, V> overrides) {
    Map<K, V> result = new HashMap<K, V>(defaults);
    result.putAll(overrides);
    return result;
}
```


## Collection Views _集合视图_


The `Collection` view methods allow a `Map` to be viewed as a `Collection` in these three ways:


`Collection` 视图方法允许通过以下三种方式将 `Map` 视为 `Collection` ：


* `keySet` — the `Set` of keys contained in the `Map`.

* `keySet` — `Map` 中包含的键的 `Set` 。

* `values` — The `Collection` of values contained in the `Map`. 
  This `Collection` is not a `Set`, because multiple keys can map to the same value.

* `values` — `Map` 中包含的值的 `Collection` 。
  这个 `Collection` 不是 `Set` ，因为多个键可以映射到同一个值。

* `entrySet` — the `Set` of key-value pairs contained in the `Map`. 
  The `Map` interface provides a small nested interface called `Map.Entry`, the type of the elements in this `Set`.

* `entrySet` — `Map` 中包含的键值对的 `Set` 。
  `Map` 接口提供了一个称为 `Map.Entry` 的小型嵌套接口，即此 `Set` 中元素的类型。


The `Collection` views provide the _only_ means to iterate over a `Map`. 
This example illustrates the standard idiom for iterating over the keys in a `Map` with a `for-each` construct:


`Collection` 视图提供了迭代 `Map` 的*唯一*的办法。
此示例说明了使用 `for-each` 构造迭代 `Map` 中的键的标准习惯用法：


```text
for (KeyType key : m.keySet())
    System.out.println(key);
```


and with an `iterator`:


并使用 `iterator` ：


```text
// Filter a map based on some 
// property of its keys.
for (Iterator<Type> it = m.keySet().iterator(); it.hasNext(); )
    if (it.next().isBogus())
        it.remove();
```


The idiom for iterating over values is analogous. 
Following is the idiom for iterating over key-value pairs.


迭代值的习惯用法是类似的。
以下是迭代键值对的习惯用法。


```text
for (Map.Entry<KeyType, ValType> e : m.entrySet())
    System.out.println(e.getKey() + ": " + e.getValue());
```


At first, many people worry that these idioms may be slow because the `Map` has to create a new `Collection` instance each time a `Collection` view operation is called. 
Rest easy: 
There's no reason that a `Map` cannot always return the same object each time it is asked for a given `Collection` view. 
This is precisely what all the `Map` implementations in `java.util` do.


起初，很多人担心这些习惯用法可能会很慢，因为每次调用 `Collection` 视图操作时， `Map` 都必须创建一个新的 `Collection` 实例。
高枕无忧：
没有理由每次要求一个给定的 `Collection` 视图时， `Map` 不能总是返回相同的对象。
这正是 `java.util` 中的所有 `Map` 实现所做的。


With all three `Collection` views, calling an `Iterator`'s `remove` operation removes the associated entry from the backing `Map`, assuming that the backing `Map` supports element removal to begin with. 
This is illustrated by the preceding filtering idiom.


对于所有三个 `Collection` 视图，调用 `Iterator` 的 `remove` 操作会从支持 `Map` 中删除相关条目，假设支持 `Map` 一 开始就支持元素删除。
前面的过滤习语说明了这一点。


With the `entrySet` view, it is also possible to change the value associated with a key by calling a `Map.Entry`'s `setValue` method during iteration (again, assuming the `Map` supports value modification to begin with). 
Note that these are the only safe ways to modify a `Map` during iteration; the behavior is unspecified if the underlying `Map` is modified in any other way while the iteration is in progress.


使用 `entrySet` 视图，还可以通过在迭代期间调用 `Map.Entry` 的 `setValue` 方法来更改与键关联的值（再次假设 `Map` 支持值修改开始） .
请注意，这些是在迭代期间修改 `Map` 的唯一安全方法；如果在迭代过程中以任何其他方式修改了底层的 `Map` ，则行为是未指定的。


The `Collection` views support element removal in all its many forms — `remove`, `removeAll`, `retainAll`, and `clear` operations, as well as the `Iterator.remove` operation. 
(Yet again, this assumes that the backing `Map` supports element removal.)


`Collection` 视图支持所有多种形式的元素删除 —— `remove`、 `removeAll`、 `retainAll` 和 `clear` 操作，以及 `Iterator.remove` 操作。
（再一次，这假设支持 `Map` 支持元素删除。）


The `Collection` views do not support element addition under any circumstances. 
It would make no sense for the `keySet` and `values` views, and it's unnecessary for the `entrySet` view, because the backing `Map`'s `put` and `putAll` methods provide the same functionality.


`Collection` 视图在任何情况下都不支持添加元素。
`keySet` 和 `values` 视图没有意义， `entrySet` 视图也没有必要，因为支持 `Map` 的 `put` 和 `putAll` 方法提供相同的功能。


## Fancy Uses of Collection Views: Map Algebra _集合视图的奇特用途： Map 代数_


When applied to the `Collection` views, bulk operations (`containsAll`, `removeAll`, and `retainAll`) are surprisingly potent tools. 
For starters, suppose you want to know whether one `Map` is a submap of another — that is, whether the first `Map` contains all the key-value mappings in the second. 
The following idiom does the trick.


当应用于 `Collection` 视图时，批量操作（ `containsAll`、 `removeAll` 和 `retainAll` ）是非常有效的工具。
对于初学者，假设您想知道一个 `Map` 是否是另一个 `Map` 的子图 —— 也就是说，第一个 `Map` 是否包含第二个中的所有键值映射。
以下惯用法可以解决问题。


```text
if (m1.entrySet().containsAll(m2.entrySet())) {
    ...
}
```


Along similar lines, suppose you want to know whether two `Map` objects contain mappings for all of the same keys.


沿着类似的思路，假设您想知道两个 `Map` 对象是否包含所有相同键的映射。


```text
if (m1.keySet().equals(m2.keySet())) {
    ...
}
```


Suppose you have a `Map` that represents a collection of attribute-value pairs, and two `Set`s representing required attributes and permissible attributes. 
(The permissible attributes include the required attributes.) The following snippet determines whether the attribute map conforms to these constraints and prints a detailed error message if it doesn't.


假设您有一个表示属性值对集合的 `Map` ，以及两个表示必需属性和允许属性的 `Set` 。
（允许的属性包括必需的属性。）以下代码段确定属性映射是否符合这些约束，如果不符合，则打印详细的错误消息。


```text
static <K, V> boolean validate(Map<K, V> attrMap, Set<K> requiredAttrs, Set<K>permittedAttrs) {
    boolean valid = true;
    Set<K> attrs = attrMap.keySet();

    if (! attrs.containsAll(requiredAttrs)) {
        Set<K> missing = new HashSet<K>(requiredAttrs);
        missing.removeAll(attrs);
        System.out.println("Missing attributes: " + missing);
        valid = false;
    }
    if (! permittedAttrs.containsAll(attrs)) {
        Set<K> illegal = new HashSet<K>(attrs);
        illegal.removeAll(permittedAttrs);
        System.out.println("Illegal attributes: " + illegal);
        valid = false;
    }
    return valid;
}
```


Suppose you want to know all the keys common to two `Map` objects.


假设您想知道两个 `Map` 对象共有的所有键。


````text
Set<KeyType>commonKeys = new HashSet<KeyType>(m1.keySet());
commonKeys.retainAll(m2.keySet());
````


A similar idiom gets you the common values.


类似的习语可以让您获得共同的价值观。


All the idioms presented thus far have been nondestructive; that is, they don't modify the backing `Map`. 
Here are a few that do. 
Suppose you want to remove all of the key-value pairs that one `Map` has in common with another.


迄今为止提出的所有成语都是非破坏性的。也就是说，它们不会修改支持的 `Map` 。
这里有一些可以做到的。
假设您要删除一个 `Map` 与另一个共有的所有键值对。


`m1.entrySet().removeAll(m2.entrySet());`


Suppose you want to remove from one `Map` all of the keys that have mappings in another.


假设您想从一个 `Map` 中删除在另一个映射中有映射的所有键。


`m1.keySet().removeAll(m2.keySet());`


What happens when you start mixing keys and values in the same bulk operation? 
Suppose you have a `Map`, `managers`, that maps each employee in a company to the employee's manager. 
We'll be deliberately vague about the types of the key and the value objects. 
It doesn't matter, as long as they're the same. 
Now suppose you want to know who all the "individual contributors" (or nonmanagers) are. 
The following snippet tells you exactly what you want to know.


当您开始在同一个批量操作中混合键和值时会发生什么？
假设您有一个 `Map` 、 `managers` ，它将公司中的每个员工映射到该员工的经理。
我们将故意模糊键和值对象的类型。
没关系，只要他们是一样的。
现在假设您想知道所有 "individual contributors" （或非管理者）是谁。
以下代码段准确地告诉您您想知道的内容。


```text
Set<Employee> individualContributors = new HashSet<Employee>(managers.keySet());
individualContributors.removeAll(managers.values());
```


Suppose you want to fire all the employees who report directly to some manager, Simon.


假设您想解雇直接向某个经理 Simon 汇报的所有员工。


```text
Employee simon = ... ;
managers.values().removeAll(Collections.singleton(simon));
```


Note that this idiom makes use of `Collections.singleton`, a static factory method that returns an immutable `Set` with the single, specified element.


Once you've done this, you may have a bunch of employees whose managers no longer work for the company (if any of Simon's direct-reports were themselves managers). 
The following code will tell you which employees have managers who no longer works for the company.


```text
Map<Employee, Employee> m = new HashMap<Employee, Employee>(managers);
m.values().removeAll(managers.keySet());
Set<Employee> slackers = m.keySet();
```


This example is a bit tricky. 
First, it makes a temporary copy of the `Map`, and it removes from the temporary copy all entries whose (manager) value is a key in the original `Map`. 
Remember that the original `Map` has an entry for each employee. 
Thus, the remaining entries in the temporary `Map` comprise all the entries from the original `Map` whose (manager) values are no longer employees. 
The keys in the temporary copy, then, represent precisely the employees that we're looking for.


There are many more idioms like the ones contained in this section, but it would be impractical and tedious to list them all. 
Once you get the hang of it, it's not that difficult to come up with the right one when you need it.


## Multimaps


A _multimap_ is like a `Map` but it can map each key to multiple values. 
The Java Collections Framework doesn't include an interface for multimaps because they aren't used all that commonly. 
It's a fairly simple matter to use a `Map` whose values are `List` instances as a multimap. 
This technique is demonstrated in the next code example, which reads a word list containing one word per line (all lowercase) and prints out all the anagram groups that meet a size criterion. 
An _anagram group_ is a bunch of words, all of which contain exactly the same letters but in a different order. 
The program takes two arguments on the command line: (1) the name of the dictionary file and (2) the minimum size of anagram group to print out. 
Anagram groups containing fewer words than the specified minimum are not printed.


There is a standard trick for finding anagram groups: For each word in the dictionary, alphabetize the letters in the word (that is, reorder the word's letters into alphabetical order) and put an entry into a multimap, mapping the alphabetized word to the original word. 
For example, the word _bad_ causes an entry mapping abd into bad to be put into the multimap. 
A moment's reflection will show that all the words to which any given key maps form an anagram group. 
It's a simple matter to iterate over the keys in the multimap, printing out each anagram group that meets the size constraint.


[The following program]() is a straightforward implementation of this technique.


```java
import java.util.*;
import java.io.*;

public class Anagrams {
    public static void main(String[] args) {
        int minGroupSize = Integer.parseInt(args[1]);

        // Read words from file and put into a simulated multimap
        Map<String, List<String>> m = new HashMap<String, List<String>>();

        try {
            Scanner s = new Scanner(new File(args[0]));
            while (s.hasNext()) {
                String word = s.next();
                String alpha = alphabetize(word);
                List<String> l = m.get(alpha);
                if (l == null)
                    m.put(alpha, l=new ArrayList<String>());
                l.add(word);
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        // Print all permutation groups above size threshold
        for (List<String> l : m.values())
            if (l.size() >= minGroupSize)
                System.out.println(l.size() + ": " + l);
    }

    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
```


Running this program on a 173,000-word dictionary file with a minimum anagram group size of eight produces the following output.


```text
9: [estrin, inerts, insert, inters, niters, nitres, sinter,
     triens, trines]
8: [lapse, leaps, pales, peals, pleas, salep, sepal, spale]
8: [aspers, parses, passer, prases, repass, spares, sparse,
     spears]
10: [least, setal, slate, stale, steal, stela, taels, tales,
      teals, tesla]
8: [enters, nester, renest, rentes, resent, tenser, ternes,
     treens]
8: [arles, earls, lares, laser, lears, rales, reals, seral]
8: [earings, erasing, gainers, reagins, regains, reginas,
     searing, seringa]
8: [peris, piers, pries, prise, ripes, speir, spier, spire]
12: [apers, apres, asper, pares, parse, pears, prase, presa,
      rapes, reaps, spare, spear]
11: [alerts, alters, artels, estral, laster, ratels, salter,
      slater, staler, stelar, talers]
9: [capers, crapes, escarp, pacers, parsec, recaps, scrape,
     secpar, spacer]
9: [palest, palets, pastel, petals, plates, pleats, septal,
     staple, tepals]
9: [anestri, antsier, nastier, ratines, retains, retinas,
     retsina, stainer, stearin]
8: [ates, east, eats, etas, sate, seat, seta, teas]
8: [carets, cartes, caster, caters, crates, reacts, recast,
     traces]
```


Many of these words seem a bit bogus, but that's not the program's fault; they're in the [dictionary file](). 
Here's the dictionary file we used. 
It was derived from the Public Domain ENABLE benchmark reference word list. 
