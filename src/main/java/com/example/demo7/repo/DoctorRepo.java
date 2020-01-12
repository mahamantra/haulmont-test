package com.example.demo7.repo;

import com.example.demo7.domain.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctors, Long> {
   List<Doctors> findByLastNameAndFirstNameAndPatronymic(String lastName,String firstName,String patronymic);
}
