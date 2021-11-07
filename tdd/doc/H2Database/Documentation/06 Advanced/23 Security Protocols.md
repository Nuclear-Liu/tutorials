## Security Protocols *安全协议*

The following paragraphs document the security protocols used in this database.
These descriptions are very technical and only intended for security experts that already know the underlying security primitives.


以下段落记录了该数据库中使用的安全协议。
这些描述非常技术性，仅适用于已经了解底层安全原语的安全专家。

---

### User Password Encryption *用户密码加密*

When a user tries to connect to a database, the combination of user name, @, and password are hashed using SHA-256, and this hash value is transmitted to the database.
This step does not protect against an attacker that re-uses the value if he is able to listen to the (unencrypted) transmission between the client and the server.
But, the passwords are never transmitted as plain text, even when using an unencrypted connection between client and server.
That means if a user reuses the same password for different things, this password is still protected up to some point.
See also 'RFC 2617 - HTTP Authentication: Basic and Digest Access Authentication' for more information.


当用户尝试连接到数据库时，用户名、@ 和密码的组合使用 SHA-256 进行散列，并将此散列值传输到数据库。
如果攻击者能够侦听客户端和服务器之间的（未加密）传输，则此步骤无法防止攻击者重用该值。
但是，即使在客户端和服务器之间使用未加密的连接时，密码也不会以纯文本形式传输。
这意味着如果用户对不同的事情重复使用相同的密码，这个密码在某些时候仍然受到保护。
有关更多信息，另请参阅“RFC 2617 - HTTP 身份验证：基本和摘要式访问身份验证”。

---

When a new database or user is created, a new random salt value is generated.
The size of the salt is 64 bits. Using the random salt reduces the risk of an attacker pre-calculating hash values for many different (commonly used) passwords.


创建新数据库或用户时，会生成新的随机盐值。
盐的大小是 64 位。使用随机盐可以降低攻击者为许多不同（常用）密码预先计算哈希值的风险。

---

The combination of user-password hash value (see above) and salt is hashed using SHA-256.
The resulting value is stored in the database.
When a user tries to connect to the database, the database combines user-password hash value with the stored salt value and calculates the hash value.
Other products use multiple iterations (hash the hash value again and again), but this is not done in this product to reduce the risk of denial of service attacks (where the attacker tries to connect with bogus passwords, and the server spends a lot of time calculating the hash value for each password).
The reasoning is: if the attacker has access to the hashed passwords, he also has access to the data in plain text, and therefore does not need the password any more.
If the data is protected by storing it on another computer and only accessible remotely, then the iteration count is not required at all.


用户密码哈希值（见上文）和盐的组合使用 SHA-256 进行哈希处理。
结果值存储在数据库中。
当用户尝试连接到数据库时，数据库将用户密码哈希值与存储的盐值结合起来并计算哈希值。
其他产品使用多次迭代（一次又一次地对哈希值进行散列），但本产品并没有这样做以降低拒绝服务攻击的风险（攻击者尝试使用伪造密码进行连接，服务器花费大量计算每个密码的哈希值的时间）。
原因是：如果攻击者可以访问散列密码，他也可以访问纯文本数据，因此不再需要密码。
如果数据通过将其存储在另一台计算机上而受到保护并且只能远程访问，则根本不需要迭代计数。

---

### File Encryption *文件加密*

The database files can be encrypted using the AES-128 algorithm.


可以使用 AES-128 算法对数据库文件进行加密。

---

When a user tries to connect to an encrypted database, the combination of `file@` and the file password is hashed using SHA-256.
This hash value is transmitted to the server.


当用户尝试连接到加密数据库时，`file@` 和文件密码的组合使用 SHA-256 进行散列。
这个散列值被传输到服务器。

---

When a new database file is created, a new cryptographically secure random salt value is generated.
The size of the salt is 64 bits.
The combination of the file password hash and the salt value is hashed 1024 times using SHA-256.
The reason for the iteration is to make it harder for an attacker to calculate hash values for common passwords.


创建新的数据库文件时，会生成新的加密安全随机盐值。
盐的大小是 64 位。
文件密码哈希和盐值的组合使用 SHA-256 进行了 1024 次哈希处理。
迭代的原因是让攻击者更难计算常见密码的哈希值。

---

The resulting hash value is used as the key for the block cipher algorithm.
Then, an initialization vector (IV) key is calculated by hashing the key again using SHA-256.
This is to make sure the IV is unknown to the attacker.
The reason for using a secret IV is to protect against watermark attacks.


得到的散列值用作分组密码算法的密钥。
-然后，通过使用 SHA-256 再次散列密钥来计算初始化向量 (IV) 密钥。
这是为了确保攻击者不知道 IV。
使用秘密 IV 的原因是为了防止水印攻击。

---

Before saving a block of data (each block is 8 bytes long), the following operations are executed: first, the IV is calculated by encrypting the block number with the IV key (using the same block cipher algorithm).
This IV is combined with the plain text using XOR.
The resulting data is encrypted using the AES-128 algorithm.


在保存一个数据块（每个块长8个字节）之前，执行以下操作：首先，通过用IV密钥（使用相同的分组密码算法）加密块号来计算IV。
这个 IV 与使用 XOR 的纯文本结合。
生成的数据使用 AES-128 算法进行加密。

---

When decrypting, the operation is done in reverse.
First, the block is decrypted using the key, and then the IV is calculated combined with the decrypted text using XOR.


解密时，操作反向进行。
首先，使用密钥对块进行解密，然后使用 XOR 结合解密的文本计算 IV。

---

Therefore, the block cipher mode of operation is CBC (cipher-block chaining), but each chain is only one block long.
The advantage over the ECB (electronic codebook) mode is that patterns in the data are not revealed, and the advantage over multi block CBC is that flipped cipher text bits are not propagated to flipped plaintext bits in the next block.


因此，块密码的操作方式是CBC（密码块链接），但每条链只有一个块长。
与 ECB（电子码本）模式相比的优势在于不会显示数据中的模式，而与多块 CBC 相比的优势在于翻转的密文位不会传播到下一个块中翻转的明文位。

---

Database encryption is meant for securing the database while it is not in use (stolen laptop and so on).
It is not meant for cases where the attacker has access to files while the database is in use.
When he has write access, he can for example replace pieces of files with pieces of older versions and manipulate data like this.


数据库加密旨在保护未使用的数据库（被盗的笔记本电脑等）。
它不适用于攻击者在使用数据库时访问文件的情况。
当他有写访问权限时，他可以例如用旧版本的片段替换文件片段并像这样操作数据。

---

File encryption slows down the performance of the database engine.
Compared to unencrypted mode, database operations take about 2.5 times longer using AES (embedded mode).


文件加密会降低数据库引擎的性能。
与未加密模式相比，使用 AES（嵌入式模式）的数据库操作需要大约 2.5 倍的时间。

---

### Wrong Password / User Name Delay *密码错误 / 用户名延迟*

To protect against remote brute force password attacks, the delay after each unsuccessful login gets double as long.
Use the system properties `h2.delayWrongPasswordMin` and `h2.delayWrongPasswordMax` to change the minimum (the default is 250 milliseconds) or maximum delay (the default is 4000 milliseconds, or 4 seconds).
The delay only applies for those using the wrong password.
Normally there is no delay for a user that knows the correct password, with one exception: after using the wrong password, there is a delay of up to (randomly distributed) the same delay as for a wrong password.
This is to protect against parallel brute force attacks, so that an attacker needs to wait for the whole delay.
Delays are synchronized.
This is also required to protect against parallel attacks.


为了防止远程暴力密码攻击，每次登录失败后的延迟时间都会加倍。
使用系统属性 `h2.delayWrongPasswordMin` 和 `h2.delayWrongPasswordMax` 更改最小（默认为 250 毫秒）或最大延迟（默认为 4000 毫秒，或 4 秒）。
延迟仅适用于使用错误密码的人。
通常，知道正确密码的用户没有延迟，但有一个例外：在使用错误密码后，延迟最多（随机分布）与错误密码相同。
这是为了防止并行蛮力攻击，因此攻击者需要等待整个延迟。
延迟是同步的。
这也是防止并行攻击所必需的。

---

There is only one exception message for both wrong user and for wrong password, to make it harder to get the list of user names.
It is not possible from the stack trace to see if the user name was wrong or the password.


对于错误的用户和错误的密码，只有一个异常消息，这使得获取用户名列表变得更加困难。
无法从堆栈跟踪中查看用户名或密码是否错误。

---

### HTTPS Connections *HTTPS 连接*

The web server supports HTTP and HTTPS connections using `SSLServerSocket`.
There is a default self-certified certificate to support an easy starting point, but custom certificates are supported as well.


Web 服务器支持使用 `SSLServerSocket` 的 HTTP 和 HTTPS 连接。
有一个默认的自认证证书来支持一个简单的起点，但也支持自定义证书。

---
