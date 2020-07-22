package com.demo.web;

import com.demo.po.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String signupPage() {

        return "signup";
    }

    @PostMapping
    public String signup(User user, HttpSession session) {
        userService.saveUser(user);
        user.setPassword(null);
        session.setAttribute("user", user);
        return "welcome";
    }
}
