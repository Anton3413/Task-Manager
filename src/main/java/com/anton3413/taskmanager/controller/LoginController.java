package com.anton3413.taskmanager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    String displayLoginPage(){
        return "login";
    }

    @GetMapping("/registration")
    String displayRegistrationPage(){
        return "registration";
    }
}
