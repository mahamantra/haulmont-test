package com.example.demo7.repo;

import com.example.demo7.domain.Doctor;
import com.example.demo7.domain.Reciept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RecieptRepo extends JpaRepository<Reciept, Long> {

    List<Reciept> findAllByDoctorId(Long id);

    @Query("from Reciept r " +
                    "where r.description like concat('%',:filterDes,'%') ")
    List<Reciept> findByDescription(@Param("filterDes") String filterDes);
}
