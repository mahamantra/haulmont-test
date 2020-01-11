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

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class EditRecieptWindow extends Window {
    private Recipes recipes;
    private DateField creationDate = new DateField("Дата создания");
    private DateField validity = new DateField("Срок действия");
    private NativeSelect<Patients> patient = new NativeSelect<>("Пациент");
    private NativeSelect<Doctors> doctor = new NativeSelect<>("Доктор");
    private NativeSelect<Priority> priority = new NativeSelect<>("Приоритет");
    private TextArea description = new TextArea("Описание");

    private Button save = new Button("ОК");
    private Button cancel = new Button("Отменить");

    private RecipeService recipeService;
    private MainUI mainUI;
    private Binder<Recipes> binder = new Binder<>(Recipes.class);

    public EditRecieptWindow(Recipes recipes, MainUI mainUI) {
        super("Рецепт");
        this.recipes = recipes;
        this.mainUI = mainUI;
        setMyService(mainUI.getRecipeService());
        center();
        setModal(true);
        setClosable(false);
        setResizable(false);
        List<Doctors> doctorsList = mainUI.getDoctorService().allDoctors();
        doctor.setItems(doctorsList);
        List<Patients> patientsList = mainUI.getPatientService().allPatients();
        patient.setItems(patientsList);
        priority.setItems(Priority.values());

        binder.setBean(recipes);
        binder.forField(creationDate)
                .withValidator(Objects::nonNull,"Пустое поле")
                .bind(Recipes::getCreationDate, Recipes::setCreationDate);
        creationDate.setRequiredIndicatorVisible(true);

        binder.forField(validity)
                .withValidator(localDate -> localDate.isAfter(creationDate.getValue()),"Неверная дата")
                .bind(Recipes::getValidity, Recipes::setValidity);
        validity.setRequiredIndicatorVisible(true);
        binder.forField(patient)
                .withValidator(Objects::nonNull,"Пустое поле")
                .bind(Recipes::getPatients, Recipes::setPatients);
        patient.setRequiredIndicatorVisible(true);
        binder.forField(doctor)
                .withValidator(Objects::nonNull,"Пустое поле")
                .bind(Recipes::getDoctors, Recipes::setDoctors);
        doctor.setRequiredIndicatorVisible(true);
        binder.forField(priority)
                .withValidator(Objects::nonNull,"Пустое поле")
                .bind(Recipes::getPriority, Recipes::setPriority);
        priority.setRequiredIndicatorVisible(true);
        binder.forField(description)
                .withValidator(new StringLengthValidator("Заполните описание",1,255))
                 .bind(Recipes::getDescription, Recipes::setDescription);
        description.setRequiredIndicatorVisible(true);
        description.setSizeFull();

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.closeBtn());

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);
        VerticalLayout verticalLayout = new VerticalLayout(creationDate, validity, patient, doctor, priority, description, horizontalLayout);
        setContent(verticalLayout);
    }


    public void setMyUI(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    public void setRecipes(Recipes recipes) {
        this.recipes = recipes;
    }

    public void setMyService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    private void closeBtn() {
        close();
    }

    private void save() {
        try {
            if(binder.validate().hasErrors())throw new Exception();
            recipeService.saveReciept(recipes);
            mainUI.updateRecieptList();
            close();
        } catch (Exception e) {
            Notification.show("Заполните все необходимые поля");
        }
    }
}
