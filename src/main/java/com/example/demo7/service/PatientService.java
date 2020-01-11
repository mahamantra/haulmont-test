package com.example.demo7.service;

import com.example.demo7.domain.Patients;
import com.example.demo7.repo.PatientRepo;
import com.vaadin.ui.Notification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepo patientRepo;

    public PatientService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }


    public List<Patients> allPatients() {

        return patientRepo.findAll();
    }

    public void savePatient(Patients patients) {

        try {
            patientRepo.save(patients);
            Notification.show(patients +" Сохранен", Notification.Type.WARNING_MESSAGE);
        } catch (Exception e) {
            Notification.show(e.toString());
        }
    }

    public void del(Patients patients) {
        try {
            patientRepo.delete(patients);
            Notification.show(patients +" Удален", Notification.Type.WARNING_MESSAGE);

        } catch (InvalidDataAccessApiUsageException e) {
            Notification.show("Выбери строку ");
        } catch (DataIntegrityViolationException e) {
            Notification.show("Существует рецепт с таким пациентом");
        }
    }


}
