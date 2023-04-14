package org.hui.security.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
    @PostMapping("/index") // use @GetMapping will fail.
    public String index() {
        return "redirect:/index.html";
    }

    @PostMapping("toError")
    public String toError() {
        return "redirect:/error.html";
    }
}
