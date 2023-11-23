package org.example.beerMachine.controller;

import org.example.beerMachine.model.Users;
import org.example.beerMachine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path="api/users")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from frontend
public class AuthController {
    /*
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Users user) {
        Users currentUser = new Users();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(currentUser.getUsername(), currentUser.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // You can generate a JWT token here and return it to the client
        /*String jwtToken = "your_generated_jwt_token";

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken));



        String jwtToken = "your_generated_jwt_token";

        return ResponseEntity.ok(jwtToken);
    }


     */

}
