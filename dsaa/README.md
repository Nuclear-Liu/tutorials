# 数据结构与算法


## 时间复杂度


描述**常数操作**（操作与**数据量**没有关系是固定时间）次数的指标；
时间复杂度之关系最高阶（不关心常数项和系数）；
常数操作时间复杂度认为是 `O(1)` ；


如果跟数据量有关的操作，就不是常熟操作；


`O(1) < O(logN) < O(N) < O(N*logN) < O(N^2) < O(N^3) < O(N^K) < O(2^N) < O(3^N) < O(K^N) < O(N!)`


> 插入排序时间复杂度：
> 
> `Sn= na1 + n(n-1)/2d, n[N*]` : `Sn= n2/2` : `O(n2)`

## 基础

* [排序](./src/main/doc/SimpleSort/Sort.md)
* [`Math.random()` & 概率结果区间变换](./src/main/doc/jdk/Random.md)
* [对数器](./src/main/doc/Tools/对数器.md)
* [动态数组](./src/main/doc/DynamicArray/DynamicArray.md)
* [哈希表和有序表](./src/main/doc/map/HashMapTreeMap.md)
* [单链表和双链表](./src/main/doc/list/List.md)
