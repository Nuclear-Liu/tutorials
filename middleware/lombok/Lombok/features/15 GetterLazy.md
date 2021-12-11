# `@Getter(lazy=true)`

## Overview

You can let lombok generate a getter which will calculate a value once, the first time this getter is called, and cache it from then on. 
This can be useful if calculating the value takes a lot of CPU, or the value takes a lot of memory. 
To use this feature, create a `private final` variable, initialize it with the expression that's expensive to run, and annotate your field with `@Getter(lazy=true)`. 
The field will be hidden from the rest of your code, and the expression will be evaluated no more than once, when the getter is first called. 
There are no magic marker values (i.e. even if the result of your expensive calculation is `null`, the result is cached) and your expensive calculation need not be thread-safe, as lombok takes care of locking.

If the initialization expression is complex, or contains generics, we recommend moving the code to a private (if possible static) method, and call that instead.
