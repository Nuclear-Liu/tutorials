# Overview

# Best Practices


## Annotation

### Configuration keys


```lombok.config
lombok.val.flagUsage = ERROR
lombok.var.flagUsage = ERROR # JDK Support
lombok.nonNull.flagUsage = ERROR
lombok.cleanup.flagUsage = ERROR # use try-with-rsource
```

### [`@Getter` / `@Setter`](./features/05%20GetterSetter.md)


```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Put on any field to make lombok build a standard getter.
 * <p>
 * Complete documentation is found at <a href="https://projectlombok.org/features/GetterSetter">the project lombok features page for &#64;Getter and &#64;Setter</a>.
 * <p>
 * Even though it is not listed, this annotation also has the {@code onMethod} parameter. See the full documentation for more details.
 * <p>
 * Example:
 * <pre>
 *     private &#64;Getter int foo;
 * </pre>
 * 
 * will generate:
 * 
 * <pre>
 *     public int getFoo() {
 *         return this.foo;
 *     }
 * </pre>
 * <p>
 * This annotation can also be applied to a class, in which case it'll be as if all non-static fields that don't already have
 * a {@code @Getter} annotation have the annotation.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Getter {
	/**
	 * If you want your getter to be non-public, you can specify an alternate access level here.
	 * 
	 * @return The getter method will be generated with this access modifier.
	 */
	lombok.AccessLevel value() default lombok.AccessLevel.PUBLIC;
	
	/**
	 * Any annotations listed here are put on the generated method.
	 * The syntax for this feature depends on JDK version (nothing we can do about that; it's to work around javac bugs).<br>
	 * up to JDK7:<br>
	 *  {@code @Getter(onMethod=@__({@AnnotationsGoHere}))}<br>
	 * from JDK8:<br>
	 *  {@code @Getter(onMethod_={@AnnotationsGohere})} // note the underscore after {@code onMethod}.
	 *  
	 * @return List of annotations to apply to the generated getter method.
	 */
	AnyAnnotation[] onMethod() default {};
	
	boolean lazy() default false;
	
	/**
	 * Placeholder annotation to enable the placement of annotations on the generated code.
	 * @deprecated Don't use this annotation, ever - Read the documentation.
	 */
	@Deprecated
	@Retention(RetentionPolicy.SOURCE)
	@Target({})
	@interface AnyAnnotation {}
}
```

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Put on any field to make lombok build a standard setter.
 * <p>
 * Complete documentation is found at <a href="https://projectlombok.org/features/GetterSetter">the project lombok features page for &#64;Getter and &#64;Setter</a>.
 * <p>
 * Even though it is not listed, this annotation also has the {@code onParam} and {@code onMethod} parameter. See the full documentation for more details.
 * <p>
 * Example:
 * <pre>
 *     private &#64;Setter int foo;
 * </pre>
 * 
 * will generate:
 * 
 * <pre>
 *     public void setFoo(int foo) {
 *         this.foo = foo;
 *     }
 * </pre>
 * 
 * <p>
 * This annotation can also be applied to a class, in which case it'll be as if all non-static fields that don't already have
 * a {@code Setter} annotation have the annotation.
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Setter {
	/**
	 * If you want your setter to be non-public, you can specify an alternate access level here.
	 * 
	 * @return The setter method will be generated with this access modifier.
	 */
	lombok.AccessLevel value() default lombok.AccessLevel.PUBLIC;
	
	/**
	 * Any annotations listed here are put on the generated method.
	 * The syntax for this feature depends on JDK version (nothing we can do about that; it's to work around javac bugs).<br>
	 * up to JDK7:<br>
	 *  {@code @Setter(onMethod=@__({@AnnotationsGoHere}))}<br>
	 * from JDK8:<br>
	 *  {@code @Setter(onMethod_={@AnnotationsGohere})} // note the underscore after {@code onMethod}.
	 *  
	 * @return List of annotations to apply to the generated setter method.
	 */
	AnyAnnotation[] onMethod() default {};
	
	/**
	 * Any annotations listed here are put on the generated method's parameter.
	 * The syntax for this feature depends on JDK version (nothing we can do about that; it's to work around javac bugs).<br>
	 * up to JDK7:<br>
	 *  {@code @Setter(onParam=@__({@AnnotationsGoHere}))}<br>
	 * from JDK8:<br>
	 *  {@code @Setter(onParam_={@AnnotationsGohere})} // note the underscore after {@code onParam}.
	 *  
	 * @return List of annotations to apply to the generated parameter in the setter method.
	 */
	AnyAnnotation[] onParam() default {};
	
	/**
	  * Placeholder annotation to enable the placement of annotations on the generated code.
	  * @deprecated Don't use this annotation, ever - Read the documentation.
	  */
	@Deprecated
	@Retention(RetentionPolicy.SOURCE)
	@Target({})
	@interface AnyAnnotation {}
}
```

```java
/**
 * Represents an AccessLevel. Used e.g. to specify the access level for generated methods and fields.
 */
public enum AccessLevel {
	PUBLIC, MODULE, PROTECTED, PACKAGE, PRIVATE,
	/** Represents not generating anything or the complete lack of a method. */
	NONE;
}
```


### [`@ToString`](./features/06%20ToString.md)


```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates an implementation for the {@code toString} method inherited by all objects, consisting of printing the values of relevant fields.
 * <p>
 * Complete documentation is found at <a href="https://projectlombok.org/features/ToString">the project lombok features page for &#64;ToString</a>.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ToString {
	/**
	 * Include the name of each field when printing it.
	 * <strong>default: true</strong>
	 * 
	 * @return Whether or not to include the names of fields in the string produced by the generated {@code toString()}.
	 */
	boolean includeFieldNames() default true;
	
	/**
	 * Any fields listed here will not be printed in the generated {@code toString} implementation.
	 * Mutually exclusive with {@link #of()}.
	 * <p>
	 * Will soon be marked {@code @Deprecated}; use the {@code @ToString.Exclude} annotation instead.
	 * 
	 * @return A list of fields to exclude.
	 */
	String[] exclude() default {};
	
	/**
	 * If present, explicitly lists the fields that are to be printed.
	 * Normally, all non-static fields are printed.
	 * <p>
	 * Mutually exclusive with {@link #exclude()}.
	 * <p>
	 * Will soon be marked {@code @Deprecated}; use the {@code @ToString.Include} annotation together with {@code @ToString(onlyExplicitlyIncluded = true)}.
	 * 
	 * @return A list of fields to use (<em>default</em>: all of them).
	 */
	String[] of() default {};
	
	/**
	 * Include the result of the superclass's implementation of {@code toString} in the output.
	 * <strong>default: false</strong>
	 * 
	 * @return Whether to call the superclass's {@code toString} implementation as part of the generated toString algorithm.
	 */
	boolean callSuper() default false;
	
	/**
	 * Normally, if getters are available, those are called. To suppress this and let the generated code use the fields directly, set this to {@code true}.
	 * <strong>default: false</strong>
	 * 
	 * @return If {@code true}, always use direct field access instead of calling the getter method.
	 */
	boolean doNotUseGetters() default false;
	
	/**
	 * Only include fields and methods explicitly marked with {@code @ToString.Include}.
	 * Normally, all (non-static) fields are included by default.
	 * 
	 * @return If {@code true}, don't include non-static fields automatically (default: {@code false}).
	 */
	boolean onlyExplicitlyIncluded() default false;
	
	/**
	 * If present, do not include this field in the generated {@code toString}.
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.SOURCE)
	public @interface Exclude {}
	
	/**
	 * Configure the behaviour of how this member is rendered in the {@code toString}; if on a method, include the method's return value in the output.
	 */
	@Target({ElementType.FIELD, ElementType.METHOD})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Include {
//		/** If true and the return value is {@code null}, omit this member entirely from the {@code toString} output. */
//		boolean skipNull() default false; // -- We'll add it later, it requires a complete rework on the toString code we generate.
		
		/**
		 * Higher ranks are printed first. Members of the same rank are printed in the order they appear in the source file.
		 * 
		 * @return ordering within the generating {@code toString()}; higher numbers are printed first.
		 */
		int rank() default 0;
		
		/**
		 * Defaults to the field / method name of the annotated member.
		 * If the name equals the name of a default-included field, this member takes its place.
		 * 
		 * @return The name to show in the generated {@code toString()}. Also, if this annotation is on a method and the name matches an existing field, it replaces that field.
		 */
		String name() default "";
	}
}

```


### [`@EqualsAndHashCode`](./features/07%20EqualsAndHashCode.md)


```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates implementations for the {@code equals} and {@code hashCode} methods inherited by all objects, based on relevant fields.
 * <p>
 * Complete documentation is found at <a href="https://projectlombok.org/features/EqualsAndHashCode">the project lombok features page for &#64;EqualsAndHashCode</a>.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EqualsAndHashCode {
	/**
	 * Any fields listed here will not be taken into account in the generated {@code equals} and {@code hashCode} implementations.
	 * Mutually exclusive with {@link #of()}.
	 * <p>
	 * Will soon be marked {@code @Deprecated}; use the {@code @EqualsAndHashCode.Exclude} annotation instead.
	 * 
	 * @return A list of fields to exclude.
	 */
	String[] exclude() default {};
	
	/**
	 * If present, explicitly lists the fields that are to be used for identity.
	 * Normally, all non-static, non-transient fields are used for identity.
	 * <p>
	 * Mutually exclusive with {@link #exclude()}.
	 * <p>
	 * Will soon be marked {@code @Deprecated}; use the {@code @EqualsAndHashCode.Include} annotation together with {@code @EqualsAndHashCode(onlyExplicitlyIncluded = true)}.
	 * 
	 * @return A list of fields to use (<em>default</em>: all of them).
	 */
	String[] of() default {};
	
	/**
	 * Call on the superclass's implementations of {@code equals} and {@code hashCode} before calculating for the fields in this class.
	 * <strong>default: false</strong>
	 * 
	 * @return Whether to call the superclass's {@code equals} implementation as part of the generated equals algorithm.
	 */
	boolean callSuper() default false;
	
	/**
	 * Normally, if getters are available, those are called. To suppress this and let the generated code use the fields directly, set this to {@code true}.
	 * <strong>default: false</strong>
	 * 
	 * @return If {@code true}, always use direct field access instead of calling the getter method.
	 */
	boolean doNotUseGetters() default false;

	/**
	 * Determines how the result of the {@code hashCode} method will be cached.
	 * <strong>default: {@link CacheStrategy#NEVER}</strong>
	 *
	 * @return The {@code hashCode} cache strategy to be used.
	 */
	CacheStrategy cacheStrategy() default CacheStrategy.NEVER;
	
	/**
	 * Any annotations listed here are put on the generated parameter of {@code equals} and {@code canEqual}.
	 * This is useful to add for example a {@code Nullable} annotation.<br>
	 * The syntax for this feature depends on JDK version (nothing we can do about that; it's to work around javac bugs).<br>
	 * up to JDK7:<br>
	 *  {@code @EqualsAndHashCode(onParam=@__({@AnnotationsGoHere}))}<br>
	 * from JDK8:<br>
	 *  {@code @EqualsAndHashCode(onParam_={@AnnotationsGohere})} // note the underscore after {@code onParam}.
	 *  
	 * @return List of annotations to apply to the generated parameter in the {@code equals()} method.
	 */
	AnyAnnotation[] onParam() default {};
	
	/**
	 * Placeholder annotation to enable the placement of annotations on the generated code.
	 * @deprecated Don't use this annotation, ever - Read the documentation.
	 */
	@Deprecated
	@Retention(RetentionPolicy.SOURCE)
	@Target({})
	@interface AnyAnnotation {}
	
	/**
	 * Only include fields and methods explicitly marked with {@code @EqualsAndHashCode.Include}.
	 * Normally, all (non-static, non-transient) fields are included by default.
	 * 
	 * @return If {@code true}, don't include non-static non-transient fields automatically (default: {@code false}).
	 */
	boolean onlyExplicitlyIncluded() default false;
	
	/**
	 * If present, do not include this field in the generated {@code equals} and {@code hashCode} methods.
	 */
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.SOURCE)
	public @interface Exclude {}
	
	/**
	 * Configure the behaviour of how this member is treated in the {@code equals} and {@code hashCode} implementation; if on a method, include the method's return value as part of calculating hashCode/equality.
	 */
	@Target({ElementType.FIELD, ElementType.METHOD})
	@Retention(RetentionPolicy.SOURCE)
	public @interface Include {
		/**
		 * Defaults to the method name of the annotated member.
		 * If on a method and the name equals the name of a default-included field, this member takes its place.
		 * 
		 * @return If present, this method serves as replacement for the named field.
		 */
		String replaces() default "";

		/**
		 * Higher ranks are considered first. Members of the same rank are considered in the order they appear in the source file.
		 * 
		 * If not explicitly set, the {@code default} rank for primitives is 1000, and for primitive wrappers 800.
		 * 
		 * @return ordering within the generating {@code equals} and {@code hashCode} methods; higher numbers are considered first.
		 */
		int rank() default 0;
	}

	public enum CacheStrategy {
		/**
		 * Never cache. Perform the calculation every time the method is called.
		 */
		NEVER,
		/**
		 * Cache the result of the first invocation of {@code hashCode} and use it for subsequent invocations.
		 * This can improve performance if all fields used for calculating the {@code hashCode} are immutable
		 * and thus every invocation of {@code hashCode} will always return the same value.
		 * <strong>Do not use this if there's <em>any</em> chance that different invocations of {@code hashCode}
		 * might return different values.</strong>
		 */
		LAZY
	}
}

```

### [`@NoArgsConstructor`, `@RequiredArgsConstructor`, `@AllArgsConstructor`](./features/08%20Constructor.md)


### [`@Builder`](./features/11%20Builder.md)


### [``]()
