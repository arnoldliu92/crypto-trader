package com.crypto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @NotNull(message = "Name is required")
    @Column(name = "full_name")
    private String fullName;

    @NotNull(message = "Email is required")
    @Column(name = "email", unique = true)
    private String email;

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}
