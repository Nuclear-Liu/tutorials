# Reduction


The section [Aggregate Operations]() describes the following pipeline of operations, which calculates the average age of all male members in the collection `roster`:


```text
double average = roster
    .stream()
    .filter(p -> p.getGender() == Person.Sex.MALE)
    .mapToInt(Person::getAge)
    .average()
    .getAsDouble();
```


The JDK contains many terminal operations (such as `average`, `sum`, `min`, `max`, and `count`) that return one value by combining the contents of a stream. 
These operations are called reduction operations. 
The JDK also contains reduction operations that return a collection instead of a single value. 
Many reduction operations perform a specific task, such as finding the average of values or grouping elements into categories. 
However, the JDK provides you with the general-purpose reduction operations `reduce` and `collect`, which this section describes in detail.


This section covers the following topics:

* [The `Stream.reduce` Method]()
* [The `Stream.collect` Method]()

You can find the code excerpts described in this section in the example [`ReductionExamples`]().
