# `@NoArgsConstructor`, `@RequiredArgsConstructor`, `@AllArgsConstructor`

## Overview


This set of 3 annotations generate a constructor that will accept 1 parameter for certain fields, and simply assigns this parameter to the field.


`@NoArgsConstructor` will generate a constructor with no parameters. 
If this is not possible (because of final fields), a compiler error will result instead, unless `@NoArgsConstructor(force = true)` is used, then all final fields are initialized with `0` / `false` / `null`. 
For fields with constraints, such as `@NonNull` fields, no check is generated,so be aware that these constraints will generally not be fulfilled until those fields are properly initialized later. 
Certain java constructs, such as hibernate and the Service Provider Interface require a no-args constructor. 
This annotation is useful primarily in combination with either `@Data` or one of the other constructor generating annotations.


`@RequiredArgsConstructor` generates a constructor with 1 parameter for each field that requires special handling. 
All non-initialized final fields get a parameter, as well as any fields that are marked as `@NonNull` that aren't initialized where they are declared. 
For those fields marked with `@NonNull`, an explicit null check is also generated. 
The constructor will throw a `NullPointerException` if any of the parameters intended for the fields marked with `@NonNull` contain `null`. 
The order of the parameters match the order in which the fields appear in your class.


`@AllArgsConstructor` generates a constructor with 1 parameter for each field in your class. 
Fields marked with `@NonNull` result in null checks on those parameters.


Each of these annotations allows an alternate form, where the generated constructor is always private, and an additional static factory method that wraps around the private constructor is generated. 
This mode is enabled by supplying the `staticName` value for the annotation, like so: `@RequiredArgsConstructor(staticName="of")`. 
Such a static factory method will infer generics, unlike a normal constructor. 
This means your API users get write `MapEntry.of("foo", 5)` instead of the much longer `new MapEntry<String, Integer>("foo", 5)`.


To put annotations on the generated constructor, you can use `onConstructor=@__({@AnnotationsHere})`, but be careful; this is an experimental feature. For more details see the documentation on the [onX]() feature.


Static fields are skipped by these annotations.


Unlike most other lombok annotations, the existence of an explicit constructor does not stop these annotations from generating their own constructor. 
This means you can write your own specialized constructor, and let lombok generate the boilerplate ones as well. 
If a conflict arises (one of your constructors ends up with the same signature as one that lombok generates), a compiler error will occur.

