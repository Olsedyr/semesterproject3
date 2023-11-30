package org.example.beerMachine.service;

import org.example.beerMachine.model.Users;
import org.example.beerMachine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    @Autowired
    public void addUsers() {
        // Check if users already exist
        if (userRepository.count() == 0) {
            // Create and save user1
            Users user1 = new Users("admin", passwordEncoder.encode("admin"));
            userRepository.save(user1);

            // Create and save user2
            Users user2 = new Users("junior", passwordEncoder.encode("junior"));
            userRepository.save(user2);
        }
    }
}

