# Overview _概述_

# Best Practices _最佳实践_


### Annotation

### [`@Getter` / `@Setter`](./features/05%20GetterSetter.md)
    * Target
      * `ElementType.FIELD`(Only on no-static fields)
      * `ElementType.TYPE`
    * `value`: `AccessLevel`
      * `AccessLevel.PUBLIC` _default_
      * `AccessLevel.MODULE`
      * `AccessLevel.PROTECTED`
      * `AccessLevel.PACKAGE`
      * `AccessLevel.PRIVATE`
      * `AccessLevel.NONE` Represents not generating anything or the complete lack of a method.
      
### [`@ToString`](./features/06%20ToString.md)
  * `@Target(ElementType.TYPE)`

### [``]()

### Configuration keys


```lombok.config
lombok.val.flagUsage = ERROR
lombok.var.flagUsage = ERROR
lombok.nonNull.flagUsage = ERROR
lombok.cleanup.flagUsage = ERROR # try-with-rsource
```
