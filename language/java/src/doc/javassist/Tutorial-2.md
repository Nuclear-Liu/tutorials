<div align="right">Getting Started with Javassist</div>

<p><br>

<a name="intro">
<h2>4. Introspection and customization</h2>

<p><code>CtClass</code> provides methods for introspection.  The
introspective ability of Javassist is compatible with that of
the Java reflection API.  <code>CtClass</code> provides
<code>getName()</code>, <code>getSuperclass()</code>,
<code>getMethods()</code>, and so on.
<code>CtClass</code> also provides methods for modifying a class
definition.  It allows adding a new field, constructor, and method.
Instrumenting a method body is also possible.

<p>
Methods are represented by <code>CtMethod</code> objects. 
<code>CtMethod</code> provides several methods for modifying
the definition of the method.  Note that if a method is inherited
from a super class, then 
the same <code>CtMethod</code> object
that represents the inherited method represents the method declared
in that super class.
A <code>CtMethod</code> object corresponds to every method declaration.

<p>
For example, if class <code>Point</code> declares method <code>move()</code>
and a subclass <code>ColorPoint</code> of <code>Point</code> does
not override <code>move()</code>, the two <code>move()</code> methods
declared in <code>Point</code> and inherited in <code>ColorPoint</code>
are represented by the identical <code>CtMethod</code> object.
If the method definition represented by this 
<code>CtMethod</code> object is modified, the modification is
reflected on both the methods.
If you want to modify only the <code>move()</code> method in
<code>ColorPoint</code>, you first have to add to <code>ColorPoint</code>
a copy of the <code>CtMethod</code> object representing <code>move()</code>
in <code>Point</code>.  A copy of the the <code>CtMethod</code> object
can be obtained by <code>CtNewMethod.copy()</code>.


<p><hr width="40%">

<ul>
Javassist does not allow removing a method or field, but it allows
changing the name.  So if a method is not necessary any more, it should be
renamed and changed to be a private method by calling
<code>setName()</code>
and <code>setModifiers()</code> declared in <code>CtMethod</code>.

<p>Javassist does not allow adding an extra parameter to an existing
method, either.  Instead of doing that, a new method receiving the
extra parameter as well as the other parameters should be added to the
same class.  For example, if you want to add an extra <code>int</code>
parameter <code>newZ</code> to a method:

<ul><pre>void move(int newX, int newY) { x = newX; y = newY; }</pre></ul>

<p>in a <code>Point</code> class, then you should add the following
method to the <code>Point</code> class:

<ul><pre>void move(int newX, int newY, int newZ) {
    // do what you want with newZ.
    move(newX, newY);
}</pre></ul>

</ul>

<p><hr width="40%">

<p>Javassist also provides low-level API for directly editing a raw
class file.  For example, <code>getClassFile()</code> in
<code>CtClass</code> returns a <code>ClassFile</code> object
representing a raw class file.  <code>getMethodInfo()</code> in
<code>CtMethod</code> returns a <code>MethodInfo</code> object
representing a <code>method_info</code> structure included in a class
file.  The low-level API uses the vocabulary from the Java Virtual
machine specification.  The users must have the knowledge about class
files and bytecode.  For more details, the users should see the
<a href="tutorial3.html#intro"><code>javassist.bytecode</code> package</a>.

<p>The class files modified by Javassist requires the
<code>javassist.runtime</code> package for runtime support
only if some special identifiers starting with <code>$</code>
are used.  Those special identifiers are described below.
The class files modified without those special identifiers
do not need the <code>javassist.runtime</code> package or any
other Javassist packages at runtime.
For more details, see the API documentation
of the <code>javassist.runtime</code> package.

<p><br>

<a name="before">
<h3>4.1 Inserting source text at the beginning/end of a method body</h3>

<p><code>CtMethod</code> and <code>CtConstructor</code> provide
methods <code>insertBefore()</code>, <code>insertAfter()</code>, and
<code>addCatch()</code>.  They are used for inserting a code fragment
into the body of an existing method.  The users can specify those code
fragments with <em>source text</em> written in Java.
Javassist includes a simple Java compiler for processing source
text.  It receives source text
written in Java and compiles it into Java bytecode, which will be
<em>inlined</em> into a method body.

<p>
Inserting a code fragment at the position specified by a line number
is also possible
(if the line number table is contained in the class file).
<code>insertAt()</code> in <code>CtMethod</code> and
<code>CtConstructor</code> takes source text and a line number in the source
file of the original class definition.
It compiles the source text and inserts the compiled code at the line number.

<p>The methods <code>insertBefore()</code>, <code>insertAfter()</code>,
<code>addCatch()</code>, and <code>insertAt()</code>
receive a <code>String</code> object representing
a statement or a block.  A statement is a single control structure like
<code>if</code> and <code>while</code> or an expression ending with
a semi colon (<code>;</code>).  A block is a set of
statements surrounded with braces <code>{}</code>.
Hence each of the following lines is an example of valid statement or block:

<ul><pre>System.out.println("Hello");
{ System.out.println("Hello"); }
if (i < 0) { i = -i; }
</pre></ul>

<p>The statement and the block can refer to fields and methods.
They can also refer to the parameters
to the method that they are inserted into
if that method was compiled with the -g option
(to include a local variable attribute in the class file).
Otherwise, they must access the method parameters through the special
variables <code>$0</code>, <code>$1</code>, <code>$2</code>, ... described
below.  
<em>Accessing local variables declared in the method is not allowed</em>
although declaring a new local variable in the block is allowed.
However, <code>insertAt()</code> allows the statement and the block
to access local variables
if these variables are available at the specified line number
and the target method was compiled with the -g option.


<!--
<p><center><table border=8 cellspacing=0 bordercolor="#cfcfcf">
<tr><td bgcolor="#cfcfcf">
<b>Tip:</b>
<br>&nbsp&nbsp&nbsp Local variables are not accessible.&nbsp&nbsp
</td></tr>
</table></center>
-->

<p>The <code>String</code> object passed to the methods
<code>insertBefore()</code>, <code>insertAfter()</code>,
<code>addCatch()</code>, and <code>insertAt()</code> are compiled by
the compiler included in Javassist.
Since the compiler supports language extensions,
several identifiers starting with <code>$</code>
have special meaning:

<ul><table border=0>
<tr>
<td><code>$0</code>, <code>$1</code>, <code>$2</code>, ... &nbsp &nbsp</td>
<td><code>this</code> and actual parameters</td>
</tr>

<tr>
<td><code>$args</code></td>
<td>An array of parameters.
The type of <code>$args</code> is <code>Object[]</code>.
</td>
</tr>

<tr>
<td><code>$$</code></td>
<td rowspan=2>All actual parameters.<br>
For example, <code>m($$)</code> is equivalent to
<code>m($1,$2,</code>...<code>)</code></td>
</tr>

<tr><td>&nbsp</td></tr>

<tr>
<td><code>$cflow(</code>...<code>)</code></td>
<td><code>cflow</code> variable</td>
</tr>

<tr>
<td><code>$r</code></td>
<td>The result type.  It is used in a cast expression.</td>
</tr>

<tr>
<td><code>$w</code></td>
<td>The wrapper type.  It is used in a cast expression.</td>
</tr>

<tr>
<td><code>$_</code></td>
<td>The resulting value</td>
</tr>

<tr>
<td><code>$sig</code></td>
<td>An array of <code>java.lang.Class</code> objects representing
the formal parameter types.
</td>
</tr>

<tr>
<td><code>$type</code></td>
<td>A <code>java.lang.Class</code> object representing
the formal result type.</td>
</tr>

<tr>
<td><code>$class</code></td>
<td>A <code>java.lang.Class</code> object representing
the class currently edited.</td>
</tr>

</table>
</ul>

<h4>$0, $1, $2, ...</h4>

<p>The parameters passed to the target method
are accessible with
<code>$1</code>, <code>$2</code>, ... instead of
the original parameter names.
<code>$1</code> represents the
first parameter, <code>$2</code> represents the second parameter, and
so on.  The types of those variables are identical to the parameter
types.
<code>$0</code> is
equivalent to <code>this</code>.  If the method is static,
<code>$0</code> is not available.

<p>These variables are used as following.  Suppose that a class
<code>Point</code>:

    class Point {
        int x, y;
        void move(int dx, int dy) { x += dx; y += dy; }
    }

<p>To print the values of <code>dx</code> and <code>dy</code>
whenever the method <code>move()</code> is called, execute this
program:

<ul><pre>ClassPool pool = ClassPool.getDefault();
CtClass cc = pool.get("Point");
CtMethod m = cc.getDeclaredMethod("move");
m.insertBefore("{ System.out.println($1); System.out.println($2); }");
cc.writeFile();
</pre></ul>

<p>Note that the source text passed to <code>insertBefore()</code> is
surrounded with braces <code>{}</code>.
<code>insertBefore()</code> accepts only a single statement or a block
surrounded with braces.

<p>The definition of the class <code>Point</code> after the
modification is like this:

    class Point {
        int x, y;
        void move(int dx, int dy) {
            { System.out.println(dx); System.out.println(dy); }
            x += dx; y += dy;
        }
    }

<p><code>$1</code> and <code>$2</code> are replaced with
<code>dx</code> and <code>dy</code>, respectively.

<p><code>$1</code>, <code>$2</code>, <code>$3</code> ... are
updatable.  If a new value is assigend to one of those variables,
then the value of the parameter represented by that variable is
also updated.


<h4>$args</h4>

<p>The variable <code>$args</code> represents an array of all the
parameters.  The type of that variable is an array of class
<code>Object</code>.  If a parameter type is a primitive type such as
<code>int</code>, then the parameter value is converted into a wrapper
object such as <code>java.lang.Integer</code> to store in
<code>$args</code>.  Thus, <code>$args[0]</code> is equivalent to
<code>$1</code> unless the type of the first parameter is a primitive
type.  Note that <code>$args[0]</code> is not equivalent to
<code>$0</code>; <code>$0</code> represents <code>this</code>.

<p>If an array of <code>Object</code> is assigned to
<code>$args</code>, then each element of that array is
assigned to each parameter.  If a parameter type is a primitive
type, the type of the corresponding element must be a wrapper type.
The value is converted from the wrapper type to the primitive type
before it is assigned to the parameter.

<h4>$$</h4>

<p>The variable <code>$$</code> is abbreviation of a list of
all the parameters separated by commas.
For example, if the number of the parameters
to method <code>move()</code> is three, then

<ul><pre>move($$)</pre></ul>

<p>is equivalent to this:

<ul><pre>move($1, $2, $3)</pre></ul>

<p>If <code>move()</code> does not take any parameters,
then <code>move($$)</code> is
equivalent to <code>move()</code>.

<p><code>$$</code> can be used with another method.
If you write an expression:

<ul><pre>exMove($$, context)</pre></ul>

<p>then this expression is equivalent to:

<ul><pre>exMove($1, $2, $3, context)</pre></ul>

<p>Note that <code>$$</code> enables generic notation of method call
with respect to the number of parameters.
It is typically used with <code>$proceed</code> shown later.

<h4>$cflow</h4>

<p><code>$cflow</code> means "control flow".
This read-only variable returns the depth of the recursive calls
to a specific method.

<p>Suppose that the method shown below is represented by a
<code>CtMethod</code> object <code>cm</code>:

<ul><pre>int fact(int n) {
    if (n <= 1)
        return n;
    else
        return n * fact(n - 1);
}</pre></ul>

<p>To use <code>$cflow</code>, first declare that <code>$cflow</code>
is used for monitoring calls to the method <code>fact()</code>:

<ul><pre>CtMethod cm = ...;
cm.useCflow("fact");</pre></ul>

<p>The parameter to <code>useCflow()</code> is the identifier of the
declared <code>$cflow</code> variable.  Any valid Java name can be
used as the identifier.  Since the identifier can also include
<code>.</code> (dot), for example, <code>"my.Test.fact"</code>
is a valid identifier.

<p>Then, <code>$cflow(fact)</code> represents the depth of the
recursive calls to the method specified by <code>cm</code>.  The value
of <code>$cflow(fact)</code> is 0 (zero) when the method is
first called whereas it is 1 when the method is recursively called
within the method.  For example,

<ul><pre>
cm.insertBefore("if ($cflow(fact) == 0)"
              + "    System.out.println(\"fact \" + $1);");
</pre></ul>

<p>translates the method <code>fact()</code> so that it shows the
parameter.  Since the value of <code>$cflow(fact)</code> is checked,
the method <code>fact()</code> does not show the parameter if it is
recursively called within <code>fact()</code>.

<p>The value of <code>$cflow</code> is the number of stack frames
associated with the specified method <code>cm</code>
under the current topmost
stack frame for the current thread.  <code>$cflow</code> is also
accessible within a method different from the specified method
<code>cm</code>.

<h4>$r</h4>

<p><code>$r</code> represents the result type (return type) of the method.
It must be used as the cast type in a cast expression.
For example, this is a typical use:

<ul><pre>Object result = ... ;
$_ = ($r)result;</pre></ul>

<p>If the result type is a primitive type, then <code>($r)</code>
follows special semantics.  First, if the operand type of the cast
expression is a primitive type, <code>($r)</code> works as a normal
cast operator to the result type.
On the other hand, if the operand type is a wrapper type,
<code>($r)</code> converts from the wrapper type to the result type.
For example, if the result type is <code>int</code>, then
<code>($r)</code> converts from <code>java.lang.Integer</code> to
<code>int</code>.

<p>If the result type is <code>void</code>, then
<code>($r)</code> does not convert a type; it does nothing.
However, if the operand is a call to a <code>void</code> method,
then <code>($r)</code> results in <code>null</code>.  For example,
if the result type is <code>void</code> and
<code>foo()</code> is a <code>void</code> method, then

<ul><pre>$_ = ($r)foo();</pre></ul>

<p>is a valid statement.

<p>The cast operator <code>($r)</code> is also useful in a
<code>return</code> statement.  Even if the result type is
<code>void</code>, the following <code>return</code> statement is valid:

<ul><pre>return ($r)result;</pre></ul>

<p>Here, <code>result</code> is some local variable.
Since <code>($r)</code> is specified, the resulting value is
discarded.
This <code>return</code> statement is regarded as the equivalent
of the <code>return</code> statement without a resulting value:

<ul><pre>return;</pre></ul>

<h4>$w</h4>

<p><code>$w</code> represents a wrapper type.
It must be used as the cast type in a cast expression.
<code>($w)</code> converts from a primitive type to the corresponding
wrapper type.

The following code is an example:

<ul><pre>Integer i = ($w)5;</pre></ul>

<p>The selected wrapper type depends on the type of the expression
following <code>($w)</code>.  If the type of the expression is
<code>double</code>, then the wrapper type is <code>java.lang.Double</code>.

<p>If the type of the expression following <code>($w)</code> is not
a primitive type, then <code>($w)</code> does nothing.

<h4>$_</h4>

<p><code>insertAfter()</code> in <code>CtMethod</code> and
<code>CtConstructor</code> inserts the
compiled code at the end of the method.  In the statement given to
<code>insertAfter()</code>, not only the variables shown above such as
<code>$0</code>, <code>$1</code>, ... but also <code>$_</code> is
available.

<p>The variable <code>$_</code> represents the resulting value of the
method.  The type of that variable is the type of the result type (the
return type) of the method.  If the result type is <code>void</code>,
then the type of <code>$_</code> is <code>Object</code> and the value
of <code>$_</code> is <code>null</code>.

<p>Although the compiled code inserted by <code>insertAfter()</code>
is executed just before the control normally returns from the method,
it can be also executed when an exception is thrown from the method.
To execute it when an exception is thrown, the second parameter
<code>asFinally</code> to <code>insertAfter()</code> must be
<code>true</code>.

<p>If an exception is thrown, the compiled code inserted by
<code>insertAfter()</code> is executed as a <code>finally</code>
clause.  The value of <code>$_</code> is <code>0</code> or
<code>null</code> in the compiled code.  After the execution of the
compiled code terminates, the exception originally thrown is re-thrown
to the caller.  Note that the value of <code>$_</code> is never thrown
to the caller; it is rather discarded.

<h4>$sig</h4>

<p>The value of <code>$sig</code> is an array of
<code>java.lang.Class</code> objects that represent the formal
parameter types in declaration order.

<h4>$type</h4>

<p>The value of <code>$type</code> is an <code>java.lang.Class</code>
object representing the formal type of the result value.  This
variable refers to <code>Void.class</code> if this is a constructor.

<h4>$class</h4>

<p>The value of <code>$class</code> is an <code>java.lang.Class</code>
object representing the class in which the edited method is declared.
This represents the type of <code>$0</code>.

<h4>addCatch()</h4>

<p><code>addCatch()</code> inserts a code fragment into a method body
so that the code fragment is executed when the method body throws
an exception and the control returns to the caller.  In the source
text representing the inserted code fragment, the exception value
is referred to with the special variable <code>$e</code>.

<p>For example, this program:

<ul><pre>
CtMethod m = ...;
CtClass etype = ClassPool.getDefault().get("java.io.IOException");
m.addCatch("{ System.out.println($e); throw $e; }", etype);
</pre></ul>

<p>translates the method body represented by <code>m</code> into
something like this:

<ul><pre>
try {
    <font face="serif"><em>the original method body</em></font>
}
catch (java.io.IOException e) {
    System.out.println(e);
    throw e;
}
</pre></ul>

<p>Note that the inserted code fragment must end with a
<code>throw</code> or <code>return</code> statement.

<p><br>

<a name="alter">
<h3>4.2 Altering a method body</h3>

<p><code>CtMethod</code> and <code>CtConstructor</code> provide
<code>setBody()</code> for substituting a whole
method body.  They compile the given source text into Java bytecode
and substitutes it for the original method body.  If the given source
text is <code>null</code>, the substituted body includes only a
<code>return</code> statement, which returns zero or null unless the
result type is <code>void</code>.

<p>In the source text given to <code>setBody()</code>, the identifiers
starting with <code>$</code> have special meaning

<ul><table border=0>
<tr>
<td><code>$0</code>, <code>$1</code>, <code>$2</code>, ...</td>
<td><code>this</code> and actual parameters</td>
</tr>

<tr>
<td><code>$args</code></td>
<td>An array of parameters.
The type of <code>$args</code> is <code>Object[]</code>.
</td>
</tr>

<tr>
<td><code>$$</code></td>
<td>All actual parameters.<br>
</tr>

<tr>
<td><code>$cflow(</code>...<code>)</code></td>
<td><code>cflow</code> variable</td>
</tr>

<tr>
<td><code>$r</code></td>
<td>The result type.  It is used in a cast expression.</td>
</tr>

<tr>
<td><code>$w</code></td>
<td>The wrapper type.  It is used in a cast expression.</td>
</tr>

<tr>
<td><code>$sig</code></td>
<td>An array of <code>java.lang.Class</code> objects representing
the formal parameter types.
</td>
</tr>

<tr>
<td><code>$type</code></td>
<td>A <code>java.lang.Class</code> object representing
the formal result type.</td>
</tr>

<tr>
<td><code>$class</code></td>
<td rowspan=2>A <code>java.lang.Class</code> object representing
the class that declares the method<br>
currently edited (the type of $0).</td>
</tr>

</table>
</ul>

Note that <code>$_</code> is not available.

<h4>Substituting source text for an existing expression</h4>

<p>Javassist allows modifying only an expression included in a method body.
<code>javassist.expr.ExprEditor</code> is a class
for replacing an expression in a method body.
The users can define a subclass of <code>ExprEditor</code>
to specify how an expression is modified.

<p>To run an <code>ExprEditor</code> object, the users must
call <code>instrument()</code> in <code>CtMethod</code> or
<code>CtClass</code>.

For example,

<ul><pre>
CtMethod cm = ... ;
cm.instrument(
    new ExprEditor() {
        public void edit(MethodCall m)
                      throws CannotCompileException
        {
            if (m.getClassName().equals("Point")
                          && m.getMethodName().equals("move"))
                m.replace("{ $1 = 0; $_ = $proceed($$); }");
        }
    });
</pre></ul>

<p>searches the method body represented by <code>cm</code> and
replaces all calls to <code>move()</code> in class <code>Point</code>
with a block:

<ul><pre>{ $1 = 0; $_ = $proceed($$); }
</pre></ul>

<p>so that the first parameter to <code>move()</code> is always 0.
Note that the substituted code is not an expression but
a statement or a block.  It cannot be or contain a try-catch statement.

<p>The method <code>instrument()</code> searches a method body.
If it finds an expression such as a method call, field access, and object
creation, then it calls <code>edit()</code> on the given
<code>ExprEditor</code> object.  The parameter to <code>edit()</code>
is an object representing the found expression.  The <code>edit()</code>
method can inspect and replace the expression through that object.

<p>Calling <code>replace()</code> on the parameter to <code>edit()</code>
substitutes the given statement or block for the expression.  If the given
block is an empty block, that is, if <code>replace("{}")</code>
is executed, then the expression is removed from the method body.

If you want to insert a statement (or a block) before/after the
expression, a block like the following should be passed to
<code>replace()</code>:

<ul><pre>
{ <em>before-statements;</em>
  $_ = $proceed($$);
  <em>after-statements;</em> }
</pre></ul>

<p>whichever the expression is either a method call, field access,
object creation, or others.  The second statement could be:

<ul><pre>$_ = $proceed();</pre></ul>

<p>if the expression is read access, or

<ul><pre>$proceed($$);</pre></ul>

<p>if the expression is write access.

<p>Local variables available in the target expression is
also available in the source text passed to <code>replace()</code>
if the method searched by <code>instrument()</code> was compiled
with the -g option (the class file includes a local variable
attribute).

<h4>javassist.expr.MethodCall</h4>

<p>A <code>MethodCall</code> object represents a method call.
The method <code>replace()</code> in
<code>MethodCall</code> substitutes a statement or
a block for the method call.
It receives source text representing the substitued statement or
block, in which the identifiers starting with <code>$</code>
have special meaning as in the source text passed to
<code>insertBefore()</code>.

<ul><table border=0>
<tr>
<td><code>$0</code></td>
<td rowspan=3>
The target object of the method call.<br>
This is not equivalent to <code>this</code>, which represents
the caller-side <code>this</code> object.<br>
<code>$0</code> is <code>null</code> if the method is static.
</td>
</tr>

<tr><td>&nbsp</td></tr>

<tr><td>&nbsp</td></tr>

<tr>
<td><code>$1</code>, <code>$2</code>, ... &nbsp &nbsp</td>
<td>
The parameters of the method call.
</td>
</tr>

<tr><td>
<code>$_</code></td>
<td>The resulting value of the method call.</td>
</tr>

<tr><td><code>$r</code></td>
<td>The result type of the method call.</td>
</tr>

<tr><td><code>$class</code> &nbsp &nbsp</td>
<td>A <code>java.lang.Class</code> object representing
the class declaring the method.
</td>
</tr>

<tr><td><code>$sig</code> &nbsp &nbsp</td>
<td>An array of <code>java.lang.Class</code> objects representing
the formal parameter types.</td>
</tr>

<tr><td><code>$type</code> &nbsp &nbsp</td>
<td>A <code>java.lang.Class</code> object representing
the formal result type.</td>
</tr>

<tr><td><code>$proceed</code> &nbsp &nbsp</td>
<td>The name of the method originally called
in the expression.</td>
</tr>

</table>
</ul>

<p>Here the method call means the one represented by the
<code>MethodCall</code> object.

<p>The other identifiers such as <code>$w</code>,
<code>$args</code> and <code>$$</code>
are also available.

<p>Unless the result type of the method call is <code>void</code>,
a value must be assigned to
<code>$_</code> in the source text and the type of <code>$_</code>
is the result type.
If the result type is <code>void</code>, the type of <code>$_</code>
is <code>Object</code> and the value assigned to <code>$_</code>
is ignored.

<p><code>$proceed</code> is not a <code>String</code> value but special
syntax.  It must be followed by an argument list surrounded by parentheses
<code>( )</code>.

<h4>javassist.expr.ConstructorCall</h4>

<p>A <code>ConstructorCall</code> object represents a constructor call
such as <code>this()</code> and <code>super</code> included in a constructor
body.
The method <code>replace()</code> in
<code>ConstructorCall</code> substitutes a statement or
a block for the constructor call.
It receives source text representing the substituted statement or
block, in which the identifiers starting with <code>$</code>
have special meaning as in the source text passed to
<code>insertBefore()</code>.

<ul><table border=0>
<tr>
<td><code>$0</code></td>
<td>
The target object of the constructor call.
This is equivalent to <code>this</code>.
</td>
</tr>

<tr>
<td><code>$1</code>, <code>$2</code>, ... &nbsp &nbsp</td>
<td>
The parameters of the constructor call.
</td>
</tr>

<tr><td><code>$class</code> &nbsp &nbsp</td>
<td>A <code>java.lang.Class</code> object representing
the class declaring the constructor.
</td>
</tr>

<tr><td><code>$sig</code> &nbsp &nbsp</td>
<td>An array of <code>java.lang.Class</code> objects representing
the formal parameter types.</td>
</tr>

<tr><td><code>$proceed</code> &nbsp &nbsp</td>
<td>The name of the constructor originally called
in the expression.</td>
</tr>

</table>
</ul>

<p>Here the constructor call means the one represented by the
<code>ConstructorCall</code> object.

<p>The other identifiers such as <code>$w</code>,
<code>$args</code> and <code>$$</code>
are also available.

<p>Since any constructor must call either a constructor of the super
class or another constructor of the same class,
the substituted statement must include a constructor call,
normally a call to <code>$proceed()</code>.

<p><code>$proceed</code> is not a <code>String</code> value but special
syntax.  It must be followed by an argument list surrounded by parentheses
<code>( )</code>.

<h4>javassist.expr.FieldAccess</h4>

<p>A <code>FieldAccess</code> object represents field access.
The method <code>edit()</code> in <code>ExprEditor</code>
receives this object if field access is found.
The method <code>replace()</code> in
<code>FieldAccess</code> receives
source text representing the substitued statement or
block for the field access.

<p>
In the source text, the identifiers starting with <code>$</code>
have special meaning:

<ul><table border=0>
<tr>
<td><code>$0</code></td>
<td rowspan=3>
The object containing the field accessed by the expression.
This is not equivalent to <code>this</code>.<br>
<code>this</code> represents the object that the method including the
expression is invoked on.<br>
<code>$0</code> is <code>null</code> if the field is static.
</td>
</tr>

<tr><td>&nbsp</td></tr>

<tr><td>&nbsp</td></tr>

<tr>
<td><code>$1</code></td>
<td rowspan=2>
The value that would be stored in the field
if the expression is write access.
<br>Otherwise, <code>$1</code> is not available.
</td>
</tr>

<tr><td>&nbsp</td></tr>

<tr>
<td><code>$_</code></td>
<td rowspan=2>
The resulting value of the field access
if the expression is read access.
<br>Otherwise, the value stored in <code>$_</code> is discarded.
</td>
</tr>

<tr><td>&nbsp</td></tr>
<tr>
<td><code>$r</code></td>
<td rowspan=2>
The type of the field if the expression is read access.
<br>Otherwise, <code>$r</code> is <code>void</code>.
</td>
</tr>

<tr><td>&nbsp</td></tr>

<tr><td><code>$class</code> &nbsp &nbsp</td>
<td>A <code>java.lang.Class</code> object representing
the class declaring the field.
</td></tr>

<tr><td><code>$type</code></td>
<td>A <code>java.lang.Class</code> object representing
the field type.</td>
</tr>

<tr><td><code>$proceed</code> &nbsp &nbsp</td>
<td>The name of a virtual method executing the original
field access.
.</td>
</tr>

</table>
</ul>

<p>The other identifiers such as <code>$w</code>,
<code>$args</code> and <code>$$</code>
are also available.

<p>If the expression is read access, a value must be assigned to
<code>$_</code> in the source text.  The type of <code>$_</code>
is the type of the field.

<h4>javassist.expr.NewExpr</h4>

<p>A <code>NewExpr</code> object represents object creation
with the <code>new</code> operator (not including array creation).
The method <code>edit()</code> in <code>ExprEditor</code>
receives this object if object creation is found.
The method <code>replace()</code> in
<code>NewExpr</code> receives
source text representing the substitued statement or
block for the object creation.

<p>
In the source text, the identifiers starting with <code>$</code>
have special meaning:

<ul><table border=0>

<tr>
<td><code>$0</code></td>
<td>
<code>null</code>.
</td>
</tr>

<tr>
<td><code>$1</code>, <code>$2</code>, ... &nbsp &nbsp</td>
<td>
The parameters to the constructor.
</td>
</tr>

<tr>
<td><code>$_</code></td>
<td rowspan=2>
The resulting value of the object creation.
<br>A newly created object must be stored in this variable.
</td>
</tr>

<tr><td>&nbsp</td></tr>

<tr>
<td><code>$r</code></td>
<td>
The type of the created object.
</td>
</tr>

<tr><td><code>$sig</code> &nbsp &nbsp</td>
<td>An array of <code>java.lang.Class</code> objects representing
the formal parameter types.</td>
</tr>

<tr><td><code>$type</code> &nbsp &nbsp</td>
<td>A <code>java.lang.Class</code> object representing
the class of the created object.
</td></tr>

<tr><td><code>$proceed</code> &nbsp &nbsp</td>
<td>The name of a virtual method executing the original
object creation.
.</td>
</tr>

</table>
</ul>

<p>The other identifiers such as <code>$w</code>,
<code>$args</code> and <code>$$</code>
are also available.

<h4>javassist.expr.NewArray</h4>

<p>A <code>NewArray</code> object represents array creation
with the <code>new</code> operator.
The method <code>edit()</code> in <code>ExprEditor</code>
receives this object if array creation is found.
The method <code>replace()</code> in
<code>NewArray</code> receives
source text representing the substitued statement or
block for the array creation.

<p>
In the source text, the identifiers starting with <code>$</code>
have special meaning:

<ul><table border=0>

<tr>
<td><code>$0</code></td>
<td>
<code>null</code>.
</td>
</tr>

<tr>
<td><code>$1</code>, <code>$2</code>, ... &nbsp &nbsp</td>
<td>
The size of each dimension.
</td>
</tr>

<tr>
<td><code>$_</code></td>
<td rowspan=2>
The resulting value of the array creation.
<br>A newly created array must be stored in this variable.
</td>
</tr>

<tr><td>&nbsp</td></tr>

<tr>
<td><code>$r</code></td>
<td>
The type of the created array.
</td>
</tr>

<tr><td><code>$type</code> &nbsp &nbsp</td>
<td>A <code>java.lang.Class</code> object representing
the class of the created array.
</td></tr>

<tr><td><code>$proceed</code> &nbsp &nbsp</td>
<td>The name of a virtual method executing the original
array creation.
.</td>
</tr>

</table>
</ul>

<p>The other identifiers such as <code>$w</code>,
<code>$args</code> and <code>$$</code>
are also available.

<p>For example, if the array creation is the following expression,

<ul><pre>
String[][] s = new String[3][4];
</pre></ul>

then the value of $1 and $2 are 3 and 4, respectively.  $3 is not available.

<p>If the array creation is the following expression,

<ul><pre>
String[][] s = new String[3][];
</pre></ul>

then the value of $1 is 3 but $2 is not available.

<h4>javassist.expr.Instanceof</h4>

<p>A <code>Instanceof</code> object represents an <code>instanceof</code>
expression.
The method <code>edit()</code> in <code>ExprEditor</code>
receives this object if an instanceof expression is found.
The method <code>replace()</code> in
<code>Instanceof</code> receives
source text representing the substitued statement or
block for the expression.

<p>
In the source text, the identifiers starting with <code>$</code>
have special meaning:

<ul><table border=0>

<tr>
<td><code>$0</code></td>
<td>
<code>null</code>.
</td>
</tr>

<tr>
<td><code>$1</code></td>
<td>
The value on the left hand side of the original
<code>instanceof</code> operator.
</td>
</tr>

<tr>
<td><code>$_</code></td>
<td>
The resulting value of the expression.
The type of <code>$_</code> is <code>boolean</code>.
</td>
</tr>

<tr>
<td><code>$r</code></td>
<td>
The type on the right hand side of the <code>instanceof</code> operator.
</td>
</tr>

<tr><td><code>$type</code></td>
<td>A <code>java.lang.Class</code> object representing
the type on the right hand side of the <code>instanceof</code> operator.
</td>
</tr>

<tr><td><code>$proceed</code> &nbsp &nbsp</td>
<td rowspan=4>The name of a virtual method executing the original
<code>instanceof</code> expression.
<br>It takes one parameter (the type is <code>java.lang.Object</code>)
and returns true
<br>if the parameter value is an instance of the type on the right
hand side of
<br>the original <code>instanceof</code> operator.
Otherwise, it returns false.
</td>
</tr>

<tr><td>&nbsp</td></tr>
<tr><td>&nbsp</td></tr>
<tr><td>&nbsp</td></tr>

</table>
</ul>

<p>The other identifiers such as <code>$w</code>,
<code>$args</code> and <code>$$</code>
are also available.

<h4>javassist.expr.Cast</h4>

<p>A <code>Cast</code> object represents an expression for
explicit type casting.
The method <code>edit()</code> in <code>ExprEditor</code>
receives this object if explicit type casting is found.
The method <code>replace()</code> in
<code>Cast</code> receives
source text representing the substitued statement or
block for the expression.

<p>
In the source text, the identifiers starting with <code>$</code>
have special meaning:

<ul><table border=0>

<tr>
<td><code>$0</code></td>
<td>
<code>null</code>.
</td>
</tr>

<tr>
<td><code>$1</code></td>
<td>
The value the type of which is explicitly cast.
</td>
</tr>

<tr>
<td><code>$_</code></td>
<td rowspan=2>
The resulting value of the expression.
The type of <code>$_</code> is the same as the type
<br>after the explicit casting, that is, the type surrounded
by <code>( )</code>.
</td>
</tr>

<tr><td>&nbsp</td></tr>

<tr>
<td><code>$r</code></td>
<td>the type after the explicit casting, or the type surrounded
by <code>( )</code>.
</td>
</tr>

<tr><td><code>$type</code></td>
<td>A <code>java.lang.Class</code> object representing
the same type as <code>$r</code>.
</td>
</tr>

<tr><td><code>$proceed</code> &nbsp &nbsp</td>
<td rowspan=3>The name of a virtual method executing the original
type casting.
<br>It takes one parameter of the type <code>java.lang.Object</code>
and returns it after
<br>the explicit type casting specified by the original expression.

</td>
</tr>

<tr><td>&nbsp</td></tr>

<tr><td>&nbsp</td></tr>

</table>
</ul>

<p>The other identifiers such as <code>$w</code>,
<code>$args</code> and <code>$$</code>
are also available.

<h4>javassist.expr.Handler</h4>

<p>A <code>Handler</code> object represents a <code>catch</code>
clause of <code>try-catch</code> statement.
The method <code>edit()</code> in <code>ExprEditor</code>
receives this object if a <code>catch</code> is found.
The method <code>insertBefore()</code> in
<code>Handler</code> compiles the received
source text and inserts it at the beginning of the <code>catch</code> clause.

<p>
In the source text, the identifiers starting with <code>$</code>
have meaning:

<ul><table border=0>

<tr>
<td><code>$1</code></td>
<td>
The exception object caught by the <code>catch</code> clause.
</td>
</tr>

<tr>
<td><code>$r</code></td>
<td>the type of the exception caught by the <code>catch</code> clause.
It is used in a cast expression.
</td>
</tr>

<tr>
<td><code>$w</code></td>
<td>The wrapper type.  It is used in a cast expression.
</td>
</tr>

<tr><td><code>$type</code> &nbsp &nbsp</td>
<td rowspan=2>
A <code>java.lang.Class</code> object representing
<br>the type of the exception caught by the <code>catch</code> clause.
</td>
</tr>

<tr><td>&nbsp</td></tr>

</table>
</ul>

<p>If a new exception object is assigned to <code>$1</code>,
it is passed to the original <code>catch</code> clause as the caught
exception.

<p><br>

<a name="add">
<h3>4.3 Adding a new method or field</h3>

<h4>Adding a method</h4>

<p>Javassist allows the users to create a new method and constructor
from scratch.  <code>CtNewMethod</code>
and <code>CtNewConstructor</code> provide several factory methods,
which are static methods for creating <code>CtMethod</code> or
<code>CtConstructor</code> objects.
Especially, <code>make()</code> creates 
a <code>CtMethod</code> or <code>CtConstructor</code> object
from the given source text.

<p>For example, this program:

<ul><pre>
CtClass point = ClassPool.getDefault().get("Point");
CtMethod m = CtNewMethod.make(
                 "public int xmove(int dx) { x += dx; }",
                 point);
point.addMethod(m);
</pre></ul>

<p>adds a public method <code>xmove()</code> to class <code>Point</code>.
In this example, <code>x</code> is a <code>int</code> field in
the class <code>Point</code>.

<p>The source text passed to <code>make()</code> can include the
identifiers starting with <code>$</code> except <code>$_</code>
as in <code>setBody()</code>.
It can also include
<code>$proceed</code> if the target object and the target method name
are also given to <code>make()</code>.  For example,

<ul><pre>
CtClass point = ClassPool.getDefault().get("Point");
CtMethod m = CtNewMethod.make(
                 "public int ymove(int dy) { $proceed(0, dy); }",
                 point, "this", "move");
</pre></ul>

<p>this program creates a method <code>ymove()</code> defined below:

<ul><pre>
public int ymove(int dy) { this.move(0, dy); }
</pre></ul>

<p>Note that <code>$proceed</code> has been replaced with
<code>this.move</code>.

<p>Javassist provides another way to add a new method.
You can first create an abstract method and later give it a method body:

<ul><pre>
CtClass cc = ... ;
CtMethod m = new CtMethod(CtClass.intType, "move",
                          new CtClass[] { CtClass.intType }, cc);
cc.addMethod(m);
m.setBody("{ x += $1; }");
cc.setModifiers(cc.getModifiers() & ~Modifier.ABSTRACT);
</pre></ul>

<p>Since Javassist makes a class abstract if an abstract method is
added to the class, you have to explicitly change the class back to a
non-abstract one after calling <code>setBody()</code>.


<h4>Mutual recursive methods</h4>

<p>Javassist cannot compile a method if it calls another method that
has not been added to a class.  (Javassist can compile a method that
calls itself recursively.)  To add mutual recursive methods to a class,
you need a trick shown below.  Suppose that you want to add methods
<code>m()</code> and <code>n()</code> to a class represented
by <code>cc</code>:

<ul><pre>
CtClass cc = ... ;
CtMethod m = CtNewMethod.make("public abstract int m(int i);", cc);
CtMethod n = CtNewMethod.make("public abstract int n(int i);", cc);
cc.addMethod(m);
cc.addMethod(n);
m.setBody("{ return ($1 <= 0) ? 1 : (n($1 - 1) * $1); }");
n.setBody("{ return m($1); }");
cc.setModifiers(cc.getModifiers() & ~Modifier.ABSTRACT);
</pre></ul>

<p>You must first make two abstract methods and add them to the class.
Then you can give the method bodies to these methods even if the method
bodies include method calls to each other.  Finally you must change the
class to a not-abstract class since <code>addMethod()</code> automatically
changes a class into an abstract one if an abstract method is added.

<h4>Adding a field</h4>

<p>Javassist also allows the users to create a new field.

<ul><pre>
CtClass point = ClassPool.getDefault().get("Point");
CtField f = new CtField(CtClass.intType, "z", point);
point.addField(f);
</pre></ul>

<p>This program adds a field named <code>z</code> to class
<code>Point</code>.

<p>If the initial value of the added field must be specified,
the program shown above must be modified into:

<ul><pre>
CtClass point = ClassPool.getDefault().get("Point");
CtField f = new CtField(CtClass.intType, "z", point);
point.addField(f, "0");    <em>// initial value is 0.</em>
</pre></ul>

<p>Now, the method <code>addField()</code> receives the second parameter,
which is the source text representing an expression computing the initial
value.  This source text can be any Java expression if the result type
of the expression matches the type of the field.  Note that an expression
does not end with a semi colon (<code>;</code>).

<p>Furthermore, the above code can be rewritten into the following
simple code:

<ul><pre>
CtClass point = ClassPool.getDefault().get("Point");
CtField f = CtField.make("public int z = 0;", point);
point.addField(f);
</pre></ul>

<h4>Removing a member</h4>

<p>To remove a field or a method, call <code>removeField()</code>
or <code>removeMethod()</code> in <code>CtClass</code>.  A
<code>CtConstructor</code> can be removed by <code>removeConstructor()</code>
in <code>CtClass</code>.

<p><br>

<a name="annotation">
<h3>4.4 Annotations</h3>

<p><code>CtClass</code>, <code>CtMethod</code>, <code>CtField</code>
and <code>CtConstructor</code> provides a convenient method
<code>getAnnotations()</code> for reading annotations.
It returns an annotation-type object.

<p>For example, suppose the following annotation:

<ul><pre>
public @interface Author {
    String name();
    int year();
}
</pre></ul>

<p>This annotation is used as the following:

<ul><pre>
@Author(name="Chiba", year=2005)
public class Point {
    int x, y;
}
</pre></ul>

<p>Then, the value of the annotation can be obtained by
<code>getAnnotations()</code>.
It returns an array containing
annotation-type objects.

<ul><pre>
CtClass cc = ClassPool.getDefault().get("Point");
Object[] all = cc.getAnnotations();
Author a = (Author)all[0];
String name = a.name();
int year = a.year();
System.out.println("name: " + name + ", year: " + year);
</pre></ul>

<p>This code snippet should print:

<ul><pre>
name: Chiba, year: 2005
</pre></ul>

<p>
Since the annoation of <code>Point</code> is only <code>@Author</code>,
the length of the array <code>all</code> is one
and <code>all[0]</code> is an <code>Author</code> object.
The member values of the annotation can be obtained
by calling <code>name()</code> and <code>year()</code>
on the <code>Author</code> object.

<p>To use <code>getAnnotations()</code>, annotation types
such as <code>Author</code> must be included in the current
class path.  <em>They must be also accessible from a
<code>ClassPool</code> object.</em>  If the class file of an annotation
type is not found, Javassist cannot obtain the default values
of the members of that annotation type.

<p><br>

<a name="runtime">
<h3>4.5 Runtime support classes</h3>

<p>In most cases, a class modified by Javassist does not require
Javassist to run.  However, some kinds of bytecode generated by the
Javassist compiler need runtime support classes, which are in the
<code>javassist.runtime</code> package (for details, please read
the API reference of that package).  Note that the
<code>javassist.runtime</code> package is the only package that
classes modified by Javassist may need for running.  The other
Javassist classes are never used at runtime of the modified classes.

<p><br>

<a name="import">
<h3>4.6 Import</h3>

<p>All the class names in source code must be fully qualified
(they must include package names).
However, the <code>java.lang</code> package is an
exception; for example, the Javassist compiler can
resolve <code>Object</code> as
well as <code>java.lang.Object</code>.

<p>To tell the compiler to search other packages when resolving a
class name, call <code>importPackage()</code> in <code>ClassPool</code>.
For example,

<ul><pre>
ClassPool pool = ClassPool.getDefault();
pool.importPackage("java.awt");
CtClass cc = pool.makeClass("Test");
CtField f = CtField.make("public Point p;", cc);
cc.addField(f);
</pre></ul>

<p>The seconde line instructs the compiler
to import the <code>java.awt</code> package.
Thus, the third line will not throw an exception.
The compiler can recognize <code>Point</code>
as <code>java.awt.Point</code>.

<p>Note that <code>importPackage()</code> <em>does not</em> affect
the <code>get()</code> method in <code>ClassPool</code>.
Only the compiler considers the imported packages.
The parameter to <code>get()</code>
must be always a fully qualified name.

<p><br>

<a name="limit">
<h3>4.7 Limitations</h3>

<p>In the current implementation, the Java compiler included in Javassist
has several limitations with respect to the language that the compiler can
accept.  Those limitations are:

<p><li>The new syntax introduced by J2SE 5.0 (including enums and generics)
has not been supported.  Annotations are supported by the low level
API of Javassist.
See the <code>javassist.bytecode.annotation</code> package
(and also <code>getAnnotations()</code>
in <code>CtClass</code> and <code>CtBehavior</code>).
Generics are also only partly supported.  See <a href="./tutorial3.html#generics">the latter section</a> for more details.

<p><li>Array initializers, a comma-separated list of expressions
enclosed by braces <code>{</code> and <code>}</code>, are not
available unless the array dimension is one.

<p><li>Inner classes or anonymous classes are not supported.
Note that this is a limitation of the compiler only.
It cannot compile source code including an anonymous-class declaration. 
Javassist can read and modify a class file of inner/anonymous class.

<p><li>Labeled <code>continue</code> and <code>break</code> statements
are not supported.

<p><li>The compiler does not correctly implement the Java method dispatch
algorithm.  The compiler may confuse if methods defined in a class
have the same name but take different parameter lists.

<p>For example,

<ul><pre>
class A {} 
class B extends A {} 
class C extends B {} 

class X { 
    void foo(A a) { .. } 
    void foo(B b) { .. } 
}
</pre></ul>

<p>If the compiled expression is <code>x.foo(new C())</code>, where
<code>x</code> is an instance of X, the compiler may produce a call
to <code>foo(A)</code> although the compiler can correctly compile
<code>foo((B)new C())</code>.

<p><li>The users are recommended to use <code>#</code> as the separator
between a class name and a static method or field name.
For example, in regular Java,

<ul><pre>javassist.CtClass.intType.getName()</pre></ul>

<p>calls a method <code>getName()</code> on
the object indicated by the static field <code>intType</code>
in <code>javassist.CtClass</code>.  In Javassist, the users can
write the expression shown above but they are recommended to
write:

<ul><pre>javassist.CtClass#intType.getName()</pre></ul>

<p>so that the compiler can quickly parse the expression.
</ul>

<p><br>