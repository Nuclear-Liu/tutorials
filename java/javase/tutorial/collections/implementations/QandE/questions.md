# Questions and Exercises: Implementations


## Questions _问题_


* You plan to write a program that uses several basic collection interfaces: `Set`, `List`, `Queue`, and `Map`. 
  You're not sure which implementations will work best, so you decide to use general-purpose implementations until you get a better idea how your program will work in the real world. 
  Which implementations are these?

* 您计划编写一个使用几个基本集合接口的程序：`Set`、`List`、`Queue` 和 `Map`。
  您不确定哪种实现效果最好，因此您决定使用通用实现，直到您更好地了解您的程序将如何在现实世界中工作。
  这些是哪些实现？

* If you need a `Set` implementation that provides value-ordered iteration, which class should you use?

* 如果您需要一个提供值顺序迭代的 `Set` 实现，您应该使用哪个类？

* Which class do you use to access wrapper implementations?

* 您使用哪个类来访问包装器实现？


## Exercises _练习_


* Write a program that reads a text file, specified by the first command line argument, into a `List`. 
  The program should then print random lines from the file, the number of lines printed to be specified by the second command line argument. 
  Write the program so that a correctly-sized collection is allocated all at once, instead of being gradually expanded as the file is read in. 
  Hint: To determine the number of lines in the file, use `java.io.File.length` to obtain the size of the file, then divide by an assumed size of an average line.


* 编写一个程序，将由第一个命令行参数指定的文本文件读取到 `List` 中。
  然后程序应该从文件中打印随机行，打印的行数由第二个命令行参数指定。
  编写程序，以便一次分配一个大小正确的集合，而不是随着文件的读入而逐渐扩展。
  提示：要确定文件中的行数，请使用 `java.io.File.length` 获取文件的大小，然后除以平均行的假定大小。


[Check your answers.](https://docs.oracle.com/javase/tutorial/collections/implementations/QandE/answers.html)


[Check your answers.](./answers.md)
