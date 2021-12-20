# [Understanding Java Records From Java 16](https://dzone.com/articles/what-are-java-records) _从 Java 16 了解 Java 记录_


> A look into what you can and cannot do in Java record classes from Java 16 release, including implementations, applications, and extensions.


> 了解在 Java 16 版本的 Java 记录类中可以做什么和不能做什么，包括实现、应用程序和扩展。


by Amrut Prabhu · Jul. 16, 21 · Java Zone · Tutorial


## Java Record


Java record is a type of class whose sole purpose is to drive programming with immutable data. 
Let’s look at a simple example.


```java
public record Data( int x, int y) {
}
```


So here we have created a record with header `x` and `y`. 
The `x` and `y` here are referred to as components of a record. 
Now, when we create a record, we get the following:


* Final fields based on the record components.
* Canonical constructor. (constructor based on the record components)
* An accessor method that is the same as the field’s name, an `equals` method, and a `hashcode` method out of the box already implemented for you.
* A `toString` method implementation that prints the record components along with the component names.


So an equivalent class would be like this:


```java
public class Data {

    final private int x;
    final private int y;
    public Data( int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        // ...
    }

    public int hashCode() {
       // ...
    }

    public String toString() {
        // ...
    }
}
```


## Initialization of Records


## Record Classes Cannot Be Extended Neither Support Extension
