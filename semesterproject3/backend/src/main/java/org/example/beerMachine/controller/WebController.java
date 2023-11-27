package org.example.beerMachine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String showLoginPage() {
        return "GUI/LoginPage";
    }
}
