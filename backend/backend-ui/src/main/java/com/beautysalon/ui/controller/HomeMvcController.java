package com.beautysalon.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeMvcController {

    @GetMapping
    public String root() {
        return "redirect:/panel";
    }

    @GetMapping( "/panel")
    public String home() {
        return "index";
    }

}
