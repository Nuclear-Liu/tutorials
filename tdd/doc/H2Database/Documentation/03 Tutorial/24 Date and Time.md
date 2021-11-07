## Date and Time *日期和时间*

Date, time and timestamp values support ISO 8601 formatting, including time zone:


日期、时间和时间戳值支持 ISO 8601 格式，包括时区：

---

```sql
CALL TIMESTAMP '2008-01-01 12:00:00+01:00';
```

If the time zone is not set, the value is parsed using the current time zone setting of the system.
Date and time information is stored in H2 database files with or without time zone information depending on used data type.


如果未设置时区，则使用系统的当前时区设置解析该值。
日期和时间信息存储在带有或不带有时区信息的 H2 数据库文件中，具体取决于使用的数据类型。

---

* With TIMESTAMP data type if the database is opened using another system time zone, the date and time will be the same.
  That means if you store the value '2000-01-01 12:00:00' in one time zone, then close the database and open the database again in a different time zone, you will also get '2000-01-01 12:00:00'.
  Please note that changing the time zone after the H2 driver is loaded is not supported.

* With TIMESTAMP WITH TIME ZONE data type time zone offset is stored and if you store the value '2008-01-01 12:00:00+01:00' it remains the same even if you close and reopen the database with a different time zone.
  If you store the value with specified time zone name like '2008-01-01 12:00:00 Europe/Berlin' this name will be converted to time zone offset.
  Names of time zones are not stored.


* 使用 TIMESTAMP 数据类型如果使用另一个系统时区打开数据库，则日期和时间将相同。
  这意味着如果您将值 '2000-01-01 12:00:00' 存储在一个时区，然后关闭数据库并在不同的时区再次打开数据库，您也会得到 '2000-01-01 12 :00:00'。
  请注意，不支持在加载 H2 驱动程序后更改时区。
* 使用 TIMESTAMP WITH TIME ZONE 数据类型时区偏移被存储，如果您存储值 '2008-01-01 12:00:00+01:00' 它保持不变，即使您关闭并重新打开数据库的时间不同区。
  如果您使用指定的时区名称存储值，例如“2008-01-01 12:00:00 EuropeBerlin”，则此名称将转换为时区偏移量。
  不存储时区名称。
  
---
