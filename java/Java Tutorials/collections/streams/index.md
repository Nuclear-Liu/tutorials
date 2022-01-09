# Lesson: Aggregate Operations


* [Reduction](https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html)

* [Reduction](./reduction.md)

* [Parallelism](https://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html)

* [Parallelism](./parallelism.md)

* [Questions and Exercises](https://docs.oracle.com/javase/tutorial/collections/streams/QandE/questions.html)

* [Questions and Exercises](./QandE/questions.md)


**Note**: To better understand the concepts in this section, review the sections [Lambda Expressions]() and [Method References]().


For what do you use collections? 
You don't simply store objects in a collection and leave them there. 
In most cases, you use collections to retrieve items stored in them.


Consider again the scenario described in the section [Lambda Expressions](). 
Suppose that you are creating a social networking application. 
You want to create a feature that enables an administrator to perform any kind of action, such as sending a message, on members of the social networking application that satisfy certain criteria.


As before, suppose that members of this social networking application are represented by the following [Person]() class:


```java
public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    String name;
    LocalDate birthday;
    Sex gender;
    String emailAddress;
    
    // ...

    public int getAge() {
        // ...
    }

    public String getName() {
        // ...
    }
}
```


The following example prints the name of all members contained in the collection `roster` with a for-each loop:


```text
for (Person p : roster) {
    System.out.println(p.getName());
}
```


The following example prints all members contained in the collection `roster` but with the aggregate operation `forEach`:


```text
roster
    .stream()
    .forEach(e -> System.out.println(e.getName());
```


Although, in this example, the version that uses aggregate operations is longer than the one that uses a for-each loop, you will see that versions that use bulk-data operations will be more concise for more complex tasks.


The following topics are covered:


* Pipelines and Streams

* Differences Between Aggregate Operations and Iterators


Find the code excerpts described in this section in the example BulkDataOperationsExamples.
