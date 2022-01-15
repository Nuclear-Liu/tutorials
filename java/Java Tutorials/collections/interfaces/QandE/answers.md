# Answers to Questions and Exercises: _问题和练习的答案：_


## Questions _问题_


1. Question: At the beginning of this lesson, you learned that the core collection interfaces are organized into two distinct inheritance trees. One interface in particular is not considered to be a true Collection, and therefore sits at the top of its own tree. What is the name of this interface?
  Answer: Map

2. Question: Each interface in the collections framework is declared with the <E> syntax, which tells you that it is generic. When you declare a Collection instance, what is the advantage of specifying the type of objects that it will contain?
  Answer: Specifying the type allows the compiler to verify (at compile time) that the type of object you put into the collection is correct, thus reducing errors at runtime.

3. Question: What interface represents a collection that does not allow duplicate elements?
  Answer: Set

4. Question: What interface forms the root of the collections hierarchy?
  Answer: Collection

5. Question: What interface represents an ordered collection that may contain duplicate elements?
  Answer: List

6. Question: What interface represents a collection that holds elements prior to processing?
  Answer: Queue

7. Question: What interface represents a type that maps keys to values?
  Answer: Map

8. Question: What interface represents a double-ended queue?
  Answer: Deque

9. Question: Name three different ways to iterate over the elements of a List.
  Answer: You can iterate over a List using streams, the enhanced for statement, or iterators.

10. Question: True or False: Aggregate operations are mutative operations that modify the underlying collection.
  Answer: False. Aggregate operations do not mutate the underlying collection. In fact, you must be careful to never mutate a collection while invoking its aggregate operations. Doing so could lead to concurrency problems should the stream be changed to a parallel stream at some point in the future. 


## Exercises _练习_


Exercise: Write a program that prints its arguments in random order. Do not make a copy of the argument array. Demonstrate how to print out the elements using both streams and the traditional enhanced for statement.
Answer: 

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


Exercise: Take the FindDups example and modify it to use a SortedSet instead of a Set. Specify a Comparator so that case is ignored when sorting and identifying set elements.
Answer: 


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


Exercise: Write a method that takes a List<String> and applies String.trim to each element.
Answer:
The enhanced for statement does not allow you to modify the List. Using an instance of the Iterator class allows you to delete elements, but not replace an existing element or add a new one. That leaves ListIterator: 


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


Exercise: Consider the four core interfaces, Set, List, Queue, and Map. For each of the following four assignments, specify which of the four core interfaces is best-suited, and explain how to use it to implement the assignment.
Answers:


  * Whimsical Toys Inc (WTI) needs to record the names of all its employees. Every month, an employee will be chosen at random from these records to receive a free toy.
  * Use a List. Choose a random employee by picking a number between 0 and size()-1.
  * WTI has decided that each new product will be named after an employee — but only first names will be used, and each name will be used only once. Prepare a list of unique first names.
  * Use a Set. Collections that implement this interface don't allow the same element to be entered more than once.
  * WTI decides that it only wants to use the most popular names for its toys. Count up the number of employees who have each first name.
  * Use a Map, where the keys are first names, and each value is a count of the number of employees with that first name.
  * WTI acquires season tickets for the local lacrosse team, to be shared by employees. Create a waiting list for this popular sport.
  * Use a Queue. Invoke add() to add employees to the waiting list, and remove() to remove them.
  