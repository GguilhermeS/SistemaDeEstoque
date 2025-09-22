package com.br.SistemaDeEstoques.controller;

import org.springframework.stereotype.Controller;

import com.br.SistemaDeEstoques.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
