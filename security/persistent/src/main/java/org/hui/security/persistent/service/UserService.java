package org.hui.security.persistent.service;

import org.hui.security.persistent.domain.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    List<User> getUserInfo();

    User login(String userName, HttpServletResponse httpServletResponse);
}
