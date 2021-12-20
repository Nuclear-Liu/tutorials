## Database Testing _数据库测试_

Richard Dallaway notes about database unit testing inspired me the realization of the DbUnit framework. 
I think this is a very good text about this subject. 
Hopefully he gave me the permission to present excerpts from his notes here.


Richard Dallaway 关于数据库单元测试的笔记启发了我实现 DbUnit 框架。
我认为这是一篇关于这个主题的非常好的文本。
希望他允许我在这里展示他笔记的摘录。

---

The original text is much longer and I strongly suggest you to read it as well. 
See [resources](http://dbunit.sourceforge.net/dbunit/resources.html#RichardDallaway) for reference to it. - Manuel Laflamme


原文较长，我强烈建议您也阅读一下。
请参阅 [resources]() 以供参考。 - 曼努埃尔·拉弗拉姆

---

## Unit testing database _单元测试数据库_

These are my notes on how I've gone about unit testing database functionality.


这些是关于我如何进行单元测试数据库功能的笔记。

---

\[...\]

The problem is this: you have a SQL database, some stored procedures, and a layer of code sitting between your application and the database. 
How can you put tests in place to make sure your code really is reading and writing the right data from the database?


问题是：您有一个 SQL 数据库、一些存储过程和位于应用程序和数据库之间的代码层。
您如何进行测试以确保您的代码确实从数据库中读取和写入正确的数据？

---

## Why bother? _何苦？_

I'm guessing some, if not a lot, of database development goes like this: 
set up database, write code to access database, run code, do a `SELECT` to see if the records showed up in the database. 
They did? Good, then we're done.


我猜一些（如果不是很多）数据库开发是这样的：
设置数据库，编写访问数据库的代码，运行代码，执行 `SELECT` 以查看记录是否出现在数据库中。
他们做到了？好，那么我们就完成了。

---

The problem with visual inspection is this: 
you don't do it often, and you don't check everything every time. 
It's possible that when you make changes to a system, maybe months later, you break something and some data will go missing. 
As a coder you may not spend much time checking the data itself, so it may take a while for this mistake to surface. 
I've worked on a web project where a mandatory field on a registration form was not being inserted into a database for the best part of a year. 
Although marketing had protested that they needed this information, the problem wasn't spotted because the data was never ever looked at it (but don't get me started on that).


目视检查的问题是：
你不经常这样做，你也不会每次都检查一切。
有可能当您对系统进行更改时，也许几个月后，您会破坏某些内容并且某些数据会丢失。
作为一名编码员，您可能不会花太多时间检查数据本身，因此这个错误可能需要一段时间才能浮出水面。
我曾参与过一个网络项目，在该项目中，一年中大部分时间都没有将注册表单上的必填字段插入到数据库中。
尽管营销部门抗议他们需要这些信息，但问题并未被发现，因为数据从未被查看过（但不要让我开始这样做）。

---

Automated tests — painless tests that run often and test lots — reduce the chances of your data is going missing. 
I find they make it easier for me to sleep at night. 
(Tests have other positive features: they're good examples of how to use code, they act as documentation, they make other people's code less scary when you need to change it, they reduce debugging time).


自动化测试——经常运行和大量测试的无痛测试——减少数据丢失的机会。
我发现它们让我晚上更容易入睡。
（测试还有其他积极的特性：它们是如何使用代码的好例子，它们充当文档，当你需要更改时，它们使其他人的代码不那么可怕，它们减少了调试时间）。

---

\[...\]

But how do we manage the testing data in the database so that it doesn't "mess up" live data?  


但是我们如何管理数据库中的测试数据，使其不会“弄乱”实时数据？

---

## You need \[multiple\] databases _您需要\[多个\] 数据库_

Some thoughts: A good test set is self-sufficient and creates all the data it needs. 
Testing can be simplified if you can get the database in a known state before a test is run. 
One ways to do this is to have a separate unit test database, which is under the control of the test cases: the test cases clean out the database before starting any tests.


一些想法：一个好的测试集是自给自足的，并创建它需要的所有数据。
如果您可以在运行测试之前使数据库处于已知状态，则可以简化测试。
一种方法是拥有一个单独的单元测试数据库，它在测试用例的控制之下：测试用例在开始任何测试之前清理数据库。

---

[...]

Deleting and inserting data for every test may seem like a big time over head, but as tests use relatively little data, I find this approach to be quick enough (especially if you're running against a local test database).


为每个测试删除和插入数据似乎很费时间，但由于测试使用的数据相对较少，我发现这种方法足够快（尤其是在针对本地测试数据库运行时）。

---

[...]

The downside is that you need more than one database - but remember, they can all run on one server if necessary. 
The way I'm testing now needs four databases (well, two at a pinch):


缺点是您需要多个数据库 - 但请记住，如有必要，它们都可以在一台服务器上运行。
我现在测试的方式需要四个数据库（好吧，两个）：

---

1. The **production database**, Live data. No testing on this database.
2. Your **local development database**, which is where most of the testing is carried out.
3. A **populated development database**, possibly shared by all developers so you can run your application and see it work with realistic amounts of data, rather than the hand full of records you have in your test database. 
    You may not strictly need this, but it's reassuring to see your app work with lots of data (i.e., a copy of the production database's data).
4. A **deployment database**, or integration database, where the tests are run prior to deployment to make sure any local database changes have been applied. 
    If you're working alone, you may be able to live without this one, but you'll have to be sure any database structure or stored procedure changes have been made to the production database before you go live with your code.


1. **production database** ，实时数据。没有对此数据库进行测试。
2. 您的 **local development database** ，这是进行大部分测试的地方。
3. **populated development database** ，可能由所有开发人员共享，因此您可以运行您的应用程序并查看它是否处理实际数量的数据，而不是您在测试数据库中拥有的大量记录。
    您可能并不严格需要这个，但看到您的应用程序处理大量数据（即，生产数据库数据的副本）令人放心。
4. **deployment database** ，或集成数据库，在部署之前运行测试以确保已应用任何本地数据库更改。
    如果您是单独工作，您也许可以不用这个，但是在使用您的代码之前，您必须确保已对生产数据库进行了任何数据库结构或存储过程更改。

---

With multiple database you have to make sure you keep the structure of the databases in sync: 
if you change a table definition or a stored procedure on your test machine, you'll have to remember to make those changes on the live server. 
The deployment database should act as a reminder to make those changes.


对于多个数据库，您必须确保保持数据库结构同步：
如果您在测试机器上更改表定义或存储过程，则必须记住在实时服务器上进行这些更改。
部署数据库应作为进行这些更改的提醒。

---
