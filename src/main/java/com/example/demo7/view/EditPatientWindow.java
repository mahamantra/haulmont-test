package com.example.demo7.view;

import com.example.demo7.domain.Patients;
import com.example.demo7.service.PatientService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.validation.ConstraintViolationException;

public class EditPatientWindow extends Window {

    private PatientService patientService;

    private Patients patients;
    private MainUI mainUI;
    private Binder<Patients> binder = new Binder<>(Patients.class);

    public EditPatientWindow(MainUI mainUI) {
        super("Пациент");
        center();
        setModal(true);
        setClosable(false);
        setResizable(false);
        this.mainUI = mainUI;
        patientService = mainUI.getPatientService();

        TextField lastName = new TextField("Фамилия");
        binder.forField(lastName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицей", "^[А-Я][А-Яа-яёЁ\\-]{1,20}$"))
                .bind(Patients::getLastName, Patients::setLastName);
        lastName.setRequiredIndicatorVisible(true);
        TextField firstName = new TextField("Имя");
        binder.forField(firstName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицей", "^[А-Я][а-яёЁ]{1,20}$"))
                .bind(Patients::getFirstName, Patients::setFirstName);
        firstName.setRequiredIndicatorVisible(true);
        TextField patronymic = new TextField("Отчество");
        binder.forField(patronymic)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицей", "^[А-Яа-яёЁ]{0,20}$"))
                .bind(Patients::getPatronymic, Patients::setPatronymic);
        TextField phoneNumber = new TextField("Телефон");
        binder.forField(phoneNumber)
                .withValidator(new RegexpValidator("Введите номер телефона", "^((8|\\+7)[\\- ]?)?(\\(?\\d{4}\\)?[\\- ]?)?[\\d\\- ]{6,10}$"))
                .bind(Patients::getPhoneNumber, Patients::setPhoneNumber);
        phoneNumber.setRequiredIndicatorVisible(true);

        Button save = new Button("ОК");
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> this.save());
        Button cancel = new Button("Отменить");
        cancel.addClickListener(e -> close());

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);
        VerticalLayout verticalLayout = new VerticalLayout(firstName, lastName, patronymic, phoneNumber, horizontalLayout);
        setContent(verticalLayout);
    }


    public void setPatients(Patients patients) {
        this.patients = patients;
        binder.setBean(patients);
    }

    private void save() {
        try {
            if (binder.validate().hasErrors()) throw new IllegalArgumentException();
            if(patients.getPatronymic()==null||patients.getPatronymic().trim().length()==0)patients.setPatronymic("");
            patientService.savePatient(patients);
            mainUI.updatePatientList();
            close();
        } catch (ConstraintViolationException | IllegalArgumentException e) {
            Notification.show("Заполните все необходимые поля", Notification.Type.WARNING_MESSAGE);
        } catch (IllegalStateException e) {
            Notification.show("Пациент с таким именем уже существует", Notification.Type.WARNING_MESSAGE);
        }
    }
}
