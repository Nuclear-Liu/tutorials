## 2. Writing Tests

The following example provides a glimpse at the minimum requirements for writing a test in JUnit Jupiter. 
Subsequent sections of this chapter will provide further details on all available features.

A first test case

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import example.util.Calculator;

import org.junit.jupiter.api.Test;

class MyFirstJUnitJupiterTests {

    private final Calculator calculator = new Calculator();

    @Test
    void addition() {
        assertEquals(2, calculator.add(1, 1));
    }

}

```

### 2.1. Annotations

JUnit Jupiter supports the following annotations for configuring tests and extending the framework.

Unless otherwise stated, all core annotations are located in the `org.junit.jupiter.api` package in the `junit-jupiter-api` module.

| Annotation | Description |
| ---- | ---- |
| `@Test` |  |
| `@ParameterizedTest` |  |
| `@RepeatedTest` |  |
| `@TestFactory` |  |
| `@TestTemplate` |  |
| `@TestClassOrder` |  |
| `@TestMethodOrder` |  |
| `@TestInstance` |  |
| `@DisplayName` |  |
| `@DisplayNameGeneration` |  |
| `@BeforeEach` |  |
| `@AfterEach` |  |
| `@BeforeAll` |  |
| `@AfterAll` |  |
| `@Nested` |  |
| `@Tag` |  |
| `@Disabled` |  |
| `@Timeout` |  |
| `@ExtendWith` |  |
| `@RegisterExtension` |  |
| `@TempDir` |  |

> Some annotations may currently be experimental. 
> Consult the table in Experimental APIs for details. 

#### 2.1.1. Meta-Annotations and Composed Annotations

JUnit Jupiter annotations can be used as meta-annotations. 
That means that you can define your own composed annotation that will automatically inherit the semantics of its meta-annotations.

For example, instead of copying and pasting `@Tag("fast")` throughout your code base (see Tagging and Filtering), you can create a custom composed annotation named `@Fast` as follows. 
`@Fast` can then be used as a drop-in replacement for `@Tag("fast")`.

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Tag("fast")
public @interface Fast {
}

```

The following @Test method demonstrates usage of the @Fast annotation.

```text
@Fast
@Test
void myFastTest() {
    // ...
}

```

You can even take that one step further by introducing a custom `@FastTest` annotation that can be used as a drop-in replacement for `@Tag("fast")` and `@Test`.

```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag("fast")
@Test
public @interface FastTest {
}

```

JUnit automatically recognizes the following as a `@Test` method that is tagged with "fast".

```text
@FastTest
void myFastTest() {
    // ...
}

```

### 2.2. Test Classes and Methods

### 2.3. Display Names

#### 2.3.1. Display Name Generators

#### 2.3.2. Setting the Default Display Name Generator

### 2.4. Assertions

#### 2.4.1. Kotlin Assertion Support

#### 2.4.2. Third-party Assertion Libraries

### 2.5. Assumptions

### 2.6. Disabling Tests

### 2.7. Conditional Test Execution
