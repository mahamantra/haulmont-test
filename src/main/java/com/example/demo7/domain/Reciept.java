package com.example.demo7.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Reciept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String description;
    @ManyToOne
    @NotNull
    private Patient patient;
    @ManyToOne
    @NotNull
    private Doctor doctor;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date validity;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;

    public LocalDate getCreationDate() {
        if (creationDate != null) return new java.sql.Date(creationDate.getTime()).toLocalDate();
        return null;
    }

    public void setCreationDate(LocalDate creationDate) {
        if (creationDate != null) this.creationDate = java.sql.Date.valueOf(creationDate);
        else this.creationDate = null;
    }

    public LocalDate getValidity() {
        if (validity != null) return new java.sql.Date(validity.getTime()).toLocalDate();
        return null;
    }

    public void setValidity(LocalDate validity) {
        if (validity != null) this.validity = java.sql.Date.valueOf(validity);
        else this.validity = null;
    }
}