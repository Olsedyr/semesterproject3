package org.example.beerMachine.repository;

import org.example.beerMachine.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch,Long> { //Long because the id of Batch is a Long

    //
    @Query("SELECT b FROM Batch b WHERE b.recipe = ?1")
    Optional<Batch> findBatchByRecipe(int recipe);

}
