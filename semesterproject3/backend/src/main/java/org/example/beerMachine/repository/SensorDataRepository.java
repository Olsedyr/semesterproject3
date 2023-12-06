package org.example.beerMachine.repository;

import org.example.beerMachine.model.Batch;
import org.example.beerMachine.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData,Long> {

    Optional<SensorData> findFirstByOrderByTimeDesc();

}