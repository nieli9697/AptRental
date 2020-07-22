package com.demo.web;

import com.demo.po.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class UserLoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping("/userLogin")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes) {
        User user = userService.checkUser2(username, password);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return "welcome";
        }
        else {
            attributes.addFlashAttribute("message", "Incorrect username and/or password!");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

}
