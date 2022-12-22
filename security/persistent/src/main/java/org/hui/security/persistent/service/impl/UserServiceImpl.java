package org.hui.security.persistent.service.impl;

import org.hui.security.persistent.domain.User;
import org.hui.security.persistent.repository.UserRepository;
import org.hui.security.persistent.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserInfo() {
        List<User> users = userRepository.findAll();

        return users;
    }

}
