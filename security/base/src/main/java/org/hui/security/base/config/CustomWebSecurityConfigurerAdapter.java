package org.hui.security.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * custom login page.
 */
@Configuration
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
    /**
     * custom log pag.
     *
     * @param http the {@link HttpSecurity} to modify
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
                .successForwardUrl("/index") // custom authorization successful client request url, only use path in controller, can not use <index.html>
                .failureForwardUrl("/toError") // custom authorization failure client request url, can not use '/error' path, has been used in spring security.
                /*.failureUrl("/error.html")*/; // custom authorization failure return error pag

        http.authorizeRequests()
                .antMatchers("/login.html", /*"/toError",*/ "/error.html").permitAll() // allow access '/login.html' '/toError' '/error.html'
                .anyRequest().authenticated(); // all other not allowed

        http.csrf().disable(); // allow cors(cross-origin resource sharing)
    }
}
