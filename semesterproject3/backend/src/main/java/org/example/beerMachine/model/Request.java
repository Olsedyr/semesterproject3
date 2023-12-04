package org.example.beerMachine.model;

import jakarta.persistence.*;

@Entity
@Table
public class Request {

    @Id
    @SequenceGenerator(
            name = "Request_sequence",
            sequenceName = "Request_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Request_sequence"
    )
    private Long id;

    private String selectedOption;

    public Request() {
        // Default constructor needed for JPA
    }

    public Request(Long id, String selectedOption) {
        this.id = id;
        this.selectedOption = selectedOption;
    }

    // Getters and setters...

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", selectedOption='" + selectedOption + '\'' +
                '}';
    }
}