# Extensions

The functionality of jMock can be extended by optional plug-in libraries.

## jMock Legacy

The [jMock Legacy Extension]() provides an Imposteriser that can imposterise classes as well as interfaces. 
This is useful when working with legacy code to tease apart dependencies between tightly coupled classes. 
The ClassImposteriser creates mock instances without calling the constructor of the mocked class. 
So classes with constructors that have arguments or call overideable methods of the object can be safely mocked. 
However, the ClassImposteriser cannot create mocks of final classes or mock final methods.

Included in the jMock distribution

## jMock Scripting

The [jMock Scripting Extension]() lets you define custom actions inline in the expectation, as [BeanShell]() scripts.

Included in the jMock distribution

## MockInject

[MockInject]() uses AspectJ to inject mock objects at constructor or method calls. 
The injection points can be defined with simple EDSL. 
You don't kneed to know AOP or AspectJ to use this extension.

Written by Sebastian Sickelmann.

Available from [mockinject.dev.java.net]().

## JDave

[JDave]() is a test framework that integrates with jMock. 
Of particular note is its [unfinalizer](), an instrumentation agent that strips final modifiers from classes as they are loaded into the JVM. 
Combined with the ClassImposteriser in the jMock Legacy extension (see above), this lets you mock final classes and methods.
