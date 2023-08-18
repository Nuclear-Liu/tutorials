# 反射

## `TypeToken`

由于类型擦除，无法在运行时传递泛型 `Class` 对象——可以对它们强制转换并假装它们是泛型的，但实际上并非如此。

例如：
```jshelllanguage
ArrayList<String> stringList = Lists.newArrayList();
ArrayList<Integer> intList = Lists.newArrayList();
System.out.println(stringList.getClass().isAssignableFrom(intList.getClass())); // returns ture 
```

> `boolean isAssignableFrom(Class<?> cls)`: 
> 确定此 `Class` 对象所代表的类或接口是否与指定的 `Class` 参数所代表的类或接口**相同**，或者是其**超类**或**超接口**。
> 如果是，则返回 `true` ；否则返回 `false` 。
> 如果该类代表原始类型，那么如果指定的类参数正是该类对象，则该方法返回 `true` ；否则返回 `false` 。

`TypeToken` 使用基于反射的技巧来允许操作和查询泛型类型，即使在运行时也是如此。

#### 背景：类型擦除和反射

Java 在运行时不保留*对象*的通用类型信息。
但是**反射允许检测方法和类的通用类型**。
如果实现返回 `List` 方法，并使用反射获取该犯法的返回类型，则会返回表示 `List` 的**参数化类型**。

`TypeToken` 类使用此解决方法允许最小的语法开销操作**泛型类型**。

#### 简介

获取基本原始类的 `TypeToken` 非常简单： `TypeToken<T> tok = TypeToken.of(Class<T> type)`

为具有泛型的类型获取 `TypeToken` -- 当在编译时知道泛型参数时：（使用一个空的匿名内部函数） `TypeToken<List<String>> strsTok = new TypeToken<List<String>>() {}`

想特意引用通配符类型：（使用一个空的匿名内部函数） `TypeToken<Map<?,?>> mapTok = new TypeToken<Map<?, ?>>() {};`

`TypeToken` 提供了一种动态解析通用类型参数的方法：

```jshelllanguage
static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
    return new TypeToken<Map<K, V>>() {}
            .where(new TypeParameter<K>() {}, keyToken)
            .where(new TypeParameter<V>() {}, valueToken);
}
```

> **注意：**如果 `mapToken` 只是返回 `new TypeToken<Map<K, V>>()` ，它实际上无法具体化分配 `K` 和 `V` 的类型。

或者，可以用一个（通常是匿名的）子类来捕获一个通用类型，然后针对一个知道的类型参数是什么的上下文来解析它： `new IType<String>() {}.type`

```jshelllanguage
abstract class IType<T> {
    TypeToken<T> type = new TypeToken<T>(getClass()) {};
}
```

通过这种技术获得知道其元素类型的类。

### 查询

| 方法                       | 描述                              |
|--------------------------|---------------------------------|
| `getType()`              | 返回包装后的 `java.lang.reflect.Type` |
| `getRawType()`           |                                 |
| `getSubtype(Class<?>)`   |                                 |
| `getSupertype(Class<?>)` |                                 |
| ``                       |                                 |
| ``                       |                                 |
| ``                       |                                 |
| ``                       |                                 |
