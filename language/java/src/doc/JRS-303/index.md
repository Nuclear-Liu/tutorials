# JSR-303 Bean Validation

在需要校验的实体类上加上 `@Valid` 注解。


## Bean Validation

依赖信息：
```xml
<dependency>
    <groupId>jakarta.validation</groupId>
    <artifactId>jakarta.validation-api</artifactId>
    <version>3.0.2</version>
</dependency>
```

| Constraint     | Description |
|----------------|--|
| `@Null`        | 注解的元素必须为 `NULL` |
| `@NotNull`     | 注解的元素必须不为 `NULL` |
| `@AssertTrue`  | 注解的元素必须为 `true` |
| `@AssertFalse` | 注解的元素必须为 `false` |
| `@Min(value)` | 注解的元素必须数字，值必须大于等于 `value` |
| `@Max(value)` | 注解的元素必须是数字，值必须小于等于 `value` |
| `@DecimalMin(value)` | 注解的元素必须是数字，值必须大于等于 `value` |
| `@DecimalMax(value)` | 注解的元素必须是数字，值必须小于等于 `value` |
| `@Size(max, min)` | 注解的元素大小必须在指定的范围 `[min, max]` 内 |
| `@Digits(integer, fraction)` | 注解的元素必须是数字，值必须在可接受的范围内 |
| `@Past`  | 注解的元素必须是过去的日期 |
| `@Futuret`  | 注解的元素必须是将来的日期 |
| `@Pattern(value)`  | 注解的元素必须符合指定的正则表达式 |

## Hibernate Validator

依赖信息：
```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>8.0.0.Final</version>
</dependency>
```

| Constraint     | Description |
|----------------|--|
| `@Email`  | 注解的元素必须是电子邮件地址 |
| `@Length`  | 注解的字符串的大小必须在指定的范围内 |
| `@NotEmpty`  | 注解元素的字符串必须非空 |
| `@Range` | 注解元素必须在适当的范围内 |

