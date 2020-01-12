package com.example.demo7.service;

import com.example.demo7.domain.Doctors;
import com.example.demo7.repo.DoctorRepo;
import com.vaadin.ui.Notification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepo doctorRepo;

    public DoctorService(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public List<Doctors> allDoctors() {
        return doctorRepo.findAll();
    }

    public void saveDoctor(Doctors doctors) {
        List<Doctors> list = doctorRepo.findByLastNameAndFirstNameAndPatronymic(doctors.getLastName(), doctors.getFirstName(), doctors.getPatronymic());
        if (list.size() > 0 && doctors.getId() == null)
            throw new IllegalStateException();
        if (list.size() > 0 && doctors.getId() != null) list.forEach(doctors1 -> {
            if (!doctors1.getId().equals(doctors.getId())) throw new IllegalStateException();
        });
        doctorRepo.save(doctors);
        Notification.show(doctors + " Сохранен", Notification.Type.WARNING_MESSAGE);


    }

    public void del(Doctors doctors) {
        try {
            doctorRepo.delete(doctors);
            Notification.show(doctors + " Удален", Notification.Type.WARNING_MESSAGE);

        } catch (InvalidDataAccessApiUsageException e) {
            Notification.show("Выбери строку ", Notification.Type.WARNING_MESSAGE);
        } catch (DataIntegrityViolationException e) {
            Notification.show("Существует рецепт с таким врачом", Notification.Type.WARNING_MESSAGE);
        }

    }


}
