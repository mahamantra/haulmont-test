package com.example.demo7.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
public class Reciept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Temporal(TemporalType.DATE)
    private Date validity;
    @Enumerated(EnumType.STRING)
    private Priority priority;

}