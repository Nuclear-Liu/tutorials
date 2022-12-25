package org.hui.security.persistent.controller;

import org.hui.security.persistent.common.Result;
import org.hui.security.persistent.domain.User;
import org.hui.security.persistent.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/getUserInfo")
    public Result<List<User>> getUserInfo() {
        return Result.ok(userService.getUserInfo());
    }

    @PostMapping("/login")
    public Result<User> login(@RequestParam("user_name") String userName, HttpServletResponse httpServletResponse) {
        return Result.ok(userService.login(userName, httpServletResponse));
    }
}
