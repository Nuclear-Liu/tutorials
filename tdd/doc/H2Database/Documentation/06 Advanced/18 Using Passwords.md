## Using Passwords *使用密码*

### Using Secure Passwords *使用安全密码*

Remember that weak passwords can be broken regardless of the encryption and security protocols.
Don't use passwords that can be found in a dictionary.
Appending numbers does not make passwords secure.
A way to create good passwords that can be remembered is: 
take the first letters of a sentence, use upper and lower case characters, and creatively include special characters (but it's more important to use a long password than to use special characters).
Example:


请记住，无论加密和安全协议如何，弱密码都可能被破解。
不要使用可以在字典中找到的密码。
---

`i'sE2rtPiUKtT` from the sentence `it's easy to remember this password if you know the trick`.

### Passwords: Using Char Arrays instead of Strings *密码：使用字符数组而不是字符串*

Java strings are immutable objects and cannot be safely 'destroyed' by the application.
After creating a string, it will remain in the main memory of the computer at least until it is garbage collected.
The garbage collection cannot be controlled by the application, and even if it is garbage collected the data may still remain in memory.
It might also be possible that the part of memory containing the password is swapped to disk (if not enough main memory is available), which is a problem if the attacker has access to the swap file of the operating system.


Java 字符串是不可变对象，不能被应用程序安全地 'destroyed' 。
创建字符串后，它将保留在计算机的主内存中，至少直到它被垃圾收集为止。
垃圾收集无法由应用程序控制，即使是垃圾收集，数据也可能仍保留在内存中。
也有可能将包含密码的内存部分交换到磁盘（如果没有足够的主内存可用），如果攻击者可以访问操作系统的交换文件，这将是一个问题。

---

It is a good idea to use char arrays instead of strings for passwords.
Char arrays can be cleared (filled with zeros) after use, and therefore the password will not be stored in the swap file.


使用字符数组而不是字符串作为密码是个好主意。
使用后可以清除字符数组（用零填充），因此密码不会存储在交换文件中。

---

This database supports using char arrays instead of string to pass user and file passwords.
The following code can be used to do that:


该数据库支持使用字符数组而不是字符串来传递用户和文件密码。
可以使用以下代码来做到这一点：

---

```java
import java.sql.*;
import java.util.*;
public class Test {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:~/test";
        Properties prop = new Properties();
        prop.setProperty("user", "sa");
        System.out.print("Password?");
        char[] password = System.console().readPassword();
        prop.put("password", password);
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, prop);
        } finally {
            Arrays.fill(password, (char) 0);
        }
        conn.close();
    }
}
```

When using Swing, use `javax.swing.JPasswordField`.


使用 Swing 时，请使用 `javax.swing.JPasswordField`。

---

### Passing the User Name and/or Password in the URL *在 URL 中传递用户名和/或密码*

Instead of passing the user name as a separate parameter as in `Connection conn = DriverManager. getConnection("jdbc:h2:~/test", "sa", "123");` the user name (and/or password) can be supplied in the URL itself: `Connection conn = DriverManager. getConnection("jdbc:h2:~/test;USER=sa;PASSWORD=123");`
The settings in the URL override the settings passed as a separate parameter.


而不是像在`Connection conn = DriverManager 中那样将用户名作为单独的参数传递。 getConnection("jdbc:h2:~test", "sa", "123");` 用户名（和密码）可以在 URL 本身中提供：`Connection conn = DriverManager. getConnection("jdbc:h2:~test;USER=sa;PASSWORD=123");`
URL 中的设置会覆盖作为单独参数传递的设置。

---
