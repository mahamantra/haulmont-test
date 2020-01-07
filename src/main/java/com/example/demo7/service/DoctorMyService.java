package com.example.demo7.service;

import com.example.demo7.domain.Doctor;
import com.example.demo7.repo.DoctorRepo;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorMyService {

    @Autowired
    private DoctorRepo doctorRepo;


    //    private static DoctorService instance;
//
//    public static DoctorService getInstance() {
//        if (instance == null) {
//            instance = new DoctorService();
//
//        }
//        return instance;
//    }


    public List<Doctor> allDoctors() {
        return doctorRepo.findAll();
    }

    public void saveDoctor(Doctor doctor) {
        if (doctorRepo == null) Notification.show("ffffffffffffffffff");
        doctorRepo.save(doctor);
    }

    public void del(Doctor doctor){
        doctorRepo.delete(doctor);
    }





}
