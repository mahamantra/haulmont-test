package com.example.demo7.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Doctors {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    private String patronymic;
    @NotNull
    private String specialization;

    @Override
    public String toString() {
        String s="";
        if(patronymic!=null)s=patronymic;
        return lastName + " " + firstName + " " + s + " (" + specialization + ")";
    }
}
