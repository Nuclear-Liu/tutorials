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
//
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
Although this method is more generic than , trying to create a separate method for each possible search query can still lead to brittle code. 
You can instead separate the code that specifies the criteria for which you want to search in a different class.
PersonprintPersonsOlderThan

## Approach 3: Specify Search Criteria Code in a Local Class _在本地类中指定搜索条件代码_

## Approach 4: Specify Search Criteria Code in an Anonymous Class _在匿名类中指定搜索条件代码_

## Approach 5: Specify Search Criteria Code with a Lambda Expression _使用 Lambda 表达式指定搜索条件代码_

## Approach 6: Use Standard Functional Interfaces with Lambda Expressions _将标准函数接口与 Lambda 表达式结合使用_

## Approach 7: Use Lambda Expressions Throughout Your Application _在整个应用程序中使用 Lambda 表达式_

## Approach 8: Use Generics More Extensively _更广泛地使用泛型_

## Approach 9: Use Aggregate Operations That Accept Lambda Expressions as Parameters _使用接受 Lambda 表达式作为参数的聚合操作_
