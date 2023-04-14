## Password Hash *密码哈希*

Sometimes the database password needs to be stored in a configuration file (for example in the `web.xml` file).
In addition to connecting with the plain text password, this database supports connecting with the password hash.
This means that only the hash of the password (and not the plain text password) needs to be stored in the configuration file.
This will only protect others from reading or re-constructing the plain text password (even if they have access to the configuration file); it does not protect others from accessing the database using the password hash.


有时数据库密码需要存储在配置文件中（例如在 `web.xml` 文件中）。
除了使用明文密码连接外，该数据库还支持使用密码哈希连接。
这意味着只有密码的哈希值（而不是纯文本密码）需要存储在配置文件中。
这只会保护其他人无法读取或重新构造纯文本密码（即使他们可以访问配置文件）；它不会保护其他人使用密码哈希访问数据库。

---

To connect using the password hash instead of plain text password, append `;PASSWORD_HASH=TRUE` to the database URL, and replace the password with the password hash.
To calculate the password hash from a plain text password, run the following command within the H2 Console tool: `@password_hash <upperCaseUserName> <password>`.
As an example, if the user name is `sa` and the password is `test`, run the command `@password_hash SA test`.
Then use the resulting password hash as you would use the plain text password.
When using an encrypted database, then the user password and file password need to be hashed separately.
To calculate the hash of the file password, run: `@password_hash file <filePassword>`.


要使用密码哈希而不是纯文本密码进行连接，请将 `;PASSWORD_HASH=TRUE` 附加到数据库 URL，并将密码替换为密码哈希。
要从纯文本密码计算密码哈希，请在 H2 控制台工具中运行以下命令： `@password_hash <upperCaseUserName> <password>` 。
例如，如果用户名为 `sa` ，密码为 `test` ，则运行命令 `@password_hash SA test` 。
然后像使用纯文本密码一样使用生成的密码哈希。
当使用加密数据库时，那么用户密码和文件密码需要单独散列。
要计算文件密码的哈希值，请运行： `@password_hash file <filePassword>` 。

---
