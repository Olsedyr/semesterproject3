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
import java.util.List;

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
        String selectedOption = request.getSelectedOption();
        System.out.println("Received request with selected option: " + selectedOption);

        // Use the selectedOption as needed

        requestRepository.save(request);
        return ResponseEntity.ok("Request saved successfully");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearRequest() {
        requestRepository.deleteAll();
        return ResponseEntity.ok("Request successfully deleted");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = requestRepository.findAll();
        return ResponseEntity.ok(requests);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
    }

}
