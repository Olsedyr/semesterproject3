package org.example.beerMachine.controller;

import org.example.beerMachine.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        Users ProductionManager = new Users("admin", "admin");
        return "GUI/LoginPage";

    }
}