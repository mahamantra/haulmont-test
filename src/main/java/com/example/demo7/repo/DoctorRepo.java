package com.example.demo7.repo;

import com.example.demo7.domain.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctors, Long> {
}
