package org.hui.security.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * String username,
         * String password,
         * Collection<? extends GrantedAuthority> authorities
         */
        UserDetails userDetails = new User(
                "admin",
                passwordEncoder.encode("123456"),
                AuthorityUtils.commaSeparatedStringToAuthorityList(""));

        LOGGER.info("username: {}", username);

        return userDetails;
    }
}
