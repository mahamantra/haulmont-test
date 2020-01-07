package com.example.demo7.service;

import com.example.demo7.domain.Doctor;
import com.example.demo7.domain.Patient;
import com.example.demo7.repo.DoctorRepo;
import com.example.demo7.repo.PatientRepo;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientMyService {

    @Autowired
    private PatientRepo patientRepo;


    public List<Patient> allPatients() {

        return patientRepo.findAll();
    }

    public void savePatient(Patient patient) {

        patientRepo.save(patient);
    }

    public void del(Patient patient) {
        patientRepo.delete(patient);
    }


}
