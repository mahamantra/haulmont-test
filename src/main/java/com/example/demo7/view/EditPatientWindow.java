package com.example.demo7.view;

import com.example.demo7.domain.Patient;
import com.example.demo7.service.PatientMyService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

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
    private MainUI mainUI;
    private Binder<Patient> binder = new Binder<>(Patient.class);

    public EditPatientWindow() {
        super("Пациент");
        center();
        setModal(true);

        binder.forField(lastName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][А-Яа-яёЁ\\-]{1,20}$"))
                .bind(Patient::getLastName, Patient::setLastName);
        lastName.setRequiredIndicatorVisible(true);
        binder.forField(firstName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][а-яёЁ]{1,20}$"))
                .bind(Patient::getFirstName, Patient::setFirstName);
        firstName.setRequiredIndicatorVisible(true);
        binder.forField(patronymic)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Яа-яёЁ]{0,20}$"))
                .bind(Patient::getPatronymic, Patient::setPatronymic);
        binder.forField(phoneNumber)
                .withValidator(new RegexpValidator("Введите номер телефона", "^((8|\\+7)[\\- ]?)?(\\(?\\d{4}\\)?[\\- ]?)?[\\d\\- ]{6,10}$"))
                .bind(Patient::getPhoneNumber, Patient::setPhoneNumber);
        phoneNumber.setRequiredIndicatorVisible(true);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> this.save());
        cancel.addClickListener(e -> this.closeBtn());

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);
        VerticalLayout verticalLayout = new VerticalLayout(firstName, lastName, patronymic, phoneNumber, horizontalLayout);
        setContent(verticalLayout);
    }

    public void setMainUI(MainUI mainUI) {
        this.mainUI = mainUI;
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
        try {
            patientMyService.savePatient(patient);
            mainUI.updatePatientList();
            close();
        } catch (ConstraintViolationException e) {
            Notification.show("Заполните все необходимые поля");
        }
    }
}
