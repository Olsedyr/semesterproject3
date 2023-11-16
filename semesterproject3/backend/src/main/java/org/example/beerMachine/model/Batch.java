package org.example.beerMachine.model;

import jakarta.persistence.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@Entity
@Table
public class Batch {
    @Id
    @SequenceGenerator(
            name = "batch_sequence",
            sequenceName = "batch_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "batch_generator"
    )

    private Long id;
    private int recipe;
    private int quantity;
    private int machineSpeed;
    private LocalDateTime startTime;


    public Batch() {
    }

    public Batch(Long id, int recipe, int quantity, int machineSpeed, LocalDateTime startTime) {
        this.id = id;
        this.recipe = recipe;
        this.quantity = quantity;
        this.machineSpeed = machineSpeed;
        this.startTime = startTime;
    }

    public Batch(int recipe, int quantity, int machineSpeed) {
        this.recipe = recipe;
        this.quantity = quantity;
        this.machineSpeed = machineSpeed;
    }


    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", recipe=" + recipe +
                ", quantity=" + quantity +
                ", machineSpeed=" + machineSpeed +
                ", startTime=" + startTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public int getRecipe() {
        return recipe;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMachineSpeed() {
        return machineSpeed;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setRecipe(int recipe) {
        this.recipe = recipe;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMachineSpeed(int machineSpeed) {
        this.machineSpeed = machineSpeed;
    }

    // set the startTime before the entity is persisted for the first time
    @PrePersist
    protected void onCreate() {
        this.startTime = LocalDateTime.now();
    }
}
