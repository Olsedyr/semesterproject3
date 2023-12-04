package org.example.beerMachine.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.beerMachine.model.Request;
import org.example.beerMachine.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path = "/api/requests")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from frontend
public class RequestController {

    private final RequestRepository requestRepository;


    @Autowired
    public RequestController(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    @PostMapping("/save")
    public ResponseEntity<String> saveRequest(@RequestBody Request request) {
        System.out.println("Received request: " + request);
        requestRepository.save(request);
        return ResponseEntity.ok("Request saved successfully");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
    }

}
