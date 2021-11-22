## Data Types

Most of the data types are self explanatory and are adapted to the database in use. 
Refer to the database documentation for its data types specifics.

The following sections document data types within dbUnit.

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

Blob JavaDoc

Blobs contain binary data. DbUnit, however, processes data with XML or CSV files containing textual data sets, not binary. 
To push binary data into a database blob from dbUnit data set files, use the following syntax in a data set field: 

`... field_name="[TEXT|BASE64|FILE|URL <optional argument>] optional text"`

The table below shows how to use each of the forms available to load binary data into a blob. 

| Data Type | Description |
| ---- | ---- |
| `TEXT` |  |
| `BASE64` |  |
| `FILE` |  |
| `URL` |  |

### Relative date, time, and timestamp

It is possible to push date, time, and timestamp values relative to the current date/time by using the following syntax in a data set field (since 2.7.0): 

`... field_name="[now{diff...}{time}]"`

`diff` consists of two parts 1) a number with a leading plus or minus sign and 2) a character represents temporal unit. 
See the list below for the supported units. 
There can be multiple `diff`s and they can be specified in any order.

`time` is a string that can be parsed by `LocalTime#parse()`. 
If specified, it is used instead of the current time.

Both `diff` and `time` are optional.

Whitespaces are allowed before and after each `diff`.

* y : years
* M : months
* d : days
* h : hours
* m : minutes
* s : seconds

Here are some examples:

* `[now]` : current date time
* `[now-1d]` : the same time yesterday
* `[now+1y+1M-2h]` : a year and a month from today, two hours earlier
* `[now+1d 10:00]` : 10 o'clock tomorrow

