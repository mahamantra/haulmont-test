package com.example.demo7.view;

import com.example.demo7.domain.Doctor;
import com.example.demo7.service.DoctorMyService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.stereotype.Component;

@Component
public class EditDoctorWindow extends Window {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField patronymic = new TextField("Patronymic");
    private TextField specialization = new TextField("Specialization");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private DoctorMyService doctorMyService;

    private Doctor doctor;
    private MainUI myUI;
    private Binder<Doctor> binder = new Binder<>(Doctor.class);

    public EditDoctorWindow() {
        super("Patient editor");
        center();
        setModal(true);

        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.closeBtn());

        binder.bindInstanceFields(this);

//        binder.forField(lastName)
//                .withValidator(new StringLengthValidator("Too short",3,256));

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);

        VerticalLayout verticalLayout = new VerticalLayout(firstName, lastName, patronymic, specialization, horizontalLayout);

        setContent(verticalLayout);
    }

    public void setMyUI(MainUI myUI) {
        this.myUI = myUI;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        binder.setBean(doctor);
    }

    public void setMyService(DoctorMyService doctorMyService) {
        this.doctorMyService = doctorMyService;
    }

    private void closeBtn() {
        close();
    }

    private void save() {
        doctorMyService.saveDoctor(doctor);
        myUI.updateDoctorList();
        close();
    }
}
