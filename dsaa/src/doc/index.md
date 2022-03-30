# 算法

1. 有具体的问题；
2. 有设计解决这个问题的具体流程；
3. 有评价处理流程的可量化指标；

> 算法分类：
> 
> *  明确知道怎么算的流程；
> *  明确知道怎么尝试的流程；

## 基础知识

### 位运算

#### 负数的表示


最高位表示符号位，负数表示的值 = 存储的二进制值（除去符号位）按位取反 + 1；


## 排序

* 选择排序
* 冒泡排序
* 插入排序

## 数据结构

* 连续结构
* 跳转结构


> 随机函数： `Math.random()`
> 
> 返回值： `double` `[0,1)` 等概率返回；
> 
> `Math.random() * K`: `[0, K)` 等概率
> 
> `(int)(Math.random() * K)`: `[0, K - 1]` 等概率；


```java
public class RandTest {
    /**
     * 等概率返回 1 ~ 5 .
     * @return [1, 5]
     */
    public static int f() {
        return (int) (Math.random() * 5) + 1;
    }

    /**
     * 0 1 等概率返回 利用 f().
     * @return [0, 1]
     */
    public static int f1() {
        int ans = 0;
        do {
            ans = f();
        } while (ans == 3);
        return ans < 3 ? 0 : 1;
    }

    /**
     * 0 ~7 等概率返回
     * @return [0, 7]
     */
    public static int f2() {
        return (f1() << 2) + (f1() << 1) + (f1() << 0);
    }
}
```
