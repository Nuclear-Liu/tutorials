## Universally Unique Identifiers (UUID) *通用唯一标识符 (UUID)*

This database supports UUIDs. 
Also supported is a function to create new UUIDs using a cryptographically strong pseudo random number generator.
With random UUIDs, the chance of two having the same value can be calculated using the probability theory.
See also 'Birthday Paradox'.
Standardized randomly generated UUIDs have 122 random bits.
4 bits are used for the version (Randomly generated UUID), and 2 bits for the variant (Leach-Salz).
This database supports generating such UUIDs using the built-in function `RANDOM_UUID()` or `UUID()`.
Here is a small program to estimate the probability of having two identical UUIDs after generating a number of values:


此数据库支持 UUID。
还支持使用加密强伪随机数生成器创建新 UUID 的功能。
对于随机 UUID，可以使用概率论计算两个具有相同值的机会。
另见“生日悖论”。
标准化随机生成的 UUID 有 122 个随机位。
4 位用于版本（随机生成的 UUID），2 位用于变体（Leach-Salz）。
该数据库支持使用内置函数 `RANDOM_UUID()` 或 `UUID()` 生成此类 UUID。
这是一个小程序，用于估计在生成多个值后具有两个相同 UUID 的概率：

---

```java
public class Test {
    public static void main(String[] args) throws Exception {
        double x = Math.pow(2, 122);
        for (int i = 35; i < 62; i++) {
            double n = Math.pow(2, i);
            double p = 1 - Math.exp(-(n * n) / 2 / x);
            System.out.println("2^" + i + "=" + (1L << i) +
                    " probability: 0" +
                    String.valueOf(1 + p).substring(1));
        }
    }
}
```

Some values are:


一些值是：

---

| Number of UUIs | Probability of Duplicates |
| ---- | ---- |
| 2^36=68'719'476'736 | 0.000'000'000'000'000'4 |
| 2^41=2'199'023'255'552 | 0.000'000'000'000'4 |
| 2^46=70'368'744'177'664 | 0.000'000'000'4 |


| UUI 数量 | 重复的概率 |
| ---- | ---- |
| 2^36=68'719'476'736 | 0.000'000'000'000'000'4 |
| 2^41=2'199'023'255'552 | 0.000'000'000'000'4 |
| 2^46=70'368'744'177'664 | 0.000'000'000'4 |

---

To help non-mathematicians understand what those numbers mean, here a comparison: one's annual risk of being hit by a meteorite is estimated to be one chance in 17 billion, that means the probability is about 0.000'000'000'06.


为了帮助非数学家理解这些数字的含义，这里进行一个比较：一个人每年被陨石撞击的风险估计为 170 亿分之一，这意味着概率约为 0.000'000'000'06。

---
