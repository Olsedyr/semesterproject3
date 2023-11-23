package org.example.beerMachine.model;

import jakarta.persistence.*;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Entity
@Table
public class Users {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    private String username;
    private String password;
    private LocalDateTime createdTime;

    public Users(){
    }

    public Users(Long id, String username, String password, LocalDateTime createdTime){
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", createdTime=" + createdTime +
                '}';
    }


    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}