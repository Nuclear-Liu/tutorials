## Data Types

Most of the data types are self explanatory and are adapted to the database in use. 
Refer to the database documentation for its data types specifics.


大多数数据类型都是不言自明的，并且适用于正在使用的数据库。
有关其数据类型的详细信息，请参阅数据库文档。

---

The following sections document data types within dbUnit.


以下部分记录了 dbUnit 中的数据类型。

---

1. BigInteger
2. BinaryStream
3. Bit
4. Blob
5. Boolean
6. Bytes
7. Clob
8. Date
9. Double
10. Float
11. Integer
12. Long
13. Number
14. NumberTolerant
15. String
16. StringIgnoreCase
17. Time
18. Timestamp
19. UuidAwareBytes

### Blob

`Blob` JavaDoc

`Blobs` contain binary data. 
DbUnit, however, processes data with `XML` or `CSV` files containing textual data sets, not binary. 
To push binary data into a database blob from dbUnit data set files, use the following syntax in a data set field: 


`Blob` 包含二进制数据。
但是，DbUnit 使用包含文本数据集而非二进制的 `XML` 或 `CSV` 文件处理数据。
要将二进制数据从 dbUnit 数据集文件推送到数据库 blob，请在数据集字段中使用以下语法：

---

`... field_name="[TEXT|BASE64|FILE|URL <optional argument>] optional text"`

The table below shows how to use each of the forms available to load binary data into a blob. 


下表显示了如何使用每种可用的形式将二进制数据加载到 blob 中。

---

| Data Type | Description |
| ---- | ---- |
| `TEXT` | [TEXT]() |
| `BASE64` | [BASE64]() |
| `FILE` | [FILE]() |
| `URL` | [URL]() |

### Relative date, time, and timestamp _相对日期、时间和时间戳_

It is possible to push date, time, and timestamp values relative to the current `date`/`time` by using the following syntax in a data set field (since 2.7.0): 


通过在数据集字段中使用以下语法（自 2.7.0 起），可以推送相对于当前 `date`/`time` 的日期、时间和时间戳值：

---

`... field_name="[now{diff...}{time}]"`

`diff` consists of two parts 1) a number with a leading plus or minus sign and 2) a character represents temporal unit. 
See the list below for the supported units. 
There can be multiple `diff`s and they can be specified in any order.


`diff` 由两部分组成：1) 一个带有前导加号或减号的数字和 2) 一个字符代表时间单位。
有关支持的单元，请参见下面的列表。
可以有多个 `diff` 并且它们可以按任何顺序指定。

---

`time` is a string that can be parsed by `LocalTime#parse()`. 
If specified, it is used instead of the current time.


`time` 是一个可以被 `LocalTimeparse()` 解析的字符串。
如果指定，则使用它而不是当前时间。

---

Both `diff` and `time` are optional.


`diff` 和 `time` 都是可选的。

---

Whitespaces are allowed before and after each `diff`.


每个“diff”前后都允许有空格。

---

* `y` : years
* `M` : months
* `d` : days
* `h` : hours
* `m` : minutes
* `s` : seconds

Here are some examples:


这里有些例子：

---

* `[now]` : current date time
* `[now-1d]` : the same time yesterday
* `[now+1y+1M-2h]` : a year and a month from today, two hours earlier
* `[now+1d 10:00]` : 10 o'clock tomorrow

