## Split File System *拆分文件系统*

The file system prefix `split`: is used to split logical files into multiple physical files, for example so that a database can get larger than the maximum file system size of the operating system.
If the logical file is larger than the maximum file size, then the file is split as follows:


文件系统前缀`split`：用于将逻辑文件拆分为多个物理文件，例如，使数据库可以大于操作系统的最大文件系统大小。
如果逻辑文件大于最大文件大小，则文件拆分如下：

---

* `<fileName>` (first block, is always created)
* `<fileName>.1.part` (second block)


* `<fileName>` （第一个块，总是被创建）
* `<fileName>.1.part` （第二个块）

---

More physical files (`*.2.part`, `*.3.part`) are automatically created / deleted if needed.
The maximum physical file size of a block is 2^30 bytes, which is also called 1 GiB or 1 GB.
However this can be changed if required, by specifying the block size in the file name.
The file name format is: `split:<x>:<fileName>` where the file size per block is 2^x.
For 1 MiB block sizes, use x = 20 (because 2^20 is 1 MiB).
The following file name means the logical file is split into 1 MiB blocks: `split:20:~/test.h2.db`.
An example database URL for this case is `jdbc:h2:split:20:~/test`.


如果需要，会自动创建删除更多物理文件（ `.2.part`、 `.3.part`）。
一个块的最大物理文件大小为 2^30 字节，也称为 1 GiB 或 1 GB。
但是，如果需要，可以通过在文件名中指定块大小来更改此设置。
文件名格式为： `split:<x>:<fileName>` ，其中每个块的文件大小为 2^x。
对于 1 MiB 块大小，使用 x = 20（因为 2^20 是 1 MiB）。
以下文件名表示逻辑文件被拆分为 1 MiB 块： `split:20:~test.h2.db` 。
这种情况下的示例数据库 URL 是 `jdbc:h2:split:20:~test` 。

---
