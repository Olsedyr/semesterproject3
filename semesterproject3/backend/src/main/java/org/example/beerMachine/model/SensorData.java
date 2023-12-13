package org.example.beerMachine.model;

import jakarta.persistence.*;
import org.example.beerMachine.OpcUA.MachineController;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
@Entity
@Table(name = "sensorData")
public class SensorData {
    @Id
    @SequenceGenerator(
            name = "sensorData_sequence",
            sequenceName = "sensorData_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sensorData_generator"
    )


    private Long id;
    private Long batchId;
    private LocalDateTime time;
    private float temperature;
    private float humidity;
    private float vibration;


    public SensorData() {
    }




    public SensorData(Long batchId, float temperature, float humidity, float vibration) {
        this.batchId = batchId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.vibration = vibration;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", batchId=" + batchId +
                ", time=" + time +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", vibration=" + vibration +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Long getBatchId() {
        return batchId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getVibration() {
        return vibration;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setVibration(float vibration) {
        this.vibration = vibration;
    }

    // set the time before the entity is persisted for the first time
    @PrePersist
    protected void onCreate() throws ExecutionException, InterruptedException {
        //MachineController machineController = new MachineController();
        this.time = LocalDateTime.now();

    }
}
