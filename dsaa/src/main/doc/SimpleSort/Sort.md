# [简单排序](./../../java/org/hui/dsaa/simplesort/Sort.java)


## [选择排序](./../../../test/java/org/hui/dsaa/simplesort/SelectionSortTests.java)


遍历数组标记数组中最大值的位置，最后最大值位置与最后一个元素的位置交换；
遍历数组剩余数组中的元素，选择剩余元素中的最大位置与未排序的最后一个元素交换；
当未排序剩余1个元素，整个数组有序；


## [冒泡排序](./../../../test/java/org/hui/dsaa/simplesort/BubbleSortTests.java)


遍历数组，当当前位置元素大于下一个元素时，交换两个元素的位置，遍历一遍，数组最大值到达数组的最后元素位置；
遍历数组剩余未排序部分，最后当数组未排序的部分剩余1个元素，整个数组有序；


## [插入排序](./../../../test/java/org/hui/dsaa/simplesort/InsertSortTests.java)


从左到右遍历数组，假设第一个元素构成的子数组已经有序，当插入有序子数组后相邻元素时，当前元素倒叙与有序子数组各个元素比较，当前元素小于比较元素交换，否则存放在当前位置，最终数组有序；
