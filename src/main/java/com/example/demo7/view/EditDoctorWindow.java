package com.example.demo7.view;

import com.example.demo7.domain.Doctor;
import com.example.demo7.service.DoctorMyService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

public class EditDoctorWindow extends Window {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField patronymic = new TextField("Patronymic");
    private TextField specialization = new TextField("Specialization");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private DoctorMyService doctorMyService;

    private Doctor doctor;

    private MainUI mainUI;
    private Binder<Doctor> binder = new Binder<>();

    public EditDoctorWindow(MainUI mainUI) {
        super("Patient editor");
        center();
        setModal(true);
        this.mainUI=mainUI;
        this.doctorMyService=mainUI.getDoctorMyService();
        System.out.println("3"+doctorMyService);
        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.closeBtn());

        // binder.bindInstanceFields(this);

        binder.forField(lastName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][а-яёЁ]{1,20}$"))
                .bind(Doctor::getLastName, Doctor::setLastName);
        // lastName.setIcon(VaadinIcons.USER_CHECK);
        lastName.setRequiredIndicatorVisible(true);
        binder.forField(firstName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][а-яёЁ]{1,20}$"))
                .bind(Doctor::getFirstName, Doctor::setFirstName);
        binder.forField(patronymic)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][а-яёЁ]{1,20}$"))
                .bind(Doctor::getPatronymic, Doctor::setPatronymic);
        binder.forField(specialization)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][а-яёЁ]{1,30}$"))
                .bind(Doctor::getSpecialization, Doctor::setSpecialization);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);

        VerticalLayout verticalLayout = new VerticalLayout(lastName,firstName, patronymic, specialization, horizontalLayout);

        setContent(verticalLayout);
    }
    public void setMainUI(MainUI mainUI) {
        this.mainUI = mainUI;
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
        try {


            doctorMyService.saveDoctor(doctor);
            mainUI.updateDoctorList();
            close();
        } catch (ConstraintViolationException e) {
            Notification.show("Заполните все необходимые поля");
        }
        ;
    }
}
