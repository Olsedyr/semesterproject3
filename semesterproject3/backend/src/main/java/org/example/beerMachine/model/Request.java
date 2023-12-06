package org.example.beerMachine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "request")
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

    @Column(name = "selected_option")
    private String selectedOption;

    public Request() {
        // Default constructor needed for JPA
    }

    // Getters and setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", selectedOption='" + selectedOption + '\'' +
                '}';
    }
}