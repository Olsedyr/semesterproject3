package org.example.beerMachine.repository;

import org.example.beerMachine.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT b FROM Request b WHERE b.id = ?1")
    Optional<Request> FindRequestBYid(String id);
}
