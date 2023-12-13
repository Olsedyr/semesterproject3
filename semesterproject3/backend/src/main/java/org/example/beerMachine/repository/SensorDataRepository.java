package org.example.beerMachine.repository;

import org.example.beerMachine.model.Batch;
import org.example.beerMachine.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData,Long> {

    @Query("SELECT s FROM SensorData s WHERE s.batchId = :batchId")
    List<SensorData> findAllByBatchId(Long batchId);

    @Query("SELECT AVG(s.temperature) FROM SensorData s WHERE s.batchId = :batchId")
    Float calculateTemperatureMeanByBatchId(Long batchId);
    @Query("SELECT AVG(s.humidity) FROM SensorData s WHERE s.batchId = :batchId")
    Float calculateHumidityMeanByBatchId(Long batchId);
    @Query("SELECT AVG(s.vibration) FROM SensorData s WHERE s.batchId = :batchId")
    Float calculateVibrationMeanByBatchId(Long batchId);

    Optional<SensorData> findFirstByOrderByTimeDesc();

}