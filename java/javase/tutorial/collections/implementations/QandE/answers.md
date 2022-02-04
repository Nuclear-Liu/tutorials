# Answers to Questions and Exercises:


## Questions _问题_


* Question: 
  
  You plan to write a program that uses several basic collection interfaces: 
  `Set`, `List`, `Queue`, and `Map`. 
  You're not sure which implementations will work best, so you decide to use general-purpose implementations until you get a better idea how your program will work in the real world. 
  Which implementations are these?
    
  Answer:

  `Set`: `HashSet`

  `List`: `ArrayList`

  `Queue`: `LinkedList`

  `Map`: `HashMap`

* 问题: 

  您计划编写一个使用几个基本集合接口的程序：
  `Set` 、 `List` 、 `Queue` 和 `Map` 。
  您不确定哪种实现效果最好，因此您决定使用通用实现，直到您更好地了解您的程序将如何在现实世界中工作。
  这些是哪些实现？

  回答:

  `Set`: `HashSet`

  `List`: `ArrayList`

  `Queue`: `LinkedList`

  `Map`: `HashMap`

* Question: 

  If you need a `Set` implementation that provides value-ordered iteration, which class should you use?

  Answer:

    `TreeSet` guarantees that the sorted set is in ascending element order, sorted according to the natural order of the elements or by the Comparator provided.

* 问题: 

  如果您需要一个提供值顺序迭代的 `Set` 实现，您应该使用哪个类？

  回答:

  `TreeSet` 保证排序后的集合按元素升序排列，根据元素的自然顺序或提供的 `Comparator` 排序。

* Question: 

  Which class do you use to access wrapper implementations?

  Answer:

  You use the `Collections` class, which provides static methods that operate on or return collections.

* 问题: 

  您使用哪个类来访问包装器实现？

  回答:

  您使用 `Collections` 类，它提供了对集合进行操作或返回集合的静态方法。


## Exercises _练习_


* Exercise: 

  Write a program that reads a text file, specified by the first command line argument, into a `List`. 
  The program should then print random lines from the file, the number of lines printed to be specified by the second command line argument. 
  Write the program so that a correctly-sized collection is allocated all at once, instead of being gradually expanded as the file is read in. 
  Hint: To determine the number of lines in the file, use `java.io.File.length` to obtain the size of the file, then divide by an assumed size of an average line.

  Answer:

  Since we are accessing the `List` randomly, we will use `ArrayList`. 
  We estimate the number of lines by taking the file size and dividing by 50. 
  We then double that figure, since it is more efficient to overestimate than to underestimate. 

* 练习: 

  编写一个程序，将由第一个命令行参数指定的文本文件读取到 `List` 中。
  然后程序应该从文件中打印随机行，打印的行数由第二个命令行参数指定。
  编写程序，以便一次分配一个大小正确的集合，而不是随着文件的读入而逐渐扩展。
  提示：要确定文件中的行数，请使用 `java.io.File.length` 获取文件的大小，然后除以平均行的假定大小。

  回答:

  由于我们随机访问 `List` ，我们将使用 `ArrayList` 。
  我们通过获取文件大小并除以 50 来估计行数。
  然后我们将该数字翻倍，因为高估比低估更有效。


```java
import java.util.*;
import java.io.*;

public class FileList {
    public static void main(String[] args) {
        final int assumedLineLength = 50;
        File file = new File(args[0]);
        List<String> fileList = 
            new ArrayList<String>((int)(file.length() / assumedLineLength) * 2);
        BufferedReader reader = null;
        int lineCount = 0;
        try {
            reader = new BufferedReader(new FileReader(file));
            for (String line = reader.readLine(); line != null;
                    line = reader.readLine()) {
                fileList.add(line);
                lineCount++;
            }
        } catch (IOException e) {
            System.err.format("Could not read %s: %s%n", file, e);
            System.exit(1);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {}
            }
        }
        int repeats = Integer.parseInt(args[1]);
        Random random = new Random();
        for (int i = 0; i < repeats; i++) {
            System.out.format("%d: %s%n", i,
                    fileList.get(random.nextInt(lineCount - 1)));
        }
    }
}
```


This program actually spends most of its time reading in the file, so pre-allocating the `ArrayList` has little affect on its performance. 
Specifying an initial capacity in advance is more likely to be useful when your program repeatedly creates large `ArrayList` objects without intervening I/O.


该程序实际上大部分时间都在读取文件，因此预先分配 `ArrayList` 对其性能几乎没有影响。
当您的程序在不干预 I/O 的情况下重复创建大型 `ArrayList` 对象时，提前指定初始容量可能更有用。
