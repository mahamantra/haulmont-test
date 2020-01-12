package com.example.demo7.view;


import com.example.demo7.domain.Doctors;
import com.example.demo7.domain.Patients;
import com.example.demo7.domain.Priority;
import com.example.demo7.domain.Recipes;
import com.example.demo7.service.DoctorService;
import com.example.demo7.service.PatientService;
import com.example.demo7.service.RecipeService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@EqualsAndHashCode(callSuper = true)
@Data
@Theme("valo")
@SpringUI
public class MainUI extends UI {

    private DoctorService doctorService;
    private PatientService patientService;
    private RecipeService recipeService;

    private Grid<Doctors> doctorsGrid = new Grid<>(Doctors.class);
    private Grid<Patients> patientsGrid = new Grid<>(Patients.class);
    private Grid<Recipes> recipesGrid = new Grid<>(Recipes.class);

    private VerticalLayout doctorLayout;
    private VerticalLayout patientLayout;
    private VerticalLayout recipeLayout;


    @Autowired
    public MainUI(DoctorService doctorService, PatientService patientService, RecipeService recipeService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.recipeService = recipeService;
    }

    @Override
    protected void init(VaadinRequest request) {
        TabSheet tabSheet = new TabSheet();

        doctorGridInit();
        patientGridInit();
        recieptGridInit();
        doctorLayout.setMargin(true);
        patientLayout.setMargin(true);
        tabSheet.addTab(doctorLayout).setCaption("Врачи");
        tabSheet.addTab(patientLayout).setCaption("Пациенты");
        tabSheet.addTab(recipeLayout).setCaption("Рецепты");
        tabSheet.setSizeFull();

        VerticalLayout verticalLayout = new VerticalLayout(tabSheet);
        verticalLayout.setSizeFull();
        setContent(verticalLayout);
    }

    private void doctorGridInit() {
        EditDoctorWindow editDoctorWindow = new EditDoctorWindow(this);

        Button addBtn = new Button("Добавить", event -> {
            editDoctorWindow.setDoctors(new Doctors());
            addWindow(editDoctorWindow);
        });
        addBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);

        Button editBtn = new Button("Изменить", event -> {
            try {
                Doctors doctors = doctorsGrid.asSingleSelect().getValue();
                if (doctors == null) throw new NullPointerException();
                editDoctorWindow.setDoctors(doctors);
            } catch (NullPointerException e) {
                Notification.show("Выбери строку",Notification.Type.WARNING_MESSAGE);
                return;
            }
            addWindow(editDoctorWindow);
        });
        editBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button deleteBtn = new Button("Удалить", event -> {
            doctorService.del(doctorsGrid.asSingleSelect().getValue());
            updateDoctorList();
        });
        deleteBtn.setStyleName(ValoTheme.BUTTON_DANGER);

        Button statBtn = new Button("Показать статистику", event -> addWindow(new StatWindow(this)));

        HorizontalLayout buttonLayout = new HorizontalLayout(addBtn, editBtn, deleteBtn, statBtn);
        buttonLayout.setMargin(true);

        doctorsGrid.setColumns("lastName", "firstName", "patronymic", "specialization");
        doctorsGrid.getColumn("lastName").setCaption("Фамилия");
        doctorsGrid.getColumn("firstName").setCaption("Имя");
        doctorsGrid.getColumn("patronymic").setCaption("Отчество");
        doctorsGrid.getColumn("specialization").setCaption("Специализация");
        doctorsGrid.setSizeFull();

        doctorLayout = new VerticalLayout(doctorsGrid, buttonLayout);
        doctorLayout.setSizeFull();
        doctorLayout.setExpandRatio(doctorsGrid, 1);
        doctorLayout.setMargin(false);

        updateDoctorList();
    }


    private void patientGridInit() {
        EditPatientWindow editPatientWindow = new EditPatientWindow(this);

        Button addBtn = new Button("Добавить", event -> {
            editPatientWindow.setPatients(new Patients());
            addWindow(editPatientWindow);
        });
        addBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);


        Button editBtn = new Button("Изменить", event -> {
            try {
                Patients patients = patientsGrid.asSingleSelect().getValue();
                editPatientWindow.setPatients(patients);
                if (patients == null) throw new NullPointerException();
            } catch (NullPointerException e) {
                Notification.show("Выбери строку",Notification.Type.WARNING_MESSAGE);
                return;
            }
            addWindow(editPatientWindow);
        });
        editBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button deleteBtn = new Button("Удалить", event -> {
            patientService.del(patientsGrid.asSingleSelect().getValue());
            updatePatientList();
        });

        HorizontalLayout buttonLayout = new HorizontalLayout(addBtn, editBtn, deleteBtn);
        buttonLayout.setMargin(true);
        deleteBtn.setStyleName(ValoTheme.BUTTON_DANGER);

        patientsGrid.setColumns("lastName", "firstName", "patronymic", "phoneNumber");
        patientsGrid.getColumn("lastName").setCaption("Фамилия");
        patientsGrid.getColumn("firstName").setCaption("Имя");
        patientsGrid.getColumn("patronymic").setCaption("Отчество");
        patientsGrid.getColumn("phoneNumber").setCaption("Телефон");
        patientsGrid.setSizeFull();

        patientLayout = new VerticalLayout(patientsGrid, buttonLayout);
        patientLayout.setSizeFull();
        patientLayout.setExpandRatio(patientsGrid, 1);
        patientLayout.setMargin(false);

        updatePatientList();
    }

    private void recieptGridInit() {
        Label des = new Label("Описание:");
        TextField descriptionFilter = new TextField();
        Label pat = new Label("Пациент:");
        NativeSelect<Patients> patientFilter = new NativeSelect<>();
        patientFilter.setItems(patientService.allPatients());
        Label pri = new Label("Приоритет:");
        NativeSelect<Priority> priorityFilter = new NativeSelect<>();
        priorityFilter.setItems(Priority.values());
        Button filterBtn = new Button("Применить фильтры");

        filterBtn.addClickListener(event -> {
            List<Recipes> list = recipeService.allReciept();
            if (priorityFilter.getValue() != null) {
                list = list.stream().filter(recipes -> recipes.getPriority() == priorityFilter.getValue()).collect(Collectors.toList());
            }
            if (patientFilter.getValue() != null) {
                list = list.stream().filter(recipes -> recipes.getPatients().getId().equals(patientFilter.getValue().getId())).collect(Collectors.toList());
            }
            if (descriptionFilter.getValue() != null&&descriptionFilter.getValue().trim().length()!=0) {
                list = list.stream().filter(recipes -> recipes.getDescription().toLowerCase().contains(descriptionFilter.getValue().toLowerCase())).collect(Collectors.toList());
            }
            recipesGrid.setItems(list);
        });

        HorizontalLayout filterlayout = new HorizontalLayout(des, descriptionFilter, pat, patientFilter, pri, priorityFilter, filterBtn);
        filterlayout.setMargin(true);

        Button addBtn = new Button("Добавить", event -> {
            Recipes recipes = new Recipes();
            recipes.setCreationDate(LocalDate.now());
            addWindow(new EditRecieptWindow(recipes, this));
        });
        addBtn.setStyleName(ValoTheme.BUTTON_PRIMARY);

        Button editBtn = new Button("Изменить", event -> {
            EditRecieptWindow editRecieptWindow;
            try {
                Recipes recipes = recipesGrid.asSingleSelect().getValue();
                if (recipes == null) throw new NullPointerException();
                editRecieptWindow = new EditRecieptWindow(recipes, this);
                addWindow(editRecieptWindow);
            } catch (NullPointerException e) {
                Notification.show("Выбери строку",Notification.Type.WARNING_MESSAGE);
            }
        });
        editBtn.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button deleteBtn = new Button("Удалить", event -> {
            recipeService.del(recipesGrid.asSingleSelect().getValue());
            updateRecieptList();
        });
        deleteBtn.setStyleName(ValoTheme.BUTTON_DANGER);

        HorizontalLayout buttonLayout = new HorizontalLayout(addBtn, editBtn, deleteBtn);
        buttonLayout.setMargin(true);

        recipesGrid.setColumns("patients", "doctors", "description", "priority", "creationDate", "validity");
        recipesGrid.getColumn("description").setCaption("Описание");
        recipesGrid.getColumn("priority").setCaption("Приоритет");
        recipesGrid.getColumn("doctors").setCaption("Врач");
        recipesGrid.getColumn("patients").setCaption("Пациент");
        recipesGrid.getColumn("validity").setCaption("Срок действия");
        recipesGrid.getColumn("creationDate").setCaption("Дата создания");
        recipesGrid.setSizeFull();

        recipeLayout = new VerticalLayout(filterlayout, recipesGrid, buttonLayout);
        recipeLayout.setSizeFull();
        recipeLayout.setHeight("100%");
        recipeLayout.setExpandRatio(recipesGrid, 1);
        recipeLayout.setMargin(false);


        updateRecieptList();
    }

    public void updateDoctorList() {
        List<Doctors> list = doctorService.allDoctors();
        doctorsGrid.setItems(list);
    }

    public void updatePatientList() {
        List<Patients> list = patientService.allPatients();
        patientsGrid.setItems(list);
    }

    public void updateRecieptList() {

        List<Recipes> list = recipeService.allReciept();
        recipesGrid.setItems(list);
    }

}