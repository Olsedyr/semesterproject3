package org.example.beerMachine.repository;

import org.example.beerMachine.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,String> {
    @Query("SELECT b FROM Users b WHERE b.username = ?1")
    Optional<Users> FindUserBYUsername(String username);
}
