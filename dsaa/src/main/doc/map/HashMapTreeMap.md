# 哈希表和有序表

## 哈希表 `HashMap`

增删改查都是常数时间(`O(1)`)；

> 按值传递的哈希表（原生类型和对应的装箱类：`Short` `Integer` `Double` `Float` `String`）；占用内存空间为 k + v ；
> 
> 按引用传递的哈希表（非原生类型）；占用空间为两个引用指针；

## 有序表 `TreeMap`

所有操作时间复杂度 `O(logN)` ；
自定义类型 `key` 必须是能够比较的；

比 `HashMap` 强大提供了，内部按照 `key` 排序；

* `floorkey(keyValue)`: `key <= keyValue` 离 `keyValue` 最近的 `key`
* `ceilingKey(keyValue)`: `key >= keyValue` 离 `keyValue` 最近的 `key`
