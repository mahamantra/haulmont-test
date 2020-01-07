package com.example.demo7.view;

import com.example.demo7.domain.Patient;
import com.example.demo7.service.PatientMyService;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.stereotype.Component;

@Component
public class EditPatientWindow extends Window {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField patronymic = new TextField("Patronymic");
    private TextField phoneNumber = new TextField("Phone number");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private PatientMyService patientMyService;

    private Patient patient;
    private MainUI myUI;
    private Binder<Patient> binder = new Binder<>(Patient.class);

    public EditPatientWindow() {
        super("Patient editor");
        center();
        setModal(true);

        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.closeBtn());

        binder.bindInstanceFields(this);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);

        VerticalLayout verticalLayout = new VerticalLayout(firstName, lastName, patronymic, phoneNumber, horizontalLayout);

        setContent(verticalLayout);
    }

    public void setMyUI(MainUI myUI) {
        this.myUI = myUI;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        binder.setBean(patient);
    }

    public void setMyService(PatientMyService patientMyService) {
        this.patientMyService = patientMyService;
    }

    private void closeBtn() {
        close();
    }

    private void save() {
        patientMyService.savePatient(patient);
        myUI.updatePatientList();
        close();
    }
}
