package org.example.beerMachine.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.beerMachine.model.Users;
import org.example.beerMachine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequestMapping(path="api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Step 1: Retrieve the user from the database based on the provided username
        String username = loginRequest.getUsername();
        Users user = userRepository.FindUserBYUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Step 2: Verify if the password provided in the request matches the stored password
        String providedPassword = loginRequest.getPassword();
        if (!passwordEncoder.matches(providedPassword, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // Step 3: Generate a token (you may use a library like JWT for this)
        String token = generateToken(user);

        // Step 4: Return an appropriate response
        return ResponseEntity.ok().body(token);

    }

    private String generateToken(Users user) {
        // Set the expiration time for the token (e.g., 1 hour)
        long expirationMillis = System.currentTimeMillis() + 3600000; // 1 hour

        // Generate the token using the JWT library
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationMillis))
                .signWith(SignatureAlgorithm.HS256, "SEMESTERPROJEKT3") // Replace with your secret key
                .compact();
    }

}
