# Tutorials


My Tutorials


## Make Software

* Person Network Disk
* Map Export Picture

## Framework


* [EasyMock](./tdd/easymock/Easy%20Mock/Overview.md)
* [Lombok](./tdd/lombok/Lombok/Overview.md)
* [The Checker Framework](./tdd/checkerframework/Checker%20Framework/Overview.md)


---

[Objenesis](http://objenesis.org/)

# Excellent Literature


* [How to Write an Equality Method in Java](./tdd/lombok/Lombok/How%20to%20Write%20an%20Equality%20Method%20in%20Java.md)
* [Understanding Java Records From Java 16](./tdd/lombok/Lombok/Understanding%20Java%20Records%20From%20Java%2016.md)
* [Logging exceptions in Java](https://www.loggly.com/blog/logging-exceptions-in-java/)


* [用户系统设计:三户模型&三层身份模型](https://www.biaodianfu.com/customer-user-account-model.html)
* [The Tripartite Identity Pattern](http://habitatchronicles.com/2008/10/the-tripartite-identity-pattern/)
* [哈希一致性算法](https://zhuanlan.zhihu.com/p/129049724#:~:text=%E4%B8%80%E8%87%B4%E6%80%A7hash%E7%AE%97%E6%B3%95%E6%AD%A3%E6%98%AF%E4%B8%BA%E4%BA%86%E8%A7%A3%E5%86%B3%E6%AD%A4%E7%B1%BB%E9%97%AE%E9%A2%98%E7%9A%84%E6%96%B9%E6%B3%95%EF%BC%8C%E5%AE%83%E5%8F%AF%E4%BB%A5%E4%BF%9D%E8%AF%81%E5%BD%93%E6%9C%BA%E5%99%A8%E5%A2%9E%E5%8A%A0%E6%88%96%E8%80%85%E5%87%8F%E5%B0%91%E6%97%B6%EF%BC%8C%E8%8A%82%E7%82%B9%E4%B9%8B%E9%97%B4%E7%9A%84%E6%95%B0%E6%8D%AE%E8%BF%81%E7%A7%BB%E5%8F%AA%E9%99%90%E4%BA%8E%E4%B8%A4%E4%B8%AA%E8%8A%82%E7%82%B9%E4%B9%8B%E9%97%B4%EF%BC%8C%E4%B8%8D%E4%BC%9A%E9%80%A0%E6%88%90%E5%85%A8%E5%B1%80%E7%9A%84%E7%BD%91%E7%BB%9C%E9%97%AE%E9%A2%98%E3%80%82%201.,%E7%8E%AF%E5%BD%A2Hash%E7%A9%BA%E9%97%B4%20%E6%8C%89%E7%85%A7%E5%B8%B8%E7%94%A8%E7%9A%84hash%E7%AE%97%E6%B3%95%E6%9D%A5%E5%B0%86%E5%AF%B9%E5%BA%94%E7%9A%84key%E5%93%88%E5%B8%8C%E5%88%B0%E4%B8%80%E4%B8%AA%E5%85%B7%E6%9C%892%5E32%E6%AC%A1%E6%96%B9%E4%B8%AA%E6%A1%B6%E7%9A%84%E7%A9%BA%E9%97%B4%E4%B8%AD%EF%BC%8C%E5%8D%B30~%20%282%5E32%29-1%E7%9A%84%E6%95%B0%E5%AD%97%E7%A9%BA%E9%97%B4%E4%B8%AD%E3%80%82)
* [Spark-Streaming反压（back-pressure）](https://zhuanlan.zhihu.com/p/45954932)
* [实时流处理系统反压机制（BackPressure）综述](https://zhuanlan.zhihu.com/p/38157397)


## 高可用，高并发

* [基于四层网络协议的 LVS 解决方案](./Note/LVS/LVS%20高可用%20高并发%20负载均衡.md)

## 疑问点


* 对于生命周期的概念，是否对应映射到容器，使得对象数据与生命周期隔离会不会更合适；
* 枚举引入二进制


## 微服务设计原则

> **隔离系统变化点**；

* 高内聚，低耦合；
* 高度自治：开发、测试、构建、部署、运行、发布（无状态）；
* 以业务为中心；
* 弹性设计（容错、隔离、降级）；
* 自动化（CI、CD）；
* 粒度把控（按照业务逻辑来）；

> **AKF**
> 
> `x` 轴：水平复制；
> `y` 轴：业务拆分（拆分成更小的服务）；
> `z` 轴：数据分片（按照业务类型，地区等；


> **QPS(Queries Per Second, 每秒查询率)** 
> 
> (2000/300)
> 
> 服务器每秒处理查询的数量；
> 
> **TPS(Transactions Per Second, 每秒事务率)**
> 
> 服务器每秒处理事务的数量；


### 接口设计原则

* Restful 风格（Http)

    重在**资源**；
    * 协议 超文本传输协议
    * 域名 `api.domain_name.com`
    * 版本 `v1`
    * 路径 建议使用名词
    * 动作
        * `post` 新增
        * `put` 修改（传输修改记录的全量数据）
        * `patch` 修改（仅传输修改的具体部分）
        * `delete` 删除
        * `get` 查询

### 接口安全

* CIA：
    * 保密性（脱敏）
    * 完整性（防止篡改）
    * 可用性

* 数据层面：防止 SQL 注入
    * 过滤（`jsoup` 框架）
    * `spring-htmlUtils` 预防 `xss`
    * 人机交互、token 防止 `csrf` 攻击

* 数据权限控制（水平越权、垂直越权）

> MyBatis `$` 直接拼接 `#` 

> `xss` 攻击
> 
> 提交内容含有 `JavaScript` 脚本；
> 
> `csrf` 跨站伪装请求
> 
> **`xss` vs `csrf`**
> 
> `xss` 在正常请求中执行了恶意代码，用户数据没有过滤、转义；
> `csrf` 冒充登录信息，没有防范不信任的调用；

> `Referer` 
> 
> HTTP 请求头的一部分；
> 表示来源；用于防盗链、防止恶意请求；

### 日志

* dao 层异常，不用打日志， `catch` 抛出；
* service 层打印异常日志，覆盖详细信息；
* controller 层捕获异常，包装成状态码；
* dto 抽象到公共层(common, 二方库)；

## [有状态 VS 无状态](https://www.redhat.com/zh/topics/cloud-native-apps/stateful-vs-stateless?msclkid=d4a8b346d07d11eca80a98acca934298)

## Git

`git checkout tags/<tag> -b <branch>`

` git svn clone https://svn.apache.org/repos/asf/tomcat/trunk/ ./tomcat -s --prefix=svn/`

## My IDEA Plugins


* ~~Infinitest~~
* ExcelReader
* GitToolBox
* Grep Console
* Ideolog
* JenkinsControl
* MapStruct Support
* Maven Helper
* PlantUML integration
* Rainbow Brackets
* String Manipulation
* Translation
* UnitVisualizer


IDEA Keymap

| HotKey                   | -                                     |
|:-------------------------|---------------------------------------|
| `ctrl` + `ctrl`          | Run Anything                          |
| `ctrl` + <code>`</code>  | VCS Operations                        |
| `ctrl` + `shift` + `F10` | Run context configuration from editor |
| `ctrl` + `shift` + `r`   | Run Maven Goal                        |
| `ctrl` + `F12`           | Inherited members                     |
| `ctrl` + `I`             | Anonymous Classes                     |
| `ctrl` + `shift` + `F12` | Hide All Windows                      |
| `ctrl` + `alt` + `L`     | Reformat Code                         |
| `ctrl` + `h`             | view interface implements             |
