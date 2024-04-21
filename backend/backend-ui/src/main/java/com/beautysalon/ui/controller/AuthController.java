package com.beautysalon.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {


    @GetMapping({"/panel/logout"})
    public String logout() {
        return "redirect:/panel/login";
    }

}
