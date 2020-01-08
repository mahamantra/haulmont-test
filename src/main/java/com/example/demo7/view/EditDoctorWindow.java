package com.example.demo7.view;

import com.example.demo7.domain.Doctor;
import com.example.demo7.service.DoctorMyService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.validation.ConstraintViolationException;

public class EditDoctorWindow extends Window {

    private TextField firstName = new TextField("Имя");
    private TextField lastName = new TextField("Фамилия");
    private TextField patronymic = new TextField("Отчество");
    private TextField specialization = new TextField("Специализация");
    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");
    private DoctorMyService doctorMyService;
    private Doctor doctor;
    private MainUI mainUI;
    private Binder<Doctor> binder = new Binder<>();

    public EditDoctorWindow(MainUI mainUI) {
        super("Редактирование врача");
        center();
        setModal(true);
        this.mainUI = mainUI;
        this.doctorMyService = mainUI.getDoctorMyService();

        binder.forField(lastName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][А-Яа-яёЁ\\-]{1,20}$"))
                .bind(Doctor::getLastName, Doctor::setLastName);
        lastName.setRequiredIndicatorVisible(true);
        binder.forField(firstName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][а-яёЁ]{1,20}$"))
                .bind(Doctor::getFirstName, Doctor::setFirstName);
        firstName.setRequiredIndicatorVisible(true);

        binder.forField(patronymic)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Яа-яёЁ]{0,20}$"))
                .bind(Doctor::getPatronymic, Doctor::setPatronymic);
        binder.forField(specialization)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][А-Яа-яёЁ\\s]{1,30}$"))
                .bind(Doctor::getSpecialization, Doctor::setSpecialization);
        specialization.setRequiredIndicatorVisible(true);

        save.addClickListener(e -> this.save());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        cancel.addClickListener(e -> close());

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);
        VerticalLayout verticalLayout = new VerticalLayout(lastName, firstName, patronymic, specialization, horizontalLayout);
        setContent(verticalLayout);
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        binder.setBean(doctor);
    }

    private void save() {
        try {
            doctorMyService.saveDoctor(doctor);
            mainUI.updateDoctorList();
            close();
        } catch (ConstraintViolationException e) {
            Notification.show("Заполните все необходимые поля");
        }
    }
}
