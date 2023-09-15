# `String` 工具

## `Joiner`

用分隔符将字符串进行连接。

```jshelllanguage
Joiner joiner = Joiner.on("; ").skipNulls();
return joiner.join("Harry", null, "Ron", "Hermione"); // "Harry; Ron; Hermione"
```

* `on()` 字符连接指定连接字符
* `skipNulls()` 指定一个字符串代替 `null` 

可以在对象上使用 `Joiner` ，对象将使用其 `toString` 进行转换，然后连接起来。

```jshelllanguage
Joiner.on(",").join(Arrays.asList(1, 5, 7)); // "1,5,7"
```

**警告：**连接器实例始终是不可变的。
连接器配置方法将始终返回一个新的 `Joiner` ，必须使用他来获取所需的语义。
这使得任何 `Joiner` 线程都是安全的，并且可以用作 `static final` 变量。

## `Splitter`

用于字符串拆分。

```jshelllanguage
Splitter.on(',')
    .trimResults()
    .omitEmptyStrings()
    .split("foo,bar,,   qux");
```

* `on()` 指定分隔符
* `trimResults()` 自动删除每个返回子串的签到和尾部空白
* `omitEmptyStrings()` 自动从结果中省略空字符串
* `split()` 指定分割的原始字符串

#### 基础工厂

| 方法                  | 描述 | 案例 |
|---------------------| ---- | ---- |
| `Splitter.on(char)` |      |      |
| ``                  |      |      |
| ``                  |      |      |
| ``                  |      |      |
| ``                  |      |      |
