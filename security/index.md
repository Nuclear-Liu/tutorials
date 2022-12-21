# Security

## Spring Security

### 依赖

#### Spring Boot 依赖：

```shell
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

**测试依赖：**
```shell
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 自定义登录页面

> Spring Security 模块内部的 `login.html` 使用的是 QueryMethod 参数提交方式。

* `extends WebSecurityConfigurerAdapter` 
  * `protected void configure(HttpSecurity http) throws Exception`
    * `http.formLogin().loginPage(String loginPage)` 自定义登陆页面 (默认使用 Spring Security 提供的 login.html )
    * `http.formLogin().usernameParameter(String usernameParameter)` 自定义提交验证用户名参数名称 (默认： `username`) **前后端必须严格一致，不会自动匹配转换**
    * `http.formLogin().passwordParameter(String passwordParameter)` 自定义提交验证密码参数名称 (默认： `password`) **前后端必须严格一致，不会自动匹配转换**
    * `http.formLogin().loginProcessingUrl(String loginProcessingUrl)` 自定义登录提交 url 自定义页面是必须指定页面提交 url
    * `http.formLogin().successForwardUrl(String forwardUrl)` 指定登录成功是客户端跳转地址
    * `http.formLogin().failureForwardUrl(String forwardUrl)` 指定登录失败是客户端跳转地址 `forwardUrl` 不需要允许所有人访问控制 (不能是使用 `/error` , Spring Security 中默认使用 `/error`)
    * `http.formLogin().failureUrl(String authenticationFailureUrl)` 指定登录失败返回的页面
    
    * `http.authorizeRequests().antMatchers(String... antPatterns).permitAll()` 指定 `antMatchers` 匹配器的 URL 都可以访问的 URL
    * `http.authorizeRequests().anyRequest().authenticated()` 指定任何经过身份认证的用户都可以使用 URL
    
    * `http.csrf().disable()` 允许跨域请求

```java
@Configuration
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    /**
     * custom log pag.
     *
     * @param http the {@link org.springframework.security.config.annotation.web.builders.HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.formLogin()
              .usernameParameter("u_name") // custom form post commit username
              .passwordParameter("u_passwd") // custom form post commit password
              .loginPage("/login.html") // custom login page.
              // .loginProcessingUrl("/login") // if custom <login page> must default login post url(if not will error:302), </login> is default in spring security UsernamePasswordAuthenticationFilter.java.
              .loginProcessingUrl("/custom_login") // custom login url
              .successForwardUrl("/index"); // only use path in controller, can not use <index.html>

      http.authorizeRequests()
              .antMatchers("/login.html").permitAll() // allow access '/login.html'
              .anyRequest().authenticated(); // all other not allowed

      http.csrf().disable(); // allow cors(cross-origin resource sharing)
    }
}
```

> `@RestController` - `@Controller`
> 
> * `@RestController` 返回为 RestFul 风格数据，不能直接返回 static 下页面数据
> * `@Controller` 可以直接返回 `static` 下页面数据，使用 `redirect:` 连接的页面信息返回（例如： `"redirect:/index.html"` ）。
