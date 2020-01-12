package com.example.demo7.service;

import com.example.demo7.domain.Recipes;
import com.example.demo7.repo.RecipeRepo;
import com.vaadin.ui.Notification;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepo recipeRepo;

    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public List<Recipes> allReciept() {
        return recipeRepo.findAll();
    }

    public void saveReciept(Recipes recipes) {

        try {
            recipeRepo.save(recipes);
            Notification.show(recipes +" Сохранен", Notification.Type.WARNING_MESSAGE);
        } catch (Exception e) {
            Notification.show(e.toString(), Notification.Type.WARNING_MESSAGE);
        }
    }

    public void del(Recipes recipes) {
        try {
            recipeRepo.delete(recipes);
            Notification.show(recipes +" Удален", Notification.Type.WARNING_MESSAGE);

        } catch (InvalidDataAccessApiUsageException e) {
            Notification.show("Выбери строку ", Notification.Type.WARNING_MESSAGE);
        }
    }

    public List<Recipes> getStat(Long id) {
        return recipeRepo.findAllByDoctorsId(id);
    }

}
