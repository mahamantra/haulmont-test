package com.example.demo7.service;

import com.example.demo7.domain.Doctor;
import com.example.demo7.repo.DoctorRepo;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorMyService {

    @Autowired
    private DoctorRepo doctorRepo;

    public List<Doctor> allDoctors() {
        return doctorRepo.findAll();
    }

    public void saveDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
    }

    public void del(Doctor doctor) {
        try {
            doctorRepo.delete(doctor);
        } catch (InvalidDataAccessApiUsageException e) {
            Notification.show("Выбери строку ");
        }
    }


}
