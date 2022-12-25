package org.hui.security.persistent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * custom login page.
 */
@Configuration
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/user/login");
    }

    /**
     * custom log pag.
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // allow cors(cross-origin resource sharing)

        http.authorizeRequests()
                // .antMatchers("/user/login").permitAll() // 对自定义过滤器不起作用
                .anyRequest().authenticated(); // 对所有路径进行控制

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 禁用 session

        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
    }
}
