package com.crypto.entity;

import jakarta.persistence.*;
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

//    @Column(name = "usd_balance")
//    private double usdBalance;

}
