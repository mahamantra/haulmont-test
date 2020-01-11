package com.example.demo7.repo;

import com.example.demo7.domain.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patients, Long> {
}
