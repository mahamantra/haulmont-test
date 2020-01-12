package com.example.demo7.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Patients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String patronymic;
    @NotNull
    private String phoneNumber;

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + patronymic;
    }
}