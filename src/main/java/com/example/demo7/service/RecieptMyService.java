package com.example.demo7.service;

import com.example.demo7.domain.Doctor;
import com.example.demo7.domain.Patient;
import com.example.demo7.domain.Reciept;
import com.example.demo7.repo.DoctorRepo;
import com.example.demo7.repo.RecieptRepo;
import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecieptMyService {

    @Autowired
    private RecieptRepo recieptRepo;


    public List<Reciept> allReciept() {
        return recieptRepo.findAll();
    }

    public void saveReciept(Reciept reciept) {

        recieptRepo.save(reciept);
    }


    public void del(Reciept reciept) {
        recieptRepo.delete(reciept);
    }

    public List<Reciept> getStat(Long id) {
        return recieptRepo.findAllByDoctorId(id);
    }

    public List<Reciept> findByDes(String filtrDes){
        return recieptRepo.findByDescription(filtrDes);
    }
}
