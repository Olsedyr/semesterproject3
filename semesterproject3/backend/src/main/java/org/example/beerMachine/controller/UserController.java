package org.example.beerMachine.controller;

import org.example.beerMachine.model.Users;
import org.example.beerMachine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path="api/users")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from frontend
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Users> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    public void makeNewUser(@RequestBody Users users){
        userService.addNewUser(users);
    }

    @DeleteMapping(path = "{userID}")
    public void deleteUser(@PathVariable("userID")Long userID){
        userService.deleteUser(userID);
    }
    // Update batches in the database
    @PutMapping(path = "{userID}")
    public void updateUsers(
            @PathVariable("userID") Long userID,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {
        userService.updateStudent(userID,username,password);
    }

}
