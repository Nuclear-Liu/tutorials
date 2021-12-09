# `@EqualsAndHashCode`

## Overview


Any class definition may be annotated with `@EqualsAndHashCode` to let lombok generate implementations of the `equals(Object other)` and `hashCode()` methods. 
By default, it'll use all non-`static`, non-transient fields, but you can modify which fields are used (and even specify that the output of various methods is to be used) by marking type members with `@EqualsAndHashCode.Include` or `@EqualsAndHashCode.Exclude`. 
Alternatively, you can specify exactly which fields or methods you wish to be used by marking them with `@EqualsAndHashCode.Include` and using `@EqualsAndHashCode(onlyExplicitlyIncluded = true)`.


If applying `@EqualsAndHashCode` to a class that extends another, this feature gets a bit trickier. 
Normally, auto-generating an `equals` and `hashCode` method for such classes is a bad idea, as the superclass also defines fields, which also need `equals`/`hashCode` code but this code will not be generated. 
By setting `callSuper` to `true`, you can include the equals and hashCode methods of your superclass in the generated methods. 
For `hashCode`, the result of `super.hashCode()` is included in the hash algorithm, and forequals, the generated method will return false if the super implementation thinks it is not equal to the passed in object. 
Be aware that not all equals implementations handle this situation properly. However, lombok-generated equals implementations do handle this situation properly, so you can safely call your superclass equals if it, too, has a lombok-generated equals method. If you have an explicit superclass you are forced to supply some value for callSuper to acknowledge that you've considered it; failure to do so results in a warning.

Setting callSuper to true when you don't extend anything (you extend java.lang.Object) is a compile-time error, because it would turn the generated equals() and hashCode() implementations into having the same behaviour as simply inheriting these methods from java.lang.Object: only the same object will be equal to each other and will have the same hashCode. Not setting callSuper to true when you extend another class generates a warning, because unless the superclass has no (equality-important) fields, lombok cannot generate an implementation for you that takes into account the fields declared by your superclasses. You'll need to write your own implementations, or rely on the callSuper chaining facility. You can also use the lombok.equalsAndHashCode.callSuper config key.

NEW in Lombok 0.10: Unless your class is final and extends java.lang.Object, lombok generates a canEqual method which means JPA proxies can still be equal to their base class, but subclasses that add new state don't break the equals contract. The complicated reasons for why such a method is necessary are explained in this paper: How to Write an Equality Method in Java. If all classes in a hierarchy are a mix of scala case classes and classes with lombok-generated equals methods, all equality will 'just work'. If you need to write your own equals methods, you should always override canEqual if you change equals and hashCode.

NEW in Lombok 1.14.0: To put annotations on the other parameter of the equals (and, if relevant, canEqual) method, you can use onParam=@__({@AnnotationsHere}). Be careful though! This is an experimental feature. For more details see the documentation on the onX feature.

NEW in Lombok 1.18.16: The result of the generated hashCode() can be cached by setting cacheStrategy to a value other than CacheStrategy.NEVER. Do not use this if objects of the annotated class can be modified in any way that would lead to the result of hashCode() changing.