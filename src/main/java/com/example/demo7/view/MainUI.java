package com.example.demo7.view;


import com.example.demo7.domain.Doctor;
import com.example.demo7.domain.Patient;
import com.example.demo7.domain.Priority;
import com.example.demo7.domain.Reciept;
import com.example.demo7.service.DoctorMyService;
import com.example.demo7.service.PatientMyService;
import com.example.demo7.service.RecieptMyService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Data
@Theme("valo")
@SpringUI
public class MainUI extends UI {

    @Autowired
    private DoctorMyService doctorMyService;
    @Autowired
    private PatientMyService patientMyService;
    @Autowired
    private RecieptMyService recieptMyService;

    // private ServiceImpl serviceImpl=ServiceImpl.getInstance();


    Grid<Doctor> doctorGrid = new Grid<>(Doctor.class);
    Grid<Patient> patientGrid = new Grid<>(Patient.class);
    Grid<Reciept> recieptGrid = new Grid<>(Reciept.class);
    EditDoctorWindow editDoctorWindow = new EditDoctorWindow();
    EditPatientWindow editPatientWindow = new EditPatientWindow();

    VerticalLayout doctorLayout;
    VerticalLayout patientLayout;
    VerticalLayout recieptLayout;

    @Override
    protected void init(VaadinRequest request) {
        doctorGridInit();
        patientGridInit();
        recieptGridInit();
        doctorLayout.setVisible(true);
        patientLayout.setVisible(false);
        recieptLayout.setVisible(false);

//        ListSelect<String> sample = new ListSelect<>("Select an option");
//
//        sample.setItems("Doctors", "Patients", "Recipes");
//        // sample.setRows(6);
//        //sample.select(data.get(0));
//        sample.setWidth("200px");


//        sample.addValueChangeListener(event -> {
//            Notification.show("Value changed:", String.valueOf(event.getValue()),
//                    Notification.Type.TRAY_NOTIFICATION);
//            if (String.valueOf(event.getValue()).equals("[Doctors]")) doctorLayout.setVisible(true);
//            else doctorLayout.setVisible(false);
//        });


        Label title = new Label("Menu");
        title.addStyleName(ValoTheme.MENU_TITLE);

        Button view1 = new Button("Doctors");
        view1.addClickListener(e -> {
            doctorLayout.setVisible(true);
            patientLayout.setVisible(false);
            recieptLayout.setVisible(false);
        });
        view1.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        Button view2 = new Button("Patients");
        view2.addClickListener(e -> {
            doctorLayout.setVisible(false);
            patientLayout.setVisible(true);
            recieptLayout.setVisible(false);
        });
        view2.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        Button view3 = new Button("Reciepts");
        view3.addClickListener(e -> {
            doctorLayout.setVisible(false);
            patientLayout.setVisible(false);
            recieptLayout.setVisible(true);
        });
        view3.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        CssLayout menu = new CssLayout(title, view1, view2, view3);
        menu.addStyleName(ValoTheme.MENU_ROOT);
        menu.setWidth("200px");

        HorizontalLayout mainLayout = new HorizontalLayout(menu, doctorLayout, patientLayout, recieptLayout);
        mainLayout.setExpandRatio(doctorLayout, 1);
        mainLayout.setExpandRatio(patientLayout, 1);
        mainLayout.setExpandRatio(recieptLayout, 1);

        mainLayout.setSizeFull();


        setContent(mainLayout);

    }

    private void doctorGridInit() {
        Button addBtn = new Button("Add", event -> {
            doctorGrid.asSingleSelect().clear();
            editDoctorWindow.setDoctor(new Doctor());
            addWindow(editDoctorWindow);
        });
        Button editBtn = new Button("Edit", event -> addWindow(editDoctorWindow));
        Button deleteBtn = new Button("Delete", event -> {
            doctorMyService.del(doctorGrid.asSingleSelect().getValue());
            updateDoctorList();
        });
        Button statBtn = new Button("Statistic", event -> addWindow(new StatWindow(this)));
        HorizontalLayout buttonLayout = new HorizontalLayout(addBtn, editBtn, deleteBtn, statBtn);
        buttonLayout.setMargin(true);

        doctorGrid.setColumns("lastName", "firstName", "patronymic", "specialization");
        doctorGrid.setSizeFull();

        doctorLayout = new VerticalLayout(doctorGrid, buttonLayout);
        doctorLayout.setSizeFull();
        doctorLayout.setExpandRatio(doctorGrid, 1);
        doctorLayout.setMargin(false);

        doctorGrid.asSingleSelect().addValueChangeListener(event -> {
            editDoctorWindow.setMyService(doctorMyService);
            editDoctorWindow.setDoctor(event.getValue());
            editDoctorWindow.setMyUI(this);

        });
        updateDoctorList();
    }

    private void printStat() {


    }

    private void patientGridInit() {
        Button addBtn = new Button("Add", event -> {
            patientGrid.asSingleSelect().clear();
            editPatientWindow.setPatient(new Patient());
            addWindow(editPatientWindow);
        });
        Button editBtn = new Button("Edit", event -> addWindow(editPatientWindow));
        Button deleteBtn = new Button("Delete", event -> {
            patientMyService.del(patientGrid.asSingleSelect().getValue());
            updatePatientList();
        });
        HorizontalLayout buttonLayout = new HorizontalLayout(addBtn, editBtn, deleteBtn);
        buttonLayout.setMargin(true);

        patientGrid.setColumns("lastName", "firstName", "patronymic", "phoneNumber");
        patientGrid.setSizeFull();

        patientLayout = new VerticalLayout(patientGrid, buttonLayout);
        patientLayout.setSizeFull();
        patientLayout.setExpandRatio(patientGrid, 1);
        patientLayout.setMargin(false);

        patientGrid.asSingleSelect().addValueChangeListener(event -> {
            editPatientWindow.setMyService(patientMyService);
            editPatientWindow.setPatient(event.getValue());
            editPatientWindow.setMyUI(this);

        });
        updatePatientList();
    }

    private void recieptGridInit() {
        TextField descritionFilter = new TextField();
        descritionFilter.setPlaceholder("filter by description...");
        NativeSelect<Patient> patientFilter = new NativeSelect<>();
        patientFilter.setItems(patientMyService.allPatients());
        NativeSelect<Priority> priorityFilter = new NativeSelect<>();
        priorityFilter.setItems(Priority.values());
        Button filterBtn = new Button("Filter");
        filterBtn.addClickListener(event -> {
            List<Reciept> list = recieptMyService.allReciept();
            if (priorityFilter.getValue() != null) {
                list = list.stream().filter(reciept -> reciept.getPriority() == priorityFilter.getValue()).collect(Collectors.toList());
            }
            if (patientFilter.getValue() != null) {
                list = list.stream().filter(reciept -> reciept.getPatient().getId().equals(patientFilter.getValue().getId())).collect(Collectors.toList());
            }
            if (descritionFilter.getValue() != null) {
                list = list.stream().filter(reciept -> reciept.getDescription().contains(descritionFilter.getValue())).collect(Collectors.toList());
            }


            recieptGrid.setItems(list);
        });
        HorizontalLayout filterlayout = new HorizontalLayout(descritionFilter, patientFilter, priorityFilter, filterBtn);

        Button addBtn = new Button("Add", event -> {
            recieptGrid.asSingleSelect().clear();
            addWindow(new EditRecieptWindow(new Reciept(), this));
        });
        Button editBtn = new Button("Edit", event -> {
            EditRecieptWindow editRecieptWindow = new EditRecieptWindow(recieptGrid.asSingleSelect().getValue(), this);
            editRecieptWindow.setMyService(recieptMyService);
            editRecieptWindow.setMyUI(this);
            addWindow(editRecieptWindow);
        });

        Button deleteBtn = new Button("Delete", event -> {
            recieptMyService.del(recieptGrid.asSingleSelect().getValue());
            updateRecieptList();
        });
        HorizontalLayout buttonLayout = new HorizontalLayout(addBtn, editBtn, deleteBtn);
        buttonLayout.setMargin(true);

        recieptGrid.setColumns("description", "priority", "doctor", "patient", "validity", "creationDate");
        recieptGrid.setSizeFull();

        recieptLayout = new VerticalLayout(filterlayout, recieptGrid, buttonLayout);
        recieptLayout.setSizeFull();
        recieptLayout.setHeight("100%");
        recieptLayout.setExpandRatio(recieptGrid, 1);
        recieptLayout.setMargin(false);


        updateRecieptList();
    }

    public void updateDoctorList() {
        List<Doctor> list = doctorMyService.allDoctors();
        doctorGrid.setItems(list);
    }

    public void updatePatientList() {
        List<Patient> list = patientMyService.allPatients();
        patientGrid.setItems(list);
    }

    public void updateRecieptList() {

        List<Reciept> list = recieptMyService.allReciept();
        // List<Reciept> list = recieptMyService.findByDes("6");
        recieptGrid.setItems(list);
    }

    public void filterReciepts(String des, Long patientId, Priority priority) {
        List<Reciept> list = recieptMyService.allReciept();
        if (priority != null) {
            list = list.stream().filter(reciept -> reciept.getPriority() == priority).collect(Collectors.toList());
        }
        if (patientId != null) {
            list = list.stream().filter(reciept -> reciept.getId() == patientId).collect(Collectors.toList());
        }

        recieptGrid.setItems(list);


    }


}