package com.ray.pominowner.owner.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Owner extends BaseTimeEntity {

    @Id
    @Column(name = "OWNER_ID")
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String phoneNumber;

    private LocalDate birthDate;

}
