package com.example.demo7.repo;

import com.example.demo7.domain.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepo extends JpaRepository<Recipes, Long> {

    List<Recipes> findAllByDoctorsId(Long id);
}
