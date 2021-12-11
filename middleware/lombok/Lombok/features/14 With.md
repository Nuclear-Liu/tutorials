# `@With`

## Overview

The next best alternative to a setter for an immutable property is to construct a clone of the object, but with a new value for this one field. 
A method to generate this clone is precisely what `@With` generates: 
a `withFieldName(newValue)` method which produces a clone except for the new value for the associated field.

For example, if you create `public class Point { private final int x, y; }`, setters make no sense because the fields are final. 
`@With` can generate a `withX(int newXValue)` method for you which will return a new point with the supplied value for `x` and the same value for `y`.

The `@With` relies on a constructor for all fields in order to do its work. 
If this constructor does not exist, your `@With` annotation will result in a compile time error message. 
You can use Lombok's own `@AllArgsConstructor`, or as `Value` will automatically produce an all args constructor as well, you can use that too. 
It's of course also acceptable if you manually write this constructor. 
It must contain all non-static fields, in the same lexical order.

Like `@Setter`, you can specify an access level in case you want the generated with method to be something other than public:
`@With(level = AccessLevel.PROTECTED)`. 
Also like `@Setter`, you can also put a `@With` annotation on a type, which means a `with` method is generated for each field (even non-final fields).

To put annotations on the generated method, you can use `onMethod=@__({@AnnotationsHere})`. 
Be careful though! This is an experimental feature. 
For more details see the documentation on the [onX]() feature.

javadoc on the field will be copied to generated with methods. 
Normally, all text is copied, and `@param` is moved to the with method, whilst `@return` lines are stripped from the with method's javadoc. 
Moved means: Deleted from the field's javadoc. It is also possible to define unique text for the with method's javadoc. 
To do that, you create a 'section' named `WITH`. 
A section is a line in your javadoc containing 2 or more dashes, then the text 'WITH', followed by 2 or more dashes, and nothing else on the line. 
If you use sections, `@return` and `@param` stripping / copying for that section is no longer done (move the `@param` line into the section).