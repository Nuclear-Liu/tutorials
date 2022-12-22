package org.hui.security.persistent.config;

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
                .loginProcessingUrl("/custom_login") // custom login url
                .successHandler(new CustomAuthenticationSuccessHandler("http://192.168.99.57/index.html"))
                .failureHandler(new CustomAuthenticationFailureHandler("http://192.168.99.57/error.html"));

        http.authorizeRequests()
                .antMatchers("/login.html", "/user/getUserInfo", "/error.html").permitAll() // allow access '/login.html' '/toError' '/error.html'
                .anyRequest().authenticated(); // all other not allowed

        http.csrf().disable(); // allow cors(cross-origin resource sharing)
    }
}
