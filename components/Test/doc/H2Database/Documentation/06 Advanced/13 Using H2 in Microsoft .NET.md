## Using H2 in Microsoft .NET *在 Microsoft .NET 中使用 H2*

The database can be used from Microsoft .NET even without using Java, by using IKVM.NET.
You can access a H2 database on .NET using the JDBC API, or using the ADO.NET interface.


通过使用 IKVM.NET，即使不使用 Java，也可以从 Microsoft .NET 使用该数据库。
您可以使用 JDBC API 或使用 ADO.NET 接口访问 .NET 上的 H2 数据库。

---

### Using the ADO.NET API on .NET *在 .NET 上使用 ADO.NET API*

An implementation of the ADO.NET interface is available in the open source project [H2Sharp]().


开源项目 [H2Sharp]() 中提供了 ADO.NET 接口的实现。

---

### Using the JDBC API on .NET *在 .NET 上使用 JDBC API*

* Install the .NET Framework from [Microsoft](). 
  Mono has not yet been tested.
* Install [IKVM.NET]().
* Copy the `h2*.jar` file to `ikvm/bin`
* Run the H2 Console using: `ikvm -jar h2*.jar`
* Convert the H2 Console to an `.exe` file using: 
  `ikvmc -target:winexe h2*.jar`. 
  You may ignore the warnings.
* Create a `.dll` file using (change the version accordingly): `ikvmc.exe -target:library -version:1.0.69.0 h2*.jar`


* 从 [Microsoft]() 安装 .NET Framework。
  Mono 尚未经过测试。
* 安装 [IKVM.NET]()。
* 将 `h2.jar` 文件复制到 `ikvmbin`
* 使用以下命令运行 H2 控制台： `ikvm -jar h2.jar`
* 使用以下命令将 H2 控制台转换为 `.exe` 文件：
  `ikvmc -target:winexe h2.jar` 。
  您可以忽略这些警告。
* 使用（相应地更改版本）创建一个 `.dll` 文件： `ikvmc.exe -target:library -version:1.0.69.0 h2.jar`

---

If you want your C# application use H2, you need to add the `h2.dll` and the `IKVM.OpenJDK.ClassLibrary.dll` to your C# solution. 
Here some sample code:


如果你想让你的 C 应用程序使用 H2，你需要将 `h2.dll` 和 `IKVM.OpenJDK.ClassLibrary.dll` 添加到你的 C 解决方案中。
这里有一些示例代码：

---

```
using System;
using java.sql;

class Test
{
    static public void Main()
    {
        org.h2.Driver.load();
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT 'Hello World'");
        while (rs.next())
        {
            Console.WriteLine(rs.getString(1));
        }
    }
}
```
