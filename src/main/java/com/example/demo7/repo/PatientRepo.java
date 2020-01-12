package com.example.demo7.repo;

import com.example.demo7.domain.Doctors;
import com.example.demo7.domain.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepo extends JpaRepository<Patients, Long> {
    List<Patients> findByLastNameAndFirstNameAndPatronymic(String lastName, String firstName, String patronymic);

}
