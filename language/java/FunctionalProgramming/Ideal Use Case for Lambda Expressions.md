# Ideal Use Case for Lambda Expressions _Lambda 表达式典型案例_

Suppose that you are creating a social networking application. 
You want to create a feature that enables an administrator to perform any kind of action, such as sending a message, on members of the social networking application that satisfy certain criteria. 
The following table describes this use case in detail:

假设您正在创建一个社交网络应用程序。
您希望创建一项功能，使管理员能够对满足特定条件的社交网络应用程序成员执行任何类型的操作，如发送消息。
下表详细描述了此用例：

| Field                   | Description                                                                                                                                                                                                                                                                                                                                                                     |
|-------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Name                    | Perform action on selected members                                                                                                                                                                                                                                                                                                                                              |
| Primary Actor           | Administrator                                                                                                                                                                                                                                                                                                                                                                   |
| Preconditions           | Administrator is logged in to the system.                                                                                                                                                                                                                                                                                                                                       |
| Postconditions          | Action is performed only on members that fit the specified criteria.                                                                                                                                                                                                                                                                                                            |
| Main Success Scenario   | 1. Administrator specifies criteria of members on which to perform a certain action. <br/> 2. Administrator specifies an action to perform on those selected members. <br/> 3. Administrator selects the **Submit** button. <br/> 4. The system finds all members that match the specified criteria. <br/> 5. The system performs the specified action on all matching members. |
| Extensions              | 1a. Administrator has an option to preview those members who match the specified criteria before he or she specifies the action to be performed or before selecting the **Submit** button                                                                                                                                                                                       |
| Frequency of Occurrence | Many times during the day.                                                                                                                                                                                                                                                                                                                                                      |

| Field  | 描述                                                                                                                                    |
|--------|---------------------------------------------------------------------------------------------------------------------------------------|
| 名称     | 对选定成员执行操作                                                                                                                             |
| 主要角色   | 管理员                                                                                                                                   |
| 前提条件   | 管理员已登录系统。                                                                                                                             |
| 后置条件   | 仅对符合指定条件的成员执行操作。                                                                                                                      |
| 主要成功方案 | 1. 管理员指定要对其执行特定操作的成员的条件。 <br/> 2. 管理员指定要对这些选定成员执行的操作。 <br/> 3. 管理员选择**提交**按钮。 <br/> 4. 系统将查找与指定条件匹配的所有成员。 <br/> 5. 系统对所有匹配的成员执行指定的操作。 |
| 扩展     | 1a. 管理员可以选择在指定要执行的操作之前或在选择**提交**按钮之前预览符合指定条件的成员                                                                                       |
| 发生频率   | 一天很多次                                                                                                                                 |

Suppose that members of this social networking application are represented by the following `Person` class:

假设此社交网络应用程序的成员由以下 `Person` 类表示：

```java
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;

    public int getAge() {
        // ...
    }

    public void printPerson() {
        // ...
    }
}
```

Suppose that the members of your social networking application are stored in a instance.
`List<Person>`

假设社交网络应用程序的成员存储在实例中。
`List<Person>`

This section begins with a naive approach to this use case. 
It improves upon this approach with local and anonymous classes, and then finishes with an efficient and concise approach using lambda expressions. 
Find the code excerpts described in this section in the example `RosterTest`.

本节首先介绍此用例的幼稚方法。
它使用本地和匿名类改进了这种方法，然后使用 lambda 表达式以高效简洁的方法完成。
在示例 `RosterTest` 中找到本节中描述的代码摘录。

## Approach 1: Create Methods That Search for Members That Match One Characteristic _创建搜索与一个特征匹配的成员的方法_

One simplistic approach is to create several methods; each method searches for members that match one characteristic, such as gender or age. 
The following method prints members that are older than a specified age:

一种简单的方法是创建几种方法;每种方法都搜索与一个特征（如性别或年龄）匹配的成员。
以下方法打印早于指定期限的成员：

```text
public static void printPersonsOlderThan(List<Person> roster, int age) {
    for (Person p : roster) {
        if (p.getAge() >= age) {
            p.printPerson();
        }
    }
}
```

**Note**: A [`List`]() is an ordered [`Collection`](). 
A _collection_ is an object that groups multiple elements into a single unit. 
Collections are used to store, retrieve, manipulate, and communicate aggregate data. 
For more information about collections, see the [`Collections`]() trail.

**注意**:  `List` 是有序的 `Collection` 。
_collection_ 是将多个元素组合到一个单元中的对象。
集合用于存储、检索、操作和传递聚合数据。
有关集合的更多信息，请参阅 [`Collection`]() 线索。

This approach can potentially make your application _brittle_, which is the likelihood of an application not working because of the introduction of updates (such as newer data types). 
Suppose that you upgrade your application and change the structure of the class such that it contains different member variables; perhaps the class records and measures ages with a different data type or algorithm. 
You would have to rewrite a lot of your API to accommodate this change. 
In addition, this approach is unnecessarily restrictive; what if you wanted to print members younger than a certain age, for example?`Person`

此方法可能会使应用程序*变脆*，这是由于引入了更新（如较新的数据类型）而导致应用程序无法正常工作的可能性。
假设您升级了应用程序并更改了类的结构，使其包含不同的成员变量;也许类使用不同的数据类型或算法记录和测量年龄。
您必须重写大量 API 以适应此更改。
此外，这种做法具有不必要的限制性；例如，如果您想打印小于特定年龄的成员，该怎么办？ `Person`

## Approach 2: Create More Generalized Search Methods _创建更通用的搜索方法_

The following method is more generic than `printPersonsOlderThan`; it prints members within a specified range of ages:`printPersonsOlderThan`

以下方法比 `printPersonsOlderThan` 更通用；它打印指定年龄范围内的成员： `printPersonsOlderThan`

```text
public static void printPersonsWithinAgeRange(
    List<Person> roster, int low, int high) {
    for (Person p : roster) {
        if (low <= p.getAge() && p.getAge() < high) {
            p.printPerson();
        }
    }
}
```

What if you want to print members of a specified sex, or a combination of a specified gender and age range? 
What if you decide to change the class and add other attributes such as relationship status or geographical location? 
Although this method is more generic than `printPersonsOlderThan`, trying to create a separate method for each possible search query can still lead to brittle code. 
You can instead separate the code that specifies the criteria for which you want to search in a different class.

如果要打印指定性别的成员或指定性别和年龄范围的组合，该怎么办？
如果您决定更改类并添加其他属性（如关系状态或地理位置），该怎么办？
尽管此方法比 `printPersonsOlderThan` 更通用，但尝试为每个可能的搜索查询创建单独的方法仍会导致代码脆弱。
您可以改为分隔指定要在其他类中搜索的条件的代码。

## Approach 3: Specify Search Criteria Code in a Local Class _在本地类中指定搜索条件代码_

The following method prints members that match search criteria that you specify:

以下方法打印与您指定的搜索条件匹配的成员：

```text
public static void printPersons(
    List<Person> roster, CheckPerson tester) {
    for (Person p : roster) {
        if (tester.test(p)) {
            p.printPerson();
        }
    }
}
```

This method checks each `Person` instance contained in the `List` parameter `roster` whether it satisfies the search criteria specified in the `CheckPerson` parameter `tester` by invoking the method `tester.test`. 
If the method `tester.test` returns a `true` value, then the method `printPersons` is invoked on the `Person` instance.

此方法通过调用方法 `tester.test` 来检查 `List` 参数 `roster` 中包含的每个 `Person` 实例是否满足 `CheckPerson` 参数 `tester` 中指定的搜索条件。
如果方法 `tester.test` 返回 `true` 值，则在 `Person` 实例上调用方法 `printPersons` 。

To specify the search criteria, you implement the `CheckPerson` interface:

若要指定搜索条件，请实现 `CheckPerson` 接口：

```text
interface CheckPerson {
    boolean test(Person p);
}
```

The following class implements the `CheckPerson` interface by specifying an implementation for the method `test`. 
This method filters members that are eligible for Selective Service in the United States: 
it returns a `true` value if its `Person` parameter is male and between the ages of 18 and 25:

下面的类通过指定方法 `test` 的实现来实现 `CheckPerson` 接口。
此方法筛选有资格在美国服兵役的成员：
如果其 `Person` 参数是男性且年龄在 18 到 25 岁之间，则返回 `true` 值：

```text
class CheckPersonEligibleForSelectiveService implements CheckPerson {
    public boolean test(Person p) {
        return p.gender == Person.Sex.MALE &&
            p.getAge() >= 18 &&
            p.getAge() <= 25;
    }
}
```

To use this class, you create a new instance of it and invoke the `printPersons` method:

若要使用此类，请创建它的新实例并调用 `printPersons` 方法：

```text
printPersons(
    roster, new CheckPersonEligibleForSelectiveService());
```

Although this approach is less brittle—you don't have to rewrite methods if you change the structure of the `Person`—you still have additional code: 
a new interface and a local class for each search you plan to perform in your application. 
Because `CheckPersonEligibleForSelectiveService` implements an interface, you can use an anonymous class instead of a local class and bypass the need to declare a new class for each search.

尽管这种方法不那么脆弱（如果更改 `Person` 的结构，则不必重写方法），但您仍然有其他代码：
一个新接口和一个本地类，用于您计划在应用程序中执行的每个搜索。
由于 `CheckPersonEligibleForSelectiveService` 实现了一个接口，因此您可以使用匿名类而不是本地类，并绕过为每个搜索声明新类的需要。

## Approach 4: Specify Search Criteria Code in an Anonymous Class _在匿名类中指定搜索条件代码_

One of the arguments of the following invocation of the method `printPersons` is an anonymous class that filters members that are eligible for Selective Service in the United States: 
those who are male and between the ages of 18 and 25:

以下调用方法 `printPersons` 的参数之一是一个匿名类，该类过滤有资格在美国服兵役的成员：
年龄在 18 至 25 岁之间的男性：

```text
printPersons(
    roster,
    new CheckPerson() {
        public boolean test(Person p) {
            return p.getGender() == Person.Sex.MALE
                && p.getAge() >= 18
                && p.getAge() <= 25;
        }
    }
);
```

This approach reduces the amount of code required because you don't have to create a new class for each search that you want to perform. 
However, the syntax of anonymous classes is bulky considering that the `CheckPerson` interface contains only one method. 
In this case, you can use a lambda expression instead of an anonymous class, as described in the next section.

此方法减少了所需的代码量，因为您不必为要执行的每个搜索创建新类。
但是，考虑到 `CheckPerson` 接口只包含一个方法，匿名类的语法很庞大。
在这种情况下，您可以使用 lambda 表达式而不是匿名类，如下一节所述。

## Approach 5: Specify Search Criteria Code with a Lambda Expression _使用 Lambda 表达式指定搜索条件代码_

The `CheckPerson` interface is a _functional interface_. 
A functional interface is any interface that contains only one abstract method. 
(A functional interface may contain one or more default methods or static methods.) 
Because a functional interface contains only one abstract method, you can omit the name of that method when you implement it. 
To do this, instead of using an anonymous class expression, you use a lambda expression, which is highlighted in the following method invocation:

`CheckPerson` 接口是一个 _functional interface_。
功能接口是仅包含一个抽象方法的任何接口。
（函数接口可能包含一个或多个默认方法或静态方法。）


```text
printPersons(
    roster,
    (Person p) -> p.getGender() == Person.Sex.MALE
        && p.getAge() >= 18
        && p.getAge() <= 25
);
```

See [Syntax of Lambda Expressions](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#syntax) for information about how to define lambda expressions.

请参阅 [Lambda 表达式的语法](01%20Lambda%20Expressions.md) 了解有关如何定义 lambda 表达式的信息。

You can use a standard functional interface in place of the interface `CheckPerson`, which reduces even further the amount of code required.

您可以使用标准函数接口代替 `CheckPerson` 接口，从而进一步减少所需的代码量。

## Approach 6: Use Standard Functional Interfaces with Lambda Expressions _将标准函数接口与 Lambda 表达式结合使用_

Reconsider the `CheckPerson` interface:

重新考虑 `CheckPerson` 接口：

```java
interface CheckPerson {
    boolean test(Person p);
}
```

This is a very simple interface. 
It's a functional interface because it contains only one abstract method. 
This method takes one parameter and returns a `boolean` value. 
The method is so simple that it might not be worth it to define one in your application. 
Consequently, the JDK defines several standard functional interfaces, which you can find in the package `java.util.function`.

这是一个非常简单的接口。
它是一个函数接口，因为它只包含一个抽象方法。
此方法接收一个参数并返回 `boolean` 值。
该方法非常简单，因此在应用程序中定义一个方法可能不值得。
因此， JDK 定义了几个标准的功能接口，您可以在包 `java.util.function` 中找到这些接口。

For example, you can use the `Predicate<T>` interface in place of `CheckPerson`. 
This interface contains the method `boolean test(T t)`:

例如，您可以使用 `Predicate<T>` 接口代替 `CheckPerson` 。
此接口包含方法 `boolean test(T t)` ：

```java
interface Predicate<T> {
    boolean test(T t);
}
```

The interface `Predicate<T>` is an example of a generic interface. 
(For more information about generics, see the `Generics (Updated)` lesson.) Generic types (such as generic interfaces) specify one or more type parameters within angle brackets (`<>`). 
This interface contains only one type parameter, `T`. 
When you declare or instantiate a generic type with actual type arguments, you have a parameterized type. 
For example, the parameterized type `Predicate<Person>` is the following:

接口 `Predicate<T>` 是通用接口的一个示例。
（有关泛型的详细信息，请参阅 `Generics (Updated)` 课程。）泛型类型（如泛型接口）指定尖括号内的一个或多个类型参数（ `<>` ）。
此接口仅包含一个类型参数 `T` 。
使用实际类型参数声明或实例化泛型类型时，您具有参数化类型。
例如，参数化类型 `Predicate<Person>` 如下所示：

```java
interface Predicate<Person> {
    boolean test(Person t);
}
```

This parameterized type contains a method that has the same return type and parameters as `CheckPerson.boolean test(Person p)`. 
Consequently, you can use `Predicate<T>` in place of `CheckPerson` as the following method demonstrates:

此参数化类型包含一个方法，该方法具有与 `CheckPerson.boolean test(Person p)` 相同的返回类型和参数。
因此，您可以使用 `Predicate<T>` 代替 `CheckPerson`，如以下方法所示：

```text
public static void printPersonsWithPredicate(
    List<Person> roster, Predicate<Person> tester) {
    for (Person p : roster) {
        if (tester.test(p)) {
            p.printPerson();
        }
    }
}
```

As a result, the following method invocation is the same as when you invoked `printPersons` in [Approach 3: Specify Search Criteria Code in a Local Class]() to obtain members who are eligible for Selective Service:

因此，以下方法调用与在 [Approach 3: Specify Search Criteria Code in a Local Class](./Ideal Use Case for Lambda Expressions.md) 中调用 `printPersons` 以获取有资格服兵役的成员时相同：

```text
printPersonsWithPredicate(
    roster,
    p -> p.getGender() == Person.Sex.MALE
        && p.getAge() >= 18
        && p.getAge() <= 25
);
```

This is not the only possible place in this method to use a lambda expression. 
The following approach suggests other ways to use lambda expressions.

这不是此方法中唯一可能使用 lambda 表达式的位置。
以下方法建议使用 lambda 表达式的其他方法。

## Approach 7: Use Lambda Expressions Throughout Your Application _在整个应用程序中使用 Lambda 表达式_

Reconsider the method `printPersonsWithPredicate` to see where else you could use lambda expressions:

重新考虑方法 `printPersonsWithPredicate` 看看还有什么地方可以使用 lambda 表达式：

```text
public static void printPersonsWithPredicate(
    List<Person> roster, Predicate<Person> tester) {
    for (Person p : roster) {
        if (tester.test(p)) {
            p.printPerson();
        }
    }
}
```

This method checks each `Person` instance contained in the `List` parameter `roster` whether it satisfies the criteria specified in the `Predicate` parameter `tester`. 
If the `Person` instance does satisfy the criteria specified by `tester`, the method `printPerson` is invoked on the `Person` instance.

此方法检查 `List` 参数 `roster` 中包含的每个 `Person` 实例是否满足 `Predicate` 参数 `tester` 中指定的条件。
如果 `Person` 实例确实满足 `tester` 指定的条件，则在 `Person` 实例上调用方法 `printPerson` 。

Instead of invoking the method `printPerson`, you can specify a different action to perform on those `Person` instances that satisfy the criteria specified by `tester`. 
You can specify this action with a lambda expression. 
Suppose you want a lambda expression similar to `printPerson`, one that takes one argument (an object of type `Person`) and returns `void`. 
Remember, to use a lambda expression, you need to implement a functional interface. 
In this case, you need a functional interface that contains an abstract method that can take one argument of type `Person` and returns `void`. 
The `Consumer<T>` interface contains the method `void accept(T t)`, which has these characteristics. 
The following method replaces the invocation `p.printPerson()` with an instance of `Consumer<Person>` that invokes the method `accept`:

除了调用 `printPerson` 方法，您可以指定不同的操作来对那些满足 `tester` 指定条件的 `Person` 实例执行。
您可以使用 lambda 表达式指定此操作。
假设你想要一个类似于 `printPerson` 的 lambda 表达式，它接受一个参数（ `Person` 类型的对象）并返回 `void` 。
请记住，要使用 lambda 表达式，您需要实现一个函数式接口。
在这种情况下，您需要一个函数式接口，其中包含一个抽象方法，该方法可以接受一个 `Person` 类型的参数并返回 `void` 。
`Consumer<T>` 接口包含方法 `void accept(T t)` ，它具有这些特性。
以下方法将调用 `p.printPerson()` 替换为调用方法 `accept` 的 `Consumer<Person>` 实例：

```text
public static void processPersons(
    List<Person> roster,
    Predicate<Person> tester,
    Consumer<Person> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
}
```

As a result, the following method invocation is the same as when you invoked `printPersons` in [Approach 3: Specify Search Criteria Code in a Local Class]() to obtain members who are eligible for Selective Service. 
The lambda expression used to print members is highlighted:

因此，以下方法调用与您在 [Approach 3: Specify Search Criteria Code in a Local Class]() 中调用 `printPersons` 以获取符合选择性服务资格的成员时相同。
用于打印成员的 lambda 表达式被突出显示：

```text
processPersons(
     roster,
     p -> p.getGender() == Person.Sex.MALE
         && p.getAge() >= 18
         && p.getAge() <= 25,
     p -> p.printPerson()
);
```

What if you want to do more with your members' profiles than printing them out. 
Suppose that you want to validate the members' profiles or retrieve their contact information? 
In this case, you need a functional interface that contains an abstract method that returns a value. 
The `Function<T,R>` interface contains the method `R apply(T t)`. 
The following method retrieves the data specified by the parameter `mapper`, and then performs an action on it specified by the parameter `block`:

如果您想对会员的个人资料做更多的事情而不是打印出来怎么办。
假设您要验证成员的个人资料或检索他们的联系信息？
在这种情况下，您需要一个包含返回值的抽象方法的函数接口。
`Function<T,R>` 接口包含方法 `R apply(T t)` 。

```text
public static void processPersonsWithFunction(
    List<Person> roster,
    Predicate<Person> tester,
    Function<Person, String> mapper,
    Consumer<String> block) {
    for (Person p : roster) {
        if (tester.test(p)) {
            String data = mapper.apply(p);
            block.accept(data);
        }
    }
}
```

The following method retrieves the email address from each member contained in `roster` who is eligible for Selective Service and then prints it:

以下方法从 `roster` 中包含有资格参加选择性服务的每个成员中检索电子邮件地址，然后将其打印出来：

```text
processPersonsWithFunction(
    roster,
    p -> p.getGender() == Person.Sex.MALE
        && p.getAge() >= 18
        && p.getAge() <= 25,
    p -> p.getEmailAddress(),
    email -> System.out.println(email)
);
```

## Approach 8: Use Generics More Extensively _更广泛地使用泛型_

Reconsider the method `processPersonsWithFunction`. 
The following is a generic version of it that accepts, as a parameter, a collection that contains elements of any data type:

重新考虑方法 `processPersonsWithFunction` 。
以下是它的通用版本，它接受包含任何数据类型元素的集合作为参数：

```text
public static <X, Y> void processElements(
    Iterable<X> source,
    Predicate<X> tester,
    Function <X, Y> mapper,
    Consumer<Y> block) {
    for (X p : source) {
        if (tester.test(p)) {
            Y data = mapper.apply(p);
            block.accept(data);
        }
    }
}
```

To print the e-mail address of members who are eligible for Selective Service, invoke the `processElements` method as follows:

要打印有资格参加选择性服务的成员的电子邮件地址，请调用 `processElements` 方法，如下所示：

```text
processElements(
    roster,
    p -> p.getGender() == Person.Sex.MALE
        && p.getAge() >= 18
        && p.getAge() <= 25,
    p -> p.getEmailAddress(),
    email -> System.out.println(email)
);
```

This method invocation performs the following actions:

1. Obtains a source of objects from the collection `source`. 
In this example, it obtains a source of `Person` objects from the collection `roster`. 
Notice that the collection `roster`, which is a collection of type `List`, is also an object of type `Iterable`.

2. Filters objects that match the `Predicate` object `tester`. 
In this example, the `Predicate` object is a lambda expression that specifies which members would be eligible for Selective Service.

3. Maps each filtered object to a value as specified by the `Function` object `mapper`. 
In this example, the `Function` object is a lambda expression that returns the e-mail address of a member.

4. Performs an action on each mapped object as specified by the `Consumer` object `block`. 
In this example, the `Consumer` object is a lambda expression that prints a string, which is the e-mail address returned by the `Function` object.

此方法调用执行以下操作：

1. 从集合 `source` 中获取对象的来源。
在此示例中，它从集合 `roster` 中获取 `Person` 对象的来源。
请注意，集合 `roster` 是一个 `List` 类型的集合，也是一个 `Iterable` 类型的对象。

2. 过滤与 `Predicate` 对象 `tester` 匹配的对象。
在此示例中， `Predicate` 对象是一个 lambda 表达式，用于指定哪些成员有资格获得选择性服务。

3. 将每个过滤的对象映射到 `Function` 对象 `mapper` 指定的值。
在这个例子中， `Function` 对象是一个返回成员电子邮件地址的 lambda 表达式。

4. 根据 `Consumer` 对象 `block` 指定的每个映射对象执行操作。
在本例中， `Consumer` 对象是一个打印字符串的 lambda 表达式，该字符串是 `Function` 对象返回的电子邮件地址。

You can replace each of these actions with an aggregate operation.

您可以将这些操作中的每一个替换为聚合操作。

## Approach 9: Use Aggregate Operations That Accept Lambda Expressions as Parameters _使用接受 Lambda 表达式作为参数的聚合操作_

The following example uses aggregate operations to print the e-mail addresses of those members contained in the collection `roster` who are eligible for Selective Service:

以下示例使用聚合操作来打印集合 `roster` 中符合选择性服务条件的成员的电子邮件地址：

```text
roster
    .stream()
    .filter(
        p -> p.getGender() == Person.Sex.MALE
            && p.getAge() >= 18
            && p.getAge() <= 25)
    .map(p -> p.getEmailAddress())
    .forEach(email -> System.out.println(email));
```

The following table maps each of the operations the method `processElements` performs with the corresponding aggregate operation:

下表映射了方法 `processElements` 执行的每个操作与相应的聚合操作：

| `processElements` Action                                         | Aggregate Operation                                         |
|------------------------------------------------------------------|-------------------------------------------------------------|
| Obtain a source of objects                                       | `Stream<E> stream()`                                        |
| Filter objects that match a `Predicate` object                   | `Stream<T> filter(Predicate<? super T> predicate)`          |
| Map objects to another value as specified by a `Function` object | `<R> Stream<R> map(Function<? super T,? extends R> mapper)` |
| Perform an action as specified by a `Consumer` object            | `void forEach(Consumer<? super T> action)`                  |


| `processElements` 动作         | 聚合操作                                                        |
|------------------------------|-------------------------------------------------------------|
| 获取对象的来源                      | `Stream<E> stream()`                                        |
| 过滤与 `Predicate` 对象匹配的对象      | `Stream<T> filter(Predicate<? super T> predicate)`          |
| 将对象映射到由 `Function` 对象指定的另一个值 | `<R> Stream<R> map(Function<? super T,? extends R> mapper)` |
| 执行 `Consumer` 对象指定的操作        | `void forEach(Consumer<? super T> action)`                  |

The operations `filter`, `map`, and `forEach` are _aggregate operations_. 
Aggregate operations process elements from a stream, not directly from a collection (which is the reason why the first method invoked in this example is `stream`). 
A stream is a sequence of elements. 
Unlike a collection, it is not a data structure that stores elements. 
Instead, a stream carries values from a source, such as collection, through a pipeline. 
A _pipeline_ is a sequence of stream operations, which in this example is `filter`-`map`-`forEach`. 
In addition, aggregate operations typically accept lambda expressions as parameters, enabling you to customize how they behave.

操作 `filter` 、 `map` 和 `forEach` 是 _聚合操作_ 。
聚合操作处理来自流的元素，而不是直接来自集合（这就是本例中调用的第一个方法是 `stream` 的原因）。
流是一系列元素。
与集合不同，它不是存储元素的数据结构。
相反，流通过管道从源（例如集合）携带值。
_pipeline_ 是一系列流操作，在本例中是 `filter`-`map`-`forEach` 。
此外，聚合操作通常接受 lambda 表达式作为参数，使您能够自定义它们的行为方式。

For a more thorough discussion of aggregate operations, see the [Aggregate Operations]() lesson.

有关聚合操作的更全面讨论，请参阅 [聚合操作]() 课程。
