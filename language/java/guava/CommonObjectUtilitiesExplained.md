# 对象公共方法 Object common methods

## `equals`

当你的对象字段可以为 `null` 时，实现 `Objects.equals` 会很麻烦，必须单独检查 `null` 。
使用 `Objects.equals` 可以让你以对 `null` 敏感的方式进行 `equals` 检查，而不会有 `NullPointerException` 的风险。

```jshelllanguage
Objects.equal("a", "a");
Objects.equal(null, "a");
Objects.equal("a", null);
Objects.equal(null, null);
```

**注意**: JDK 7 中引入了 `Objects` 类提供了相似的方法。

## `hashCode`

对一个 `Object` 的所有字段进行散列应该更简单。
Guava 的 `Objects.hashCode(Object...)` 为指定的字段序列创建一个合理的、**顺序敏感**的哈希值。

**注意**: JDK 7 中引入的 `Objects` 类提供了相似的方法。

## `toString`

使用 `MoreObjects.toStringHelper()` 创建一个有用的 `toString` 方法。

```jshelllanguage
// Returns "ClassName{x=1}"
MoreObjects.toStringHelper(this).add("x",1).toString();
MoreObjects.toStringHelper(class_name).add("x",1).toString();
MoreObjects.toStringHelper(class_name.class).add("x",1).toString();
```

## `compare`/`compareTo`

Guava 提供了 `ComparisonChain` 方便实现。

`ComparisonChain` 执行**惰性**比较：它只执行比较，直到找到一个非零的结果，然后忽略进一步的输入。

```jshelllanguage
@Override
public int compareTo(Foo o) {
	return ComparisonChain.start()
			.compare(aString,o.aString)
			.compare(anInt,o.anInt)
			.compare(anEnum,o.anEnum, Ordering.natural().nullsLast())
			.result();
}
```
