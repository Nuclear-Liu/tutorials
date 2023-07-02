# OAuth 2 (RFC 6749)

> OAuth 2 解决问题域和场景
> 
> * 开放系统间授权
>   * 社交联合登录
>   * 开放 API 平台
> * 现代微服务安全
>   * 单页浏览 App (HTML5/JS/无状态)
>   * 无线原生 App
>   * 服务端 WebApp
>   * 微服务和 API 间调用
> * 企业内部应用认证授权(IAM/SSO)

关于授权与安全：
* [登录工程：传统 Web 应用中的身份验证技术](https://insights.thoughtworks.cn/traditional-web-app-authentication/)
* [The Simplest Guide To OAuth 2.0](https://darutk.medium.com/the-simplest-guide-to-oauth-2-0-8c71bd9a15bb)

## 定义

OAuth 2.0 用于 REST/APIs 的**代理授权**框架(delegated authorization framework)；
基于**令牌 Token** 的授权在无需暴露用户密码的情况下，使应用能够获取对用户数据的有限访问权限；
**解耦**认证和授权；
事实上的标准安全框架，支持多种应用场景。

> 令牌：仆从钥匙(Valet Key)，给应用授予有限的访问权限，让应用能够代表用户去访问用户的数据。

##### 相关角色

![role.png](role.png)

##### OAuth 2.0 术语
* 客户应用(Client Application): 

    通常是一个 Web 或者无线应用，它需要访问用户的受保护资源。
* 资源服务器(Resource Server):

    是一个 Web 站点或者 WebService API ，用户的受保护数据保存于此。
* 授权服务器(Authorized Server):

    在客户应用成功认证并获得授权之后，向客户应用颁发访问令牌 Access Token 。
* 资源拥有者(Resource Owner):

    资源的拥有人，想要分享某些资源给第三方应用。
* 客户凭证(Client Credentials):

    客户的 clientId 和密码用于认证用户。
* 令牌(Tokens):

    授权服务器在接收到客户请求后，颁发的访问令牌。
    * 令牌类型：
      * **访问令牌**(Access Token): 用于代表一个用户或服务直接去访问受保护的资源
      * 刷新令牌(Refresh Token): 用于去授权服务器获取一个新的访问令牌
      * 授权码(Authorization Code Token): 仅用于授权码授权类型，用于交换**访问令牌**和**刷新令牌**
      * Bearer Token: 不管谁拿到 Token 都可以访问资源（类似于现钞）
      * Proof of Possession(PoP) Token: 可以校验 client 是否对 Token 有明确的拥有权
* 作用域(Scopes):

    客户请求访问令牌时，由资源拥有者指定的细分权限(permission)

> **OAuth 2.0 误解**
> 
> * OAuth 并没有支持 **HTTP** 以外的协议
> * OAuth 并不是一个**认证协议**
> * OAuth 并没有定义**授权处理机制**
> * OAuth 并没有定义 **token 格式**
> * OAuth 2.0 并没有加密方法
> * OAuth 2.0 并不是**单个协议**
> * OAuth 2.0 仅是**授权框架**，仅用于**授权代理**

OAuth 本质：如何获取 token ，如何使用 token 。
OAuth 是一种在系统之间的**代理授权**(**delegation authorization**)协议。
OAuth 使用代理协议的方式解决密码共享反模式问题。
OAuth 提供了一个宽泛的协议框架，具体安全场景需要定制。

## 典型 OAuth Flow

* [rfc6749](https://datatracker.ietf.org/doc/html/rfc6749)
* [理解OAuth 2.0](https://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)

##### 1. 授权码模式 Authorization Code Grant

```text
     +----------+
     | Resource |
     |   Owner  |
     |          |
     +----------+
          ^
          |
         (B)
     +----|-----+          Client Identifier      +---------------+
     |         -+----(A)-- & Redirection URI ---->|               |
     |  User-   |                                 | Authorization |
     |  Agent  -+----(B)-- User authenticates --->|     Server    |
     |          |                                 |               |
     |         -+----(C)-- Authorization Code ---<|               |
     +-|----|---+                                 +---------------+
       |    |                                         ^      v
      (A)  (C)                                        |      |
       |    |                                         |      |
       ^    v                                         |      |
     +---------+                                      |      |
     |         |>---(D)-- Authorization Code ---------'      |
     |  Client |          & Redirection URI                  |
     |         |                                             |
     |         |<---(E)----- Access Token -------------------'
     +---------+       (w/ Optional Refresh Token)
```

##### 2. 密码模式


##### 3. 客户端模式


##### 4. 刷新令牌