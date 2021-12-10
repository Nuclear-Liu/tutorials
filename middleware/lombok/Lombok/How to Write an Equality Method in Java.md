# [How to Write an Equality Method in Java](https://www.artima.com/articles/how-to-write-an-equality-method-in-java)

> by Martin Odersky, Lex Spoon, and Bill Venners
> 
> June 1, 2009


**Summary** 
    
This article describes a technique for overriding the equals method that preserves the contract of equals even when subclassses of concrete classes add new fields.


In Item 8 of Effective Java1, Josh Bloch describes the difficulty of preserving the equals contract when subclassing as a “fundamental problem of equivalence relations in object-oriented languages.” Bloch writes:

There is no way to extend an instantiable class and add a value component while preserving the equals contract, unless you are willing to forgo the benefits of object-oriented abstraction.

Chapter 28 of Programming in Scala shows an approach that allows subclasses to extend an instantiable class, add a value component, and nevertheless preserve the equals contract. 
Although in the technique is described in the book in the context of defining Scala classes, it is also applicable to classes defined in Java. 
In this article, we present the technique using text adapted from the relevant section of Programming in Scala, but with the code examples translated from Scala into Java.

## Commmon equality pitfalls

Class `java.lang.Object` defines an equals method, which subclasses may override. 
Unfortunately, it turns out that writing a correct equality method is surprisingly difficult in object-oriented languages. 
In fact, after studying a large body of Java code, the authors of a 2007 paper concluded that almost all implementations of equals methods are faulty.2

This is problematic, because equality is at the basis of many other things. 
For one, a faulty equality method for a type C might mean that you cannot reliably put an object of type C in a collection. 
You might have two elements elem1, elem2 of type C that are equal, i.e., “ `elem1.equals(elem2)`” yields true. 
Nevertheless, with commonly occurring faulty implementations of the equals method, you could still see behavior like the following:

```text
Set<C> hashSet = new java.util.HashSet<C>();
hashSet.add(elem1);
hashSet.contains(elem2);    // returns false!
```

Here are four common pitfalls3 that can cause inconsistent behavior when overriding equals:

1. Defining equals with the wrong signature.
2. Changing equals without also changing hashCode.
3. Defining equals in terms of mutable fields.
4. Failing to define equals as an equivalence relation.

These four pitfalls are discussed in the remainder of this section.

## Pitfall #1: Defining equals with the wrong signature.

Consider adding an equality method to the following class of simple points:

```java
public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // ...
}
```

A seemingly obvious, but wrong way would be to define it like this:

```text
// An utterly wrong definition of equals
public boolean equals(Point other) {
  return (this.getX() == other.getX() && this.getY() == other.getY());
}
```

What's wrong with this method? At first glance, it seems to work OK:

```text
Point p1 = new Point(1, 2);
Point p2 = new Point(1, 2);

Point q = new Point(2, 3);

System.out.println(p1.equals(p2)); // prints true

System.out.println(p1.equals(q)); // prints false
```

However, trouble starts once you start putting points into a collection:

```text
import java.util.HashSet;

HashSet<Point> coll = new HashSet<Point>();
coll.add(p1);

System.out.println(coll.contains(p2)); // prints false
```

How can it be that coll does not contain p2, even though p1 was added to it, and p1 and p2 are equal objects? 
The reason becomes clear in the following interaction, where the precise type of one of the compared points is masked. 
Define p2a as an alias of p2, but with type Object instead of Point:

```text
Object p2a = p2;
```

Now, were you to repeat the first comparison, but with the alias p2a instead of p2, you would get:

```text
System.out.println(p1.equals(p2a)); // prints false
```

What went wrong? 
In fact, the version of equals given previously does not override the standard method equals, because its type is different. 
Here is the type of the equals method as it is defined in the root class Object:

```text
public boolean equals(Object other)
```

Because the `equals` method in Point takes a Point instead of an Object as an argument, it does not override `equals` in Object. 
Instead, it is just an overloaded alternative. 
Overloading in Java is resolved by the static type of the argument, not the run-time type. 
So as long as the static type of the argument is `Point`, the equals method in `Point` is called. 
However, once the static argument is of type Object, the `equals` method in Object is called instead. 
This method has not been overridden, so it is still implemented by comparing object identity. 
That's why the comparison “`p1.equals(p2a)`” yields false even though points `p1` and `p2a` have the same x and y values. 
That's also why the contains method in HashSet returned false. 
Since that method operates on generic sets, it calls the generic equals method in Object instead of the overloaded variant in Point.

A better `equals` method is the following:

```text
// A better definition, but still not perfect
@Override public boolean equals(Object other) {
    boolean result = false;
    if (other instanceof Point) {
        Point that = (Point) other;
        result = (this.getX() == that.getX() && this.getY() == that.getY());
    }
    return result;
}
```

Now `equals` has the correct type. 
It takes a value of type Object as parameter and it yields a `boolean` result. 
The implementation of this method uses `instanceof` and a cast. 
It first tests whether the other object is also of type Point. 
If it is, it compares the coordinates of the two points and returns the result. 
Otherwise the result is `false`.

## Pitfall #2: Changing equals without also changing hashCode

If you repeat the comparison of p1 and p2a with the latest definition of Point defined previously, you will get true, as expected. 
However, if you repeat the `HashSet.contains test`, you will probably still get false:

```text
Point p1 = new Point(1, 2);
Point p2 = new Point(1, 2);

HashSet<Point> coll = new HashSet<Point>();
coll.add(p1);

System.out.println(coll.contains(p2)); // prints false (probably)
```

In fact, this outcome is not 100% certain. You might also get true from the experiment. 
If you do, you can try with some other points with coordinates 1 and 2. 
Eventually, you'll get one which is not contained in the set. 
What goes wrong here is that Point redefined equals without also redefining `hashCode`.

Note that the collection in the example above is a `HashSet`. 
This means elements of the collection are put in “hash buckets” determined by their hash code. 
The contains test first determines a hash bucket to look in and then compares the given elements with all elements in that bucket. 
Now, the last version of class Point did redefine equals, but it did not at the same time redefine hashCode. 
So hashCode is still what it was in its version in class Object: some transformation of the address of the allocated object. 
The hash codes of p1 and p2 are almost certainly different, even though the fields of both points are the same. 
Different hash codes mean with high probability different hash buckets in the set. 
The contains test will look for a matching element in the bucket which corresponds to p2's hash code. 
In most cases, point p1 will be in another bucket, so it will never be found. p1 and p2 might also end up by chance in the same hash bucket. 
In that case the test would return true.

The problem was that the last implementation of `Point` violated the contract on `hashCode` as defined for class `Object`:

_If two objects are equal according to the `equals(Object)` method, then calling the `hashCode` method on each of the two objects must produce the same integer result._

In fact, it's well known in Java that hashCode and equals should always be redefined together. 
Furthermore, hashCode may only depend on fields that equals depends on. 
For the `Point` class, the following would be a suitable definition of hashCode:

```java
public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Point) {
            Point that = (Point) other;
            result = (this.getX() == that.getX() && this.getY() == that.getY());
        }
        return result;
    }

    @Override public int hashCode() {
        return (41 * (41 + getX()) + getY());
    }
}
```

This is just one of many possible implementations of `hashCode`. 
Adding the constant 41 to one integer field x, multiplying the result with the prime number 41, and adding to that result the other integer field y gives a reasonable distribution of hash codes at a low cost in running time and code size.

Adding hashCode fixes the problems of equality when defining classes like `Point`. 
However, there are still other trouble spots to watch out for.

## Pitfall #3: Defining equals in terms of mutable fields

Consider the following slight variation of class `Point`:

```java
public class Point { 

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) { // Problematic
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Point) {
            Point that = (Point) other;
            result = (this.getX() == that.getX() && this.getY() == that.getY());
        }
        return result;
    }

    @Override public int hashCode() {
        return (41 * (41 + getX()) + getY());
    }
}
```

The only difference is that the fields x and y are no longer final, and two set methods have been added that allow clients to change the `x` and `y` values. 
The equals and `hashCode` methods are now defined in terms of these mutable fields, so their results change when the fields change. 
This can have strange effects once you put points in collections:

```text
Point p = new Point(1, 2);

HashSet<Point> coll = new HashSet<Point>();
coll.add(p);

System.out.println(coll.contains(p)); // prints true
```

Now, if you change a field in point p, does the collection still contain the point? 
We'll try it:

```text
p.setX(p.getX() + 1);

System.out.println(coll.contains(p)); // prints false (probably)
```

This looks strange. Where did `p` go? 
More strangeness results if you check whether the iterator of the set contains `p`:

```text
Iterator<Point> it = coll.iterator();
boolean containedP = false;
while (it.hasNext()) {
    Point nextP = it.next();
    if (nextP.equals(p)) {
        containedP = true;
        break;
    }
}

System.out.println(containedP); // prints true
```

So here's a set that does not contain p, yet p is among the elements of the set! 
What happened, of course, is that after the change to the x field, the point p ended up in the wrong hash bucket of the set coll. 
That is, its original hash bucket no longer corresponded to the new value of its hash code. 
In a manner of speaking, the point p “dropped out of sight” in the set coll even though it still belonged to its elements.

The lesson to be drawn from this example is that when equals and hashCode depend on mutable state, it may cause problems for users. 
If they put such objects into collections, they have to be careful never to modify the depended-on state, and this is tricky. 
If you need a comparison that takes the current state of an object into account, you should usually name it something else, not equals. 
Considering the last definition of Point, it would have been preferable to omit a redefinition of hashCode and to name the comparison method equalContents, or some other name different from equals. 
Point would then have inherited the default implementation of equals and hashCode. 
So p would have stayed locatable in coll even after the modification to its x field.

## Pitfall #4: Failing to define equals as an equivalence relation

The contract of the equals method in Object specifies that equals must implement an equivalence relation on non-null objects:

* It is reflexive: for any non-null value x, the expression x.equals(x) should return true.
* It is symmetric: for any non-null values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
* It is transitive: for any non-null values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
* It is consistent: for any non-null values x and y, multiple invocations of x.equals(y) should consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
* For any non-null value x, x.equals(null) should return false.

The definition of equals developed so far for class Point satisfies the contract for equals. 
However, things become more complicated once subclasses are considered. 
Say there is a subclass ColoredPoint of Point that adds a field color of type Color. 
Assume Color is defined as an enum:

```java
public enum Color {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET;
}
```

`ColoredPoint` overrides `equals` to take the new `color` field into account:

```java
public class ColoredPoint extends Point { // Problem: equals not symmetric

    private final Color color;

    public ColoredPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ColoredPoint) {
            ColoredPoint that = (ColoredPoint) other;
            result = (this.color.equals(that.color) && super.equals(that));
        }
        return result;
    }
}
```

This is what many programmers would likely write. Note that in this case, class `ColoredPoint` need not override `hashCode`. 
Because the new definition of equals on `ColoredPoint` is stricter than the overridden definition in Point (meaning it equates fewer pairs of objects), the contract for `hashCode` stays valid. 
If two colored points are equal, they must have the same coordinates, so their hash codes are guaranteed to be equal as well.

Taking the class ColoredPoint by itself, its definition of equals looks OK. 
However, the contract for equals is broken once points and colored points are mixed. 
Consider:

```text
Point p = new Point(1, 2);

ColoredPoint cp = new ColoredPoint(1, 2, Color.RED);

System.out.println(p.equals(cp)); // prints true

System.out.println(cp.equals(p)); // prints false
```

The comparison “p equals cp” invokes p's equals method, which is defined in class Point. 
This method only takes into account the coordinates of the two points. 
Consequently, the comparison yields true. 
On the other hand, the comparison “cp equals p” invokes cp's equals method, which is defined in class ColoredPoint. 
This method returns false, because p is not a ColoredPoint. 
So the relation defined by equals is not symmetric.

The loss in symmetry can have unexpected consequences for collections. 
Here's an example:

```text
Set<Point> hashSet1 = new java.util.HashSet<Point>();
hashSet1.add(p);
System.out.println(hashSet1.contains(cp));    // prints false

Set<Point> hashSet2 = new java.util.HashSet<Point>();
hashSet2.add(cp);
System.out.println(hashSet2.contains(p));    // prints true
```

So even though `p` and `cp` are equal, one contains test succeeds whereas the other one fails.

How can you change the definition of equals so that it becomes symmetric? 
Essentially there are two ways. 
You can either make the relation more general or more strict. 
Making it more general means that a pair of two objects, a and b, is taken to be equal if either comparing a with b or comparing b with a yields true. 
Here's code that does this:

```java
public class ColoredPoint extends Point { // Problem: equals not transitive

    private final Color color;

    public ColoredPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ColoredPoint) {
            ColoredPoint that = (ColoredPoint) other;
            result = (this.color.equals(that.color) && super.equals(that));
        }
        else if (other instanceof Point) {
            Point that = (Point) other;
            result = that.equals(this);
        }
        return result;
    }
}
```

The new definition of equals in ColoredPoint checks one more case than the old one: 
If the other object is a Point but not a ColoredPoint, the method forwards to the equals method of Point. This has the desired effect of making equals symmetric. 
Now, both “cp.equals(p)” and “p.equals(cp)” result in true. 
However, the contract for equals is still broken. 
Now the problem is that the new relation is no longer transitive! Here's a sequence of statements that demonstrates this. 
Define a point and two colored points of different colors, all at the same position:

```text
ColoredPoint redP = new ColoredPoint(1, 2, Color.RED);
ColoredPoint blueP = new ColoredPoint(1, 2, Color.BLUE);
```

Taken individually, redp is equal to p and p is equal to bluep:

```text
System.out.println(redP.equals(p)); // prints true

System.out.println(p.equals(blueP)); // prints true
```

However, comparing `redP` and `blueP` yields `false`:

```text
System.out.println(redP.equals(blueP)); // prints false
```

Hence, the transitivity clause of equals's contract is violated.

Making the equals relation more general seems to lead to a dead end. 
We'll try to make it stricter instead. One way to make equals stricter is to always treat objects of different classes as different. 
That could be achieved by modifying the equals methods in classes Point and ColoredPoint. 
In class Point, you could add an extra comparison that checks whether the run-time class of the other Point is exactly the same as this Point's class, as follows:

```java
// A technically valid, but unsatisfying, equals method
public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Point) {
            Point that = (Point) other;
            result = (this.getX() == that.getX() && this.getY() == that.getY()
                    && this.getClass().equals(that.getClass()));
        }
        return result;
    }

    @Override public int hashCode() {
        return (41 * (41 + getX()) + getY());
    }
}
```

You can then revert class `ColoredPoint`'s implementation back to the version that previously had violated the symmetry requirement:4

```java
public class ColoredPoint extends Point { // No longer violates symmetry requirement

    private final Color color;

    public ColoredPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ColoredPoint) {
            ColoredPoint that = (ColoredPoint) other;
            result = (this.color.equals(that.color) && super.equals(that));
        }
        return result;
    }
}
```

Here, an instance of class `Point` is considered to be equal to some other instance of the same class only if the objects have the same coordinates and they have the same run-time class, meaning `.getClass()` on either object returns the same value. 
The new definitions satisfy symmetry and transitivity because now every comparison between objects of different classes yields `false`. 
So a colored point can never be equal to a point. 
This convention looks reasonable, but one could argue that the new definition is too strict.

Consider the following slightly roundabout way to define a point at coordinates (1, 2):

```text
Point pAnon = new Point(1, 1) {
    @Override public int getY() {
        return 2;
    }
};
```

Is `pAnon` equal to `p`? The answer is no because the `java.lang.Class` objects associated with `p` and `pAnon` are different. 
For `p` it is `Point`, whereas for `pAnon` it is an anonymous subclass of `Point`. 
But clearly, `pAnon` is just another point at coordinates (1, 2). It does not seem reasonable to treat it as being different from p.

## The canEqual method

So it seems we are stuck. 
Is there a sane way to redefine equality on several levels of the class hierarchy while keeping its contract? 
In fact, there is such a way, but it requires one more method to redefine together with equals and hashCode. 
The idea is that as soon as a class redefines equals (and hashCode), it should also explicitly state that objects of this class are never equal to objects of some superclass that implement a different equality method. 
This is achieved by adding a method canEqual to every class that redefines equals. Here's the method's signature:

```text
public boolean canEqual(Object other)
```

The method should return `true` if the `other` object is an instance of the class in which `canEqual` is (re)defined, `false` otherwise. 
It is called from `equals` to make sure that the objects are comparable both ways. 
Here is a new (and final) implementation of class `Point` along these lines:

```java
public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Point) {
            Point that = (Point) other;
            result = (that.canEqual(this) && this.getX() == that.getX() && this.getY() == that.getY());
        }
        return result;
    }

    @Override public int hashCode() {
        return (41 * (41 + getX()) + getY());
    }

    public boolean canEqual(Object other) {
        return (other instanceof Point);
    }
}
```

The `equals` method in this version of class `Point` contains the additional requirement that the other object can equal this one, as determined by the `canEqual` method. 
The implementation of `canEqual` in `Point` states that all instances of `Point` can be equal.

Here's the corresponding implementation of `ColoredPoint`:

```java
public class ColoredPoint extends Point { // No longer violates symmetry requirement

    private final Color color;

    public ColoredPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    @Override public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof ColoredPoint) {
            ColoredPoint that = (ColoredPoint) other;
            result = (that.canEqual(this) && this.color.equals(that.color) && super.equals(that));
        }
        return result;
    }

    @Override public int hashCode() {
        return (41 * super.hashCode() + color.hashCode());
    }

    @Override public boolean canEqual(Object other) {
        return (other instanceof ColoredPoint);
    }
}
```

It can be shown that the new definition of Point and `ColoredPoint` keeps the contract of equals. 
Equality is symmetric and transitive. 
Comparing a `Point` to a `ColoredPoint` always yields `false`. 
Indeed, for any point p and colored point cp, “p.equals(cp)” will return false because “cp.canEqual(p)” will return false. 
The reverse comparison, “`cp.equals(p)`”, will also return false, because p is not a `ColoredPoint`, so the first instanceof check in the body of equals in `ColoredPoint` will fail.

On the other hand, instances of different subclasses of `Point` can be equal, as long as none of the classes redefines the equality method. 
For instance, with the new class definitions, the comparison of p and `pAnon` would yield `true`. 
Here are some examples:

```text
Point p = new Point(1, 2);

ColoredPoint cp = new ColoredPoint(1, 2, Color.INDIGO);

Point pAnon = new Point(1, 1) {
    @Override public int getY() {
        return 2;
    }
};

Set<Point> coll = new java.util.HashSet<Point>();
coll.add(p);

System.out.println(coll.contains(p)); // prints true

System.out.println(coll.contains(cp)); // prints false

System.out.println(coll.contains(pAnon)); // prints true
```

These examples demonstrate that if a superclass equals implementation defines and calls canEqual, then programmers who implement subclasses can decide whether or not their subclasses may be equal to instances of the superclass. 
Because ColoredPoint overrides canEqual, for example, a colored point may never be equal to a plain-old point. 
But because the anonymous subclass referenced from `pAnon` does not override `canEqual`, its instance can be `equal` to a `Point` instance.

One potential criticism of the `canEqual` approach is that it violates the Liskov Substitution Principle (LSP). 
For example, the technique of implementing equals by comparing run-time classes, which led to the inability to define a subclass whose instances can equal instances of the superclass, has been described as a violation of the LSP.5 
The reasoning is that the LSP states you should be able to use (substitute) a subclass instance where a superclass instance is required. 
In the previous example, however, “coll.contains(cp)” returned false even though cp's x and y values matched those of the point in the collection. 
Thus it may seem like a violation of the LSP, because you can't use a ColoredPoint here where a Point is expected. 
We believe this is the wrong interpretation, though, because the LSP doesn't require that a subclass behaves identically to its superclass, just that it behaves in a way that fulfills the contract of its superclass.

The problem with writing an `equals` method that compares run-time classes is not that it violates the LSP, but that it doesn't give you a way to create a subclass whose instances can equal superclass instances. 
For example, had we used the run-time class technique in the previous example, “coll.contains(pAnon)” would have returned false, and that's not what we wanted. 
By contrast, we really did want “coll.contains(cp)” to return false, because by overriding equals in `ColoredPoint`, we were basically saying that an indigo-colored point at coordinates (1, 2) is not the same thing as an uncolored point at (1, 2). 
Thus, in the previous example we were able to pass two different `Point` subclass instances to the collection's contains method, and we got back two different answers, both correct.

## End Notes

1. Bloch, Joshua. Effective Java Second Edition. Addison-Wesley, 2008.

2. Vaziri, Mandana, Frank Tip, Stephen Fink, and Julian Dolby. “Declarative Object Identity Using Relation Types.&rdquo In Proc. ECOOP 2007, pages 54–78. 2007.

3. All but the third of these pitfalls are described in the book, Effective Java Second Edition, by Joshua Bloch.
   Bloch, Joshua. Effective Java Second Edition. Addison-Wesley, 2008.

4. Given the new implementation of equals in Point, this version of ColoredPoint no longer violates the symmetry requirement.

5. Bloch, Joshua. Effective Java Second Edition. Addison-Wesley, 2008. page 39.

## Resources

![programming_in_scala](./Scala.png)

This article is adapted from a section of chapter 28 of the book Programming in Scala:
[http://www.artima.com/shop/programming_in_scala](http://www.artima.com/shop/programming_in_scala)

### Talk back!

Have an opinion? Readers have already posted [47 comments]() about this article. Why not [add yours]()?

### About the authors

**Bill Venners** is president of Artima, Inc., publisher of the Artima Developer website (www.artima.com). 
He is author of the book, Inside the Java Virtual Machine, a programmer-oriented survey of the Java platform's architecture and internals. 
His popular columns in JavaWorld magazine covered Java internals, object-oriented design, and Jini. 
Active in the Jini Community since its inception, Bill led the Jini Community's ServiceUI project, whose ServiceUI API became the de facto standard way to associate user interfaces to Jini services. 
Bill is also the lead developer and designer of ScalaTest, an open source testing tool for Scala and Java developers.

**Lex Spoon** is a software engineer at Google, Inc. 
He worked on Scala for two years as a post-doc at EPFL. 
He has a Ph.D. in computer science from Georgia Tech, where he worked on static analysis of dynamic languages. 
In addition to Scala, he has worked on a wide variety of programming languages, ranging from the dynamic language Smalltalk to the scientific language X10. 
He and his wife currently live in Atlanta with two cats, a chihuahua, and a turtle.

Martin Odersky is the creator of the Scala language. 
As a professor at EPFL in Lausanne, Switzerland he is working on programming languages, more specifically languages for object-oriented and functional programming. 
His research thesis is that the two paradigms are two sides of the same coin, to be identified as much as possible. 
To prove this, he has experimented with a number of language designs, from Pizza to GJ to Functional Nets. He has also influenced the development of Java as a co-designer of Java generics and as the original author of the current javac reference compiler. 
Since 2001 he has concentrated on designing, implementing, and refining the Scala programming language.
