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

**OAuth 2 的核心就是向第三方应用颁发令牌**。

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

> **授权渠道(channels)**
> 
> ![img.png](img.png)
> * **前端渠道**: 没有资源服务器(RS)参与的流程【授权过程？】
> * **后端渠道**: 没有资源拥有者(RO)参与的流程【使用权限过程？】

> **客户应用类型**
> 
> * **公开**(仅存**客户标识**)：重要的客户信息不能够存储在应用中
>   * 单页应用 SPA (Angular, React, Vue)
>   * 原生 App (iOS, Android)
> * **私密**(**客户凭证**)
>   * Web 服务器应用 (.NET, Java)
>   * 服务/API (M2M, 机器对机器)

RFC 7749 标准定义了获得令牌的四种授权方式：**授权码码模式**、**简化模式**、**密码模式**、**客户端凭证模式**。

不管哪一种授权模式，第三方应用申请令牌之前，都必须先到系统备案，说明自己的身份，然后拿到两个**身份识别码**：
* **客户端 ID(client ID)**
* **客户密钥(client secret)**

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

1. 通过前端渠道**客户**获取授权码；
2. 通过后端渠道，**客户**使用 `authorization code` 去交换 `Access Token` 和可选的 `Refresh Token`
3. 假定**资源拥有者**和**客户**在不同的设备上
4. 最安全的流程，因为令牌不会传递给 user-agent

最常用的流程，安全性最高。适用于有后端的 Web 应用。
**授权码**通过前端传送，**令牌**则是存储在后端。所有与资源服务器的通信都在后端完成。
可以避免令牌泄露。

> 案例：A 网站提供一个链接，用户点击后就会跳转到 B 网站，授权用户数据给 A 网站使用。
> 
> `https://b.com/oauth/authorize?response_type=code&client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=read`
> 
> 
> 1. A 网站提供跳转 B 网站链接，申请授权： `http://localhost:8080/oauth/authorize?client_id=clientapp&response_type=code&scope=read_userinfo&redirect_uri=http://localhost:9001/callback`
>     * `client_id`: 请求授权者 ID (B用来识别申请者)
>     * `response_type`: 表示要求返回**授权码**
>     * `scope`: 表示请求授权范围
>     * `redirect_uri`: 接受或拒绝请求后跳转地址
> 2. 跳转到 B 网站， B 网站要求**用户登录**，然后询问用户是否同意授予 A 网站权限。
>     * 如果给予授权，这时 B 网站跳转回 `redirect_uri` 参数指定的地址： `http://localhost:9001/callback?code=dZmW8d`
>         * `code`: **授权码**
>     * 如果不予授权，这是 B 网站跳转回 `redirect_uri` 参数指定的地址： `http://localhost:9001/callback?error=access_denied&error_description=User%20denied%20access`
> 3. A 网站成功拿到**授权码**后，就可以在后端。向 B 网站请求令牌： `http://localhost:8080/oauth/token?code=T0blJ6&grant_type=authorization_code&scope=read_userinfo&redirect_uri=http://localhost:9001/callback`
>     * 请求头
>         * `Content-Type`:`application/x-www-form-urlencoded`
>         * `Authorization`: `Basic Y2xpZW50YXBwOjExMjIzMw==` : 根据 `Username` 与 `Password` 生成
>     * 请求参数：
>         * `code`: 授权码（上一步获取到的）
>         * `grant_type`: 授权方式--authorization_code(授权码)
>         * `scope`: 表示请求授权范围
>         * `redirect_uri`: 令牌颁发后的回调网站
> 4. B 收到请求后，颁发令牌：向 `redirect_uri` 发送 JSON 数据其中包含 `access_token` 即**令牌**。
>     ```json
>     {
>         "access_token": "a903123f-6888-4ca5-b46c-554b86bed15e",
>         "token_type": "bearer",
>         "expires_in": 43199,
>         "scope": "read_userinfo"
>     }
>     ```
> 5. 对于资源的访问过程将 `access_token` 令牌添加到头部 `Authorization` 参数中进行访问: `Authorization`: `Bearer a903123f-6888-4ca5-b46c-554b86bed15e`

##### 2. 简化模式 Implicit

> 适合单页应用 

```text
     +----------+
     | Resource |
     |  Owner   |
     |          |
     +----------+
          ^
          |
         (B)
     +----|-----+          Client Identifier     +---------------+
     |         -+----(A)-- & Redirection URI --->|               |
     |  User-   |                                | Authorization |
     |  Agent  -|----(B)-- User authenticates -->|     Server    |
     |          |                                |               |
     |          |<---(C)--- Redirection URI ----<|               |
     |          |          with Access Token     +---------------+
     |          |            in Fragment
     |          |                                +---------------+
     |          |----(D)--- Redirection URI ---->|   Web-Hosted  |
     |          |          without Fragment      |     Client    |
     |          |                                |    Resource   |
     |     (F)  |<---(E)------- Script ---------<|               |
     |          |                                +---------------+
     +-|--------+
       |    |
      (A)  (G) Access Token
       |    |
       ^    v
     +---------+
     |         |
     |  Client |
     |         |
     +---------+
```

1. 适用于**公开的**浏览器单页应用
2. `Access Token` 直接从授权服务器返回（只有前端渠道）
3. 不支持 `Refresh Token`
4. 假定资源所有者和公开客户应用在同一个设备上
5. 最容易受安全攻击

> 适用于有后端的 Web 应用，授权码通过前端传送，令牌则是存储在后端

> 案例：
> 
> 1. A 网站提供一个链接，要求用户跳转到 B 网站，授权用户数据给 A 网站使用。

##### 3. 密码模式 Resource Owner Credentials

```text
     +----------+
     | Resource |
     |  Owner   |
     |          |
     +----------+
          v
          |    Resource Owner
         (A) Password Credentials
          |
          v
     +---------+                                  +---------------+
     |         |>--(B)---- Resource Owner ------->|               |
     |         |         Password Credentials     | Authorization |
     | Client  |                                  |     Server    |
     |         |<--(C)---- Access Token ---------<|               |
     |         |    (w/ Optional Refresh Token)   |               |
     +---------+                                  +---------------+
```

1. 使用用户名/密码登录的应用，例如桌面 App
2. 使用用户名/密码作为授权方式从授权服务器上获取 Access Token
3. 一般不支持 Refresh Token
4. 假定资源拥有者和公开客户端在相同的设备上

##### 4. 客户端凭证模式 Client Credentials

> 适用于服务与服务之间

```text
     +---------+                                  +---------------+
     |         |                                  |               |
     |         |>--(A)- Client Authentication --->| Authorization |
     | Client  |                                  |     Server    |
     |         |<--(B)---- Access Token ---------<|               |
     |         |                                  |               |
     +---------+                                  +---------------+
```

1. 适用于服务期间通信场景，**机密客户**代表它自己或者一个用户
2. 只有后端渠道，使用**客户**凭证获取一个 `Access Token`
3. 因为**客户**凭证可以使用对称或非对称加密，该方式支持共享密钥或者证书

##### 5. 刷新令牌

```text
  +--------+                                           +---------------+
  |        |--(A)------- Authorization Grant --------->|               |
  |        |                                           |               |
  |        |<-(B)----------- Access Token -------------|               |
  |        |               & Refresh Token             |               |
  |        |                                           |               |
  |        |                            +----------+   |               |
  |        |--(C)---- Access Token ---->|          |   |               |
  |        |                            |          |   |               |
  |        |<-(D)- Protected Resource --| Resource |   | Authorization |
  | Client |                            |  Server  |   |     Server    |
  |        |--(E)---- Access Token ---->|          |   |               |
  |        |                            |          |   |               |
  |        |<-(F)- Invalid Token Error -|          |   |               |
  |        |                            +----------+   |               |
  |        |                                           |               |
  |        |--(G)----------- Refresh Token ----------->|               |
  |        |                                           |               |
  |        |<-(H)----------- Access Token -------------|               |
  +--------+           & Optional Refresh Token        +---------------+
```

#### 授权类型选择

![OAuth2 Flow](./OAuth2-Flow.png)

#### 典型授权服务器组成

* 授权(authorize)端点
* Token 端点
* 校验(introspect)端点
* 吊销(revoke)端点

![](./oauth2.png)

#### Spring Security OAuth 2 架构

[Spring Security OAuth 2 架构参考](http://terasolunaorg.github.io/guideline/5.3.0.RELEASE/en/Security/OAuth.html)

![OAuth2Architecture](./OAuth2Architecture.png)

## 实验

1. 授权码模式 Authorization Code Grant
