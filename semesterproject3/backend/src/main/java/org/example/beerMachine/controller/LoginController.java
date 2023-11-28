package org.example.beerMachine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@CrossOrigin(origins = {"http://localhost:3000"})
public class LoginController {


    @GetMapping("/login")
    public String showLoginPage() {
        return "GUI/LoginPage";
    }

}

