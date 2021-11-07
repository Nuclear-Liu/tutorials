## Database Files Encryption *数据库文件加密*

The database files can be encrypted.
Three encryption algorithms are supported:


数据库文件可以加密。
持三种加密算法：

---

* "AES" - also known as Rijndael, only AES-128 is implemented.
* "XTEA" - the 32 round version.
* "FOG" - pseudo-encryption only useful for hiding data from a text editor.


* “AES” - 也称为 Rijndael ，仅实现了 AES-128。
* "XTEA" - 32轮版本。
* "FOG" - 伪加密仅用于在文本编辑器中隐藏数据。

---

To use file encryption, you need to specify the encryption algorithm (the 'cipher') and the file password (in addition to the user password) when connecting to the database.


要使用文件加密，您需要在连接到数据库时指定加密算法（“密码”）和文件密码（除了用户密码）。

---

### Creating a New Database with File Encryption *使用文件加密创建新数据库*

By default, a new database is automatically created if it does not exist yet when the [embedded]() url is used.
To create an encrypted database, connect to it as it would already exist locally using the embedded URL.


默认情况下，当使用 [embedded]() url 时，如果新数据库尚不存在，则会自动创建该数据库。
要创建加密数据库，请使用嵌入的 URL 连接到它，因为它已在本地存在。

---

### Connecting to an Encrypted Database *连接到加密的数据库*

The encryption algorithm is set in the database URL, and the file password is specified in the password field, before the user password.
A single space separates the file password and the user password; the file password itself may not contain spaces.
File passwords and user passwords are case sensitive.


加密算法在数据库 URL 中设置，文件密码在密码字段中指定，在用户密码之前。
一个**空格**分隔**文件密码**和**用户密码**；文件密码本身不能包含空格。
文件密码和用户密码**区分大小写**。
这是连接到密码加密数据库的示例：

---

```
String url = "jdbc:h2:~/test;CIPHER=AES";
String user = "sa";
String pwds = "filepwd userpwd";
conn = DriverManager.
    getConnection(url, user, pwds);
```

### Encrypting or Decrypting a Database *加密或解密数据库*

To encrypt an existing database, use the `ChangeFileEncryption` tool.
This tool can also decrypt an encrypted database, or change the file encryption key.
The tool is available from within the H2 Console in the tools section, or you can run it from the command line.
The following command line will encrypt the database `test` in the user home directory with the file password `filepwd` and the encryption algorithm AES:


要加密现有数据库，请使用 `ChangeFileEncryption` 工具。
此工具还可以解密加密的数据库，或更改文件加密密钥。
该工具可从 H2 控制台的工具部分中获得，或者您可以从命令行运行它。
以下命令行将使用文件密码 `filepwd` 和加密算法 AES 加密用户主目录中的数据库 `test` ：
---

```shell
java -cp h2*.jar org.h2.tools.ChangeFileEncryption -dir ~ -db test -cipher AES -encrypt filepwd
```
