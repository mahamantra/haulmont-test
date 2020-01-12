package com.example.demo7.view;

import com.example.demo7.domain.Doctors;
import com.example.demo7.domain.Patients;
import com.example.demo7.domain.Priority;
import com.example.demo7.domain.Recipes;
import com.example.demo7.service.RecipeService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class EditRecieptWindow extends Window {
    private Recipes recipes;
    private RecipeService recipeService;
    private MainUI mainUI;
    private Binder<Recipes> binder = new Binder<>(Recipes.class);

    public EditRecieptWindow(Recipes recipes, MainUI mainUI) {
        super("Рецепт");
        this.recipes = recipes;
        this.mainUI = mainUI;
        recipeService = mainUI.getRecipeService();
        center();
        setModal(true);
        setClosable(false);
        setResizable(false);
        List<Doctors> doctorsList = mainUI.getDoctorService().allDoctors();
        NativeSelect<Doctors> doctor = new NativeSelect<>("Доктор");
        doctor.setItems(doctorsList);
        List<Patients> patientsList = mainUI.getPatientService().allPatients();
        NativeSelect<Patients> patient = new NativeSelect<>("Пациент");
        patient.setItems(patientsList);
        NativeSelect<Priority> priority = new NativeSelect<>("Приоритет");
        priority.setItems(Priority.values());

        binder.setBean(recipes);
        DateField creationDate = new DateField("Дата создания");
        creationDate.setLocale(new Locale("ru","RU"));
        binder.forField(creationDate)
                .withValidator(Objects::nonNull, "Пустое поле")
                .bind(Recipes::getCreationDate, Recipes::setCreationDate);
        creationDate.setRequiredIndicatorVisible(true);

        DateField validity = new DateField("Срок действия");
        validity.setLocale(new Locale("ru","RU"));
        binder.forField(validity)
                .withValidator(localDate -> localDate.isAfter(creationDate.getValue()), "Неверная дата")
                .bind(Recipes::getValidity, Recipes::setValidity);
        validity.setRequiredIndicatorVisible(true);
        binder.forField(patient)
                .withValidator(Objects::nonNull, "Пустое поле")
                .bind(Recipes::getPatients, Recipes::setPatients);
        patient.setRequiredIndicatorVisible(true);
        binder.forField(doctor)
                .withValidator(Objects::nonNull, "Пустое поле")
                .bind(Recipes::getDoctors, Recipes::setDoctors);
        doctor.setRequiredIndicatorVisible(true);
        binder.forField(priority)
                .withValidator(Objects::nonNull, "Пустое поле")
                .bind(Recipes::getPriority, Recipes::setPriority);
        priority.setRequiredIndicatorVisible(true);
        TextArea description = new TextArea("Описание");
        binder.forField(description)
                .withValidator(new StringLengthValidator("Заполните описание", 1, 255))
                .bind(Recipes::getDescription, Recipes::setDescription);
        description.setRequiredIndicatorVisible(true);
        description.setSizeFull();

        Button save = new Button("ОК");
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> this.save());
        Button cancel = new Button("Отменить");
        cancel.addClickListener(e -> close());

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);
        VerticalLayout verticalLayout = new VerticalLayout(creationDate, validity, patient, doctor, priority, description, horizontalLayout);
        setContent(verticalLayout);
    }


    private void save() {
        try {
            if (binder.validate().hasErrors()) throw new Exception();
            recipeService.saveReciept(recipes);
            mainUI.updateRecieptList();
            close();
        } catch (Exception e) {
            Notification.show("Заполните все необходимые поля", Notification.Type.WARNING_MESSAGE);
        }
    }
}
