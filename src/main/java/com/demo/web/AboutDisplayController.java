package com.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutDisplayController {

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
