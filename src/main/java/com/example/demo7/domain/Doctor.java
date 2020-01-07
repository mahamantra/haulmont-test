package com.example.demo7.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Doctor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;

    private String patronymic;
    @NotNull
    @NotEmpty
    private String specialization;

    @Override
    public String toString() {
        return lastName+" "+firstName+" "+patronymic+" ("+specialization+")";
    }
}
