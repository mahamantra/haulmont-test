package com.example.demo7.view;

import com.example.demo7.domain.Doctor;
import com.example.demo7.domain.Patient;
import com.example.demo7.domain.Priority;
import com.example.demo7.domain.Reciept;
import com.example.demo7.service.RecieptMyService;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;


public class EditRecieptWindow extends Window {
    private Reciept reciept;

    private TextArea description = new TextArea("description");
    private NativeSelect<Patient> patient = new NativeSelect<>("patient");
    private NativeSelect<Doctor> doctor = new NativeSelect<>("doctor");
    private DateField validity = new DateField("validity");
    private NativeSelect<Priority> priority = new NativeSelect<>("Priority");
    private DateField creationDate = new DateField("creationDate");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");


    private RecieptMyService recieptMyService;


    private MainUI mainUI;
    // private Binder<Reciept> binder = new Binder<>(Reciept.class);

    public EditRecieptWindow(Reciept reciept, MainUI mainUI) {
        super("Reciept editor");
        this.reciept = reciept;
        this.mainUI = mainUI;
        setMyService(mainUI.getRecieptMyService());
        center();
        setModal(true);
        List<Doctor> doctorsList = mainUI.getDoctorMyService().allDoctors();
        doctor.setItems(doctorsList);
        List<Patient> patientList = mainUI.getPatientMyService().allPatients();
        patient.setItems(patientList);

        priority.setItems(Priority.values());

        if (reciept.getId() != null) {
            description.setValue(reciept.getDescription());
            validity.setValue(new Date(reciept.getValidity().getTime()).toLocalDate());
            creationDate.setValue(new Date(reciept.getCreationDate().getTime()).toLocalDate());
            priority.setValue(reciept.getPriority());
            doctor.setValue(reciept.getDoctor());
            patient.setValue(reciept.getPatient());
        }


        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.closeBtn());

        // binder.bindInstanceFields(this);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);

        VerticalLayout verticalLayout = new VerticalLayout(validity, creationDate, priority, doctor, patient, description, horizontalLayout);

        setContent(verticalLayout);
    }


    public void setMyUI(MainUI mainUI) {
        this.mainUI = mainUI;
    }

    public void setReciept(Reciept reciept) {
        this.reciept = reciept;
        // binder.setBean(reciept);
    }

    public void setMyService(RecieptMyService recieptMyService) {
        this.recieptMyService = recieptMyService;
    }

    private void closeBtn() {
        close();
    }

    private void save() {
        updateReciept();
        recieptMyService.saveReciept(reciept);
        mainUI.updateRecieptList();
        close();
    }

    private void updateReciept() {
        reciept.setCreationDate(Date.valueOf(creationDate.getValue()));
        reciept.setDescription(description.getValue());
        reciept.setValidity(Date.valueOf(validity.getValue()));
        reciept.setDoctor(doctor.getValue());
        reciept.setPatient(patient.getValue());
        reciept.setPriority(priority.getValue());


    }


}
