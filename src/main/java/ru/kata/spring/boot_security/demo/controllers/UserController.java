package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {

    @GetMapping(value = "/admin/page")
    public String getAdminPage() {
        return "admin";
    }

    @GetMapping(value = "/user/page")
    public String getUserPage() {
        return "user";
    }
}
