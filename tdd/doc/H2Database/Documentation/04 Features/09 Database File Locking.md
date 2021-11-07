## Database File Locking *数据库文件锁定*

Whenever a database is opened, a lock file is created to signal other processes that the database is in use.
If database is closed, or if the process that opened the database terminates, this lock file is deleted.


每当打开数据库时，都会创建一个锁定文件，以向其他进程通知该数据库正在使用中。
如果数据库关闭，或者打开数据库的进程终止，则删除此锁定文件。

---

The following file locking methods are implemented:


实现了以下文件锁定方法：

---

* The default method is `FILE` and uses a watchdog thread to protect the database file.
  The watchdog reads the lock file each second.
* The second method is `SOCKET` and opens a server socket.
  The socket method does not require reading the lock file every second.
  The socket method should only be used if the database files are only accessed by one (and always the same) computer.
* The third method is `FS`. 
  This will use native file locking using `FileChannel.lock`.
* It is also possible to open the database without file locking; in this case it is up to the application to protect the database files.
  Failing to do so will result in a corrupted database. 
  Using the method `NO` forces the database to not create a lock file at all.
  Please note that this is unsafe as another process is able to open the same database, possibly leading to data corruption.


* 默认方法是 `FILE` 并使用看门狗线程来保护数据库文件。
  看门狗每秒读取锁定文件。
* 第二种方法是`SOCKET` 并打开一个服务器套接字。
  socket 方法不需要每秒读取锁定文件。
  仅当数据库文件只能由一台（并且始终相同）计算机访问时，才应使用套接字方法。
* 第三种方法是 `FS` 。
  这将使用 `FileChannel.lock` 使用本地文件锁定。
* 也可以在不锁定文件的情况下打开数据库；在这种情况下，由应用程序来保护数据库文件。
  否则将导致数据库损坏。
  使用 `NO` 方法强制数据库根本不创建锁定文件。
  请注意，这是不安全的，因为另一个进程能够打开同一个数据库，可能会导致数据损坏。
---

To open the database with a different file locking method, use the parameter `FILE_LOCK`.
The following code opens the database with the 'socket' locking method:


要使用不同的文件锁定方法打开数据库，请使用参数 `FILE_LOCK` 。
以下代码使用 'socket' 锁定方法打开数据库：

```
String url = "jdbc:h2:~/test;FILE_LOCK=SOCKET";
```

For more information about the algorithms, see [Advanced / File Locking Protocols](). 


有关算法的更多信息，请参阅 [高级文件锁定协议]()。

---
