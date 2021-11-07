## External authentication (Experimental) *外部身份验证（实验）*

External authentication allows to optionally validate user credentials externally (JAAS,LDAP,custom classes).
Is also possible to temporary assign roles to externally authenticated users.
**This feature is experimental and subject to change**


外部身份验证允许选择性地在外部验证用户凭据（JAAS、LDAP、自定义类）。
也可以将角色临时分配给外部身份验证的用户。
**此功能是实验性的，可能会发生变化**

---

Master user cannot be externally authenticated


主用户无法进行外部身份验证

---

To enable external authentication on a database execute statement `SET AUTHENTICATOR TRUE`.
This setting in persisted on the database.


要在数据库上启用外部身份验证，请执行语句“SET AUTHENTICATOR TRUE”。
此设置保留在数据库中。

---

To connect on a database by using external credentials client must append `AUTHREALM=H2` to the database URL.
`H2` is the identifier of the authentication realm (see later).


要使用外部凭据连接到数据库，客户端必须将 `AUTREALM=H2` 附加到数据库 URL。
`H2` 是身份验证领域的标识符（见下文）。

---

External authentication requires to send password to the server.
For this reason is works only on local connection or remote over ssl


外部身份验证需要将密码发送到服务器。
因此，仅适用于本地连接或通过 ssl 远程连接

---

By default external authentication is performed through JAAS login interface (configuration name is `h2`).
To configure JAAS add argument `-Djava.security.auth.login.config=jaas.conf` Here an example of [JAAS login configuration file]() content:


默认情况下，外部认证是通过 JAAS 登录界面执行的（配置名称为 `h2` ）。
要配置 JAAS 添加参数 `-Djava.security.auth.login.config=jaas.conf` 这里是 [JAAS login configuration file]() 内容的示例：

---

```
h2 {
    com.sun.security.auth.module.LdapLoginModule REQUIRED \
    userProvider="ldap://127.0.0.1:10389" authIdentity="uid={USERNAME},ou=people,dc=example,dc=com" \
    debug=true useSSL=false ;
};
```

Is it possible to specify custom authentication settings by using JVM argument `-Dh2auth.configurationFile={urlOfH2Auth.xml}`.
Here an example of h2auth.xml file content:


是否可以使用 JVM 参数 `-Dh2auth.configurationFile={urlOfH2Auth.xml}` 来指定自定义身份验证设置。
这是 h2auth.xml 文件内容的示例：

---

```xml
<h2Auth allowUserRegistration="false" createMissingRoles="true">

    <!-- realm: DUMMY authenticate users named DUMMY[0-9] with a static password -->
    <realm name="DUMMY"
    validatorClass="org.h2.security.auth.impl.FixedPasswordCredentialsValidator">
        <property name="userNamePattern" value="DUMMY[0-9]" />
        <property name="password" value="mock" />
    </realm>

    <!-- realm LDAPEXAMPLE:perform credentials validation on LDAP -->
    <realm name="LDAPEXAMPLE"
    validatorClass="org.h2.security.auth.impl.LdapCredentialsValidator">
        <property name="bindDnPattern" value="uid=%u,ou=people,dc=example,dc=com" />
        <property name="host" value="127.0.0.1" />
        <property name="port" value="10389" />
        <property name="secure" value="false" />
    </realm>

    <!-- realm JAAS: perform credentials validation by using JAAS api -->
    <realm name="JAAS"
    validatorClass="org.h2.security.auth.impl.JaasCredentialsValidator">
        <property name="appName" value="H2" />
    </realm>

    <!--Assign to each user role @{REALM} -->
    <userToRolesMapper class="org.h2.security.auth.impl.AssignRealmNameRole"/>

    <!--Assign to each user role REMOTEUSER -->
    <userToRolesMapper class="org.h2.security.auth.impl.StaticRolesMapper">
        <property name="roles" value="REMOTEUSER"/>
    </userToRolesMapper>
</h2Auth>
```

Custom credentials validators must implement the interface `org.h2.api.CredentialsValidator`


自定义凭据验证器必须实现接口 `org.h2.api.CredentialsValidator`

---

Custom criteria for role assignments must implement the interface `org.h2.api.UserToRoleMapper` 


角色分配的自定义标准必须实现接口`org.h2.api.UserToRoleMapper`

---
