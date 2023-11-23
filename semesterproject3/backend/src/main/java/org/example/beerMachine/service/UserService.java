package org.example.beerMachine.service;

import org.example.beerMachine.model.Users;
import org.example.beerMachine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(Users user) {
        userRepository.save(user);
    }

    public void deleteUser(Long userID) {
        boolean exists = userRepository.existsById(userID);
        if (!exists) {
            throw new IllegalStateException("No batch found with id: " + userID);
        }
        userRepository.deleteById(userID);
    }

    /*
    @Transactional
    public void updateStudent(Long userID, Integer recipe, Integer quantity, Integer machineSpeed) {
        User user = userRepository.findById(batchId).orElseThrow(() -> new IllegalStateException(
                "No batch found with id: " + batchId));
        if(recipe != null && !Objects.equals(batch.getRecipe(), recipe)){
            batch.setRecipe(recipe);
        }
        if(quantity != null && !Objects.equals(batch.getQuantity(), quantity)){
            batch.setQuantity(quantity);
        }
        if(machineSpeed != null && !Objects.equals(batch.getMachineSpeed(), machineSpeed)){
            batch.setMachineSpeed(machineSpeed);
        }
    }

     */
}
