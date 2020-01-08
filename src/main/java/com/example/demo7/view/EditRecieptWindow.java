package com.example.demo7.view;

import com.example.demo7.domain.Doctor;
import com.example.demo7.domain.Patient;
import com.example.demo7.domain.Priority;
import com.example.demo7.domain.Reciept;
import com.example.demo7.service.RecieptMyService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.validation.ConstraintViolationException;
import java.util.List;


public class EditRecieptWindow extends Window {
    private Reciept reciept;
    private DateField creationDate = new DateField("creationDate");
    private DateField validity = new DateField("validity");
    private NativeSelect<Patient> patient = new NativeSelect<>("patient");
    private NativeSelect<Doctor> doctor = new NativeSelect<>("doctor");
    private NativeSelect<Priority> priority = new NativeSelect<>("Priority");
    private TextArea description = new TextArea("description");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private RecieptMyService recieptMyService;
    private MainUI mainUI;
    private Binder<Reciept> binder = new Binder<>(Reciept.class);

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

        binder.setBean(reciept);
        binder.forField(creationDate)
                //.withValidator(new RegexpValidator("С болшой буквы, кирилицой", "^[А-Я][А-Яа-яёЁ\\-]{1,20}$"))
                .bind(Reciept::getCreationDate, Reciept::setCreationDate);
        creationDate.setRequiredIndicatorVisible(true);
        binder.forField(validity)
                .withValidator(localDate -> localDate.isAfter(creationDate.getValue()),"Неверная дата")
                .bind(Reciept::getValidity, Reciept::setValidity);
        validity.setRequiredIndicatorVisible(true);
        binder.forField(patient)
                .bind(Reciept::getPatient, Reciept::setPatient);
        patient.setRequiredIndicatorVisible(true);
        binder.forField(doctor)
                .bind(Reciept::getDoctor, Reciept::setDoctor);
        doctor.setRequiredIndicatorVisible(true);
        binder.forField(priority)
                .bind(Reciept::getPriority, Reciept::setPriority);
        priority.setRequiredIndicatorVisible(true);
        binder.forField(description)
                .withValidator(new StringLengthValidator("Заполните описание",1,255))
                 .bind(Reciept::getDescription, Reciept::setDescription);
        description.setRequiredIndicatorVisible(true);

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

    public void setReciept(Reciept reciept) {
        this.reciept = reciept;
    }

    public void setMyService(RecieptMyService recieptMyService) {
        this.recieptMyService = recieptMyService;
    }

    private void closeBtn() {
        close();
    }

    private void save() {
        try {
            if(binder.validate().hasErrors())throw new Exception();
            recieptMyService.saveReciept(reciept);
            mainUI.updateRecieptList();
            close();
        } catch (Exception e) {
            Notification.show("Заполните все необходимые поля");
        }
    }
}
