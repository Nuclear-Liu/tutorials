# [Record Classes](https://docs.oracle.com/en/java/javase/17/language/records.html) _记录类_


Record classes, which are a special kind of class, help to model plain data aggregates with less ceremony than normal classes.


记录类是一种特殊的类，有助于用比普通类更少的仪式来对普通数据聚合进行建模。


For background information about record classes, see [JEP 395](https://openjdk.java.net/jeps/395).


有关记录类的背景信息，请参阅 [JEP 395]() 。


A record declaration specifies in a header a description of its contents; the appropriate accessors, constructor, `equals`, `hashCode`, and `toString` methods are created automatically. 
A record's fields are `final` because the class is intended to serve as a simple "data carrier".


记录声明在头中指定其内容的描述；自动创建适当的访问器、构造函数、`equals`、`hashCode` 和 `toString` 方法。
记录的字段是 `final` ，因为该类旨在充当简单的“数据载体”。


For example, here is a record class with two fields:


例如，这是一个包含两个字段的记录类：


```java
record Rectangle(double length, double width) { }
```


This concise declaration of a rectangle is equivalent to the following normal class:


这个矩形的简洁声明等效于以下普通类：


```java
public final class Rectangle {
    private final double length;
    private final double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    double length() { return this.length; }
    double width()  { return this.width; }

    // Implementation of equals() and hashCode(), which specify
    // that two record objects are equal if they
    // are of the same type and contain equal field values.
    // equals() 和 hashCode() 的实现，它们指定如果两个记录对象类型相同并且包含相等的字段值，则它们是相等的。
    public boolean equals...
    public int hashCode...

    // An implementation of toString() that returns a string
    // representation of all the record class's fields,
    // including their names.
    // toString() 的实现，它返回所有记录类字段的字符串表示形式，包括它们的名称。
    public String toString() {...}
}
```


A record class declaration consists of a name; optional type parameters (generic record declarations are supported); a header, which lists the "components" of the record; and a body.


记录类声明由名称组成；可选类型参数（支持通用记录声明）；一个头，它列出了记录的“组件”；和一个身体。


A record class declares the following members automatically:


记录类自动声明以下成员：


* For each component in the header, the following two members:
  * A `private final` field with the same name and declared type as the record component. This field is sometimes referred to as a _component field_.
  * A `public` accessor method with the same name and type of the component; in the `Rectangle` record class example, these methods are `Rectangle::length()` and `Rectangle::width()`.
* A _canonical constructor_ whose signature is the same as the header. This constructor assigns each argument from the new expression that instantiates the record class to the corresponding component field.
* Implementations of the `equals` and `hashCode` methods, which specify that two record classes are equal if they are of the same type and contain equal component values.
* An implementation of the `toString` method that includes the string representation of all the record class's components, with their names.


* 对于标头中的每个组件，以下两个成员：
  * 与记录组件具有相同名称和声明类型的 `private final` 字段。该字段有时称为 _组件字段_ 。
  * 一个与组件名称和类型相同的 `public` 访问器方法；在 `Rectangle` 记录类示例中，这些方法是 `Rectangle::length()` 和 `Rectangle::width()` 。
* _规范构造函数_ 其签名与标头相同。此构造函数将实例化记录类的 new 表达式中的每个参数分配给相应的组件字段。
* `equals` 和 `hashCode` 方法的实现，它们指定如果两个记录类属于相同类型并且包含相等的组件值，则它们是相等的。
* `toString` 方法的实现，包括所有记录类组件的字符串表示，以及它们的名称。


As record classes are just special kinds of classes, you create a record object (an instance of a record class) with the `new` keyword, for example:


由于记录类只是特殊类型的类，您可以使用 `new` 关键字创建一个记录对象（记录类的实例），例如：


```text
Rectangle r = new Rectangle(4,5);
```


## The Canonical Constructor of a Record Class _记录类的规范构造函数_


The following example explicitly declares the canonical constructor for the `Rectangle` record class. 
It verifies that `length` and `width` are greater than zero. 
If not, it throws an `IllegalArgumentException`:


以下示例显式声明了 `Rectangle` 记录类的规范构造函数。
它验证 `length` 和 `width` 是否大于零。
如果没有，它会抛出一个 `IllegalArgumentException`：


```java
record Rectangle(double length, double width) {
    public Rectangle(double length, double width) {
        if (length <= 0 || width <= 0) {
            throw new java.lang.IllegalArgumentException(
                String.format("Invalid dimensions: %f, %f", length, width));
        }
        this.length = length;
        this.width = width;
    }
}
```


Repeating the record class's components in the signature of the canonical constructor can be tiresome and error-prone. 
To avoid this, you can declare a _compact constructor_ whose signature is implicit (derived from the components automatically).


在规范构造函数的签名中重复记录类的组件可能令人厌烦且容易出错。
为了避免这种情况，您可以声明一个 _紧凑的构造函数_ ，其签名是隐式的（自动从组件派生）。


For example, the following compact constructor declaration validates `length` and `width` in the same way as in the previous example:


例如，以下紧凑构造函数声明以与前一个示例相同的方式验证 `length` 和 `width` ：


```java
record Rectangle(double length, double width) {
    public Rectangle {
        if (length <= 0 || width <= 0) {
            throw new java.lang.IllegalArgumentException(
                String.format("Invalid dimensions: %f, %f", length, width));
        }
    }
}
```


This succinct form of constructor declaration is only available in a record class. 
Note that the statements `this.length = length;` and `this.width = width;` which appear in the canonical constructor do not appear in the compact constructor. 
At the end of a compact constructor, its implicit formal parameters are assigned to the record class's private fields corresponding to its components.


这种简洁形式的构造函数声明仅在记录类中可用。
请注意，出现在规范构造函数中的语句 `this.length = length;` 和 `this.width = width;` 不会出现在紧凑构造函数中。
在紧凑构造函数的末尾，它的隐式形式参数被分配给记录类与其组件对应的私有字段。


## Explicit Declaration of Record Class Members _记录类成员的显式声明_


You can explicitly declare any of the members derived from the header, such as the `public` accessor methods that correspond to the record class's components, for example:


您可以显式声明从头文件派生的任何成员，例如对应于记录类组件的 `public` 访问器方法，例如：


```java
record Rectangle(double length, double width) {
 
    // Public accessor method
    public double length() {
        System.out.println("Length is " + length);
        return length;
    }
}
```


If you implement your own accessor methods, then ensure that they have the same characteristics as implicitly derived accessors (for example, they're declared `public` and have the same return type as the corresponding record class component). 
Similarly, if you implement your own versions of the `equals`, `hashCode`, and `toString` methods, then ensure that they have the same characteristics and behavior as those in the `java.lang.Record` class, which is the common superclass of all record classes.


如果您实现自己的访问器方法，请确保它们具有与隐式派生访问器相同的特征（例如，它们被声明为 `public` 并且与相应的记录类组件具有相同的返回类型）。
类似地，如果您实现自己版本的 `equals`、`hashCode` 和 `toString` 方法，那么请确保它们具有与 `java.lang.Record` 类中的特性和行为相同的特性和行为，即所有记录类的公共超类。


You can declare static fields, static initializers, and static methods in a record class, and they behave as they would in a normal class, for example:


您可以在记录类中声明静态字段、静态初始值设定项和静态方法，它们的行为就像在普通类中一样，例如：


```java
record Rectangle(double length, double width) {
    
    // Static field
    static double goldenRatio;

    // Static initializer
    static {
        goldenRatio = (1 + Math.sqrt(5)) / 2;
    }

    // Static method
    public static Rectangle createGoldenRectangle(double width) {
        return new Rectangle(width, width * goldenRatio);
    }
}
```


You cannot declare instance variables (non-static fields) or instance initializers in a record class.


您不能在记录类中声明实例变量（非静态字段）或实例初始值设定项。


For example, the following record class declaration doesn't compile:


例如，以下记录类声明无法编译：


```java
record Rectangle(double length, double width) {

    // Field declarations must be static:
    BiFunction<Double, Double, Double> diagonal;

    // Instance initializers are not allowed in records:
    {
        diagonal = (x, y) -> Math.sqrt(x*x + y*y);
    }
}
```


You can declare instance methods in a record class, independent of whether you implement your own accessor methods. 
You can also declare nested classes and interfaces in a record class, including nested record classes (which are implicitly static). 
For example:


您可以在记录类中声明实例方法，而与您是否实现自己的访问器方法无关。
您还可以在记录类中声明嵌套类和接口，包括嵌套记录类（隐式静态）。
例如：


```java
record Rectangle(double length, double width) {

    // Nested record class
    record RotationAngle(double angle) {
        public RotationAngle {
            angle = Math.toRadians(angle);
        }
    }
    
    // Public instance method
    public Rectangle getRotatedRectangleBoundingBox(double angle) {
        RotationAngle ra = new RotationAngle(angle);
        double x = Math.abs(length * Math.cos(ra.angle())) +
                   Math.abs(width * Math.sin(ra.angle()));
        double y = Math.abs(length * Math.sin(ra.angle())) +
                   Math.abs(width * Math.cos(ra.angle()));
        return new Rectangle(x, y);
    }
}
```


You cannot declare `native` methods in a record class.


您不能在记录类中声明 `native` 方法。


## Features of Record Classes _记录类的特性_


A record class is implicitly `final`, so you cannot explicitly extend a record class. 
However, beyond these restrictions, record classes behave like normal classes:


记录类是隐式的 `final` ，因此您不能显式扩展记录类。
但是，除了这些限制之外，记录类的行为与普通类一样：

* You can create a generic record class, for example: _您可以创建一个通用的记录类，例如：_

    ```java
    record Triangle<C extends Coordinate> (C top, C left, C right) { }
    ```

* You can declare a record class that implements one or more interfaces, for example: _您可以声明一个实现一个或多个接口的记录类，例如：_

    ```java
    import java.lang.annotation.*;
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface GreaterThanZero { }
    ```

    ```java
    record Rectangle(
        @GreaterThanZero double length,
        @GreaterThanZero double width) { }
    ```

    If you annotate a record component, then the annotation may be propagated to members and constructors of the record class. 
    This propagation is determined by the contexts in which the annotation interface is applicable. 
    In the previous example, the `@Target(ElementType.FIELD)` meta-annotation means that the `@GreaterThanZero` annotation is propagated to the field corresponding to the record component. 
    Consequently, this record class declaration would be equivalent to the following normal class declaration:

    如果您注解记录组件，则注解可能会传播到记录类的成员和构造函数。
    这种传播是由注解接口适用的上下文决定的。
    在前面的示例中， `@Target(ElementType.FIELD)` 元注释意味着 `@GreaterThanZero` 注释被传播到与记录组件对应的字段。
    因此，此记录类声明将等效于以下正常类声明：

    ```java
    public final class Rectangle {
        private final @GreaterThanZero double length;
        private final @GreaterThanZero double width;
        
        public Rectangle(double length, double width) {
            this.length = length;
            this.width = width;
        }
        
        double length() { return this.length; }
        double width() { return this.width; }
    }
    ```


## Record Classes and Sealed Classes and Interfaces _记录类和密封类和接口_


Record classes work well with sealed classes and interfaces. 
See Record Classes as Permitted Subclasses for an example.


记录类与密封类和接口配合得很好。
有关示例，请参见 [将类记录为允许的子类]() 。


## Local Record Classes _本地记录类_


A local record class is similar to a local class; it's a record class defined in the body of a method.


本地记录类类似于本地类；它是在方法体中定义的记录类。


In the following example, a merchant is modeled with a record class, `Merchant`. 
A sale made by a merchant is also modeled with a record class, `Sale`. 
Both `Merchant` and `Sale` are top-level record classes. 
The aggregation of a merchant and their total monthly sales is modeled with a local record class, `MonthlySales`, which is declared inside the findTopMerchants method. 
This local record class improves the readability of the stream operations that follow:


```java
import java.time.*;
import java.util.*;
import java.util.stream.*;

record Merchant(String name) { }

record Sale(Merchant merchant, LocalDate date, double value) { }

public class MerchantExample {
    
    List<Merchant> findTopMerchants(
        List<Sale> sales, List<Merchant> merchants, int year, Month month) {
    
        // Local record class
        record MerchantSales(Merchant merchant, double sales) {}

        return merchants.stream()
            .map(merchant -> new MerchantSales(
                merchant, this.computeSales(sales, merchant, year, month)))
            .sorted((m1, m2) -> Double.compare(m2.sales(), m1.sales()))
            .map(MerchantSales::merchant)
            .collect(Collectors.toList());
    }   
    
    double computeSales(List<Sale> sales, Merchant mt, int yr, Month mo) {
        return sales.stream()
            .filter(s -> s.merchant().name().equals(mt.name()) &&
                s.date().getYear() == yr &&
                s.date().getMonth() == mo)
            .mapToDouble(s -> s.value())
            .sum();
    }    

    public static void main(String[] args) {
        
        Merchant sneha = new Merchant("Sneha");
        Merchant raj = new Merchant("Raj");
        Merchant florence = new Merchant("Florence");
        Merchant leo = new Merchant("Leo");
        
        List<Merchant> merchantList = List.of(sneha, raj, florence, leo);
        
        List<Sale> salesList = List.of(
            new Sale(sneha,    LocalDate.of(2020, Month.NOVEMBER, 13), 11034.20),
            new Sale(raj,      LocalDate.of(2020, Month.NOVEMBER, 20),  8234.23),
            new Sale(florence, LocalDate.of(2020, Month.NOVEMBER, 19), 10003.67),
            // ...
            new Sale(leo,      LocalDate.of(2020, Month.NOVEMBER,  4),  9645.34));
        
        MerchantExample app = new MerchantExample();
        
        List<Merchant> topMerchants =
            app.findTopMerchants(salesList, merchantList, 2020, Month.NOVEMBER);
        System.out.println("Top merchants: ");
        topMerchants.stream().forEach(m -> System.out.println(m.name()));
    }
}
```


Like nested record classes, local record classes are implicitly static, which means that their own methods can't access any variables of the enclosing method, unlike local classes, which are never static.


## Static Members of Inner Classes _内部类的静态成员_


Prior to Java SE 16, you could not declare an explicitly or implicitly static member in an inner class unless that member is a constant variable. 
This means that an inner class cannot declare a record class member because nested record classes are implicitly static.


In Java SE 16 and later, an inner class may declare members that are either explicitly or implicitly static, which includes record class members. 
The following example demonstrates this:


```java
public class ContactList {
    
    record Contact(String name, String number) { }
    
    public static void main(String[] args) {
        
        class Task implements Runnable {
            
            // Record class member, implicitly static,
            // declared in an inner class
            Contact c;
            
            public Task(Contact contact) {
                c = contact;
            }
            public void run() {
                System.out.println(c.name + ", " + c.number);
            }
        }        
        
        List<Contact> contacts = List.of(
            new Contact("Sneha", "555-1234"),
            new Contact("Raj", "555-2345"));
        contacts.stream()
                .forEach(cont -> new Thread(new Task(cont)).start());
    }
}
```


## Record Serialization _记录序列化_


You can serialize and deserialize instances of record classes, but you can't customize the process by providing `writeObject`, `readObject`, `readObjectNoData`, writeExternal, or readExternal methods. The components of a record class govern serialization, while the canonical constructor of a record class governs deserialization. See Serializable Records for more information and an extended example. See also the section Serialization of Records in the Java Object Serialization Specification.


## APIs Related to Record Classes _与记录类相关的 API_


The abstract class java.lang.Record is the common superclass of all record classes.


You might get a compiler error if your source file imports a class named Record from a package other than java.lang. A Java source file automatically imports all the types in the java.lang package though an implicit import java.lang.*; statement. This includes the java.lang.Record class, regardless of whether preview features are enabled or disabled.


Consider the following class declaration of com.myapp.Record:


```java
package com.myapp;

public class Record {
    public String greeting;
    public Record(String greeting) {
        this.greeting = greeting;
    }
}
```


The following example, org.example.MyappPackageExample, imports com.myapp.Record with a wildcard but doesn't compile:


```java
package org.example;
import com.myapp.*;

public class MyappPackageExample {
    public static void main(String[] args) {
       Record r = new Record("Hello world!");
    }
}
```


The compiler generates an error message similar to the following:


```log
./org/example/MyappPackageExample.java:6: error: reference to Record is ambiguous
       Record r = new Record("Hello world!");
       ^
  both class com.myapp.Record in com.myapp and class java.lang.Record in java.lang match

./org/example/MyappPackageExample.java:6: error: reference to Record is ambiguous
       Record r = new Record("Hello world!");
                      ^
  both class com.myapp.Record in com.myapp and class java.lang.Record in java.lang match
```


Both Record in the com.myapp package and Record in the java.lang package are imported with a wildcard. Consequently, neither class takes precedence, and the compiler generates an error when it encounters the use of the simple name Record.


To enable this example to compile, change the import statement so that it imports the fully qualified name of Record:


```java
import com.myapp.Record;
```


> **Note:**The introduction of classes in the java.lang package is rare but necessary from time to time, such as Enum in Java SE 5, Module in Java SE 9, and Record in Java SE 14.


The class java.lang.Class has two methods related to record classes:


* `RecordComponent[] getRecordComponents()`: Returns an array of java.lang.reflect.RecordComponent objects, which correspond to the record class's components.
* `boolean isRecord(): Similar to isEnum()` except that it returns true if the class was declared as a record class.
