package com.example.demo7.view;

import com.example.demo7.domain.Doctors;
import com.example.demo7.service.DoctorService;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.validation.ConstraintViolationException;

public class EditDoctorWindow extends Window {

    private DoctorService doctorService;
    private Doctors doctors;
    private final MainUI mainUI;
    private Binder<Doctors> binder = new Binder<>();

    public EditDoctorWindow(MainUI mainUI) {
        super("Врач");
        center();
        setModal(true);
        this.mainUI = mainUI;
        this.doctorService = mainUI.getDoctorService();
        setClosable(false);
        setResizable(false);

        TextField lastName = new TextField("Фамилия");
        binder.forField(lastName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицей", "^[А-Я][А-Яа-яёЁ\\-]{1,20}$"))
                .bind(Doctors::getLastName, Doctors::setLastName);
        lastName.setRequiredIndicatorVisible(true);

        TextField firstName = new TextField("Имя");
        binder.forField(firstName)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицей", "^[А-Я][а-яёЁ]{1,20}$"))
                .bind(Doctors::getFirstName, Doctors::setFirstName);
        firstName.setRequiredIndicatorVisible(true);

        TextField patronymic = new TextField("Отчество");
        binder.forField(patronymic)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицей", "^[А-Яа-яёЁ]{0,20}$"))
                .bind(Doctors::getPatronymic, Doctors::setPatronymic);

        TextField specialization = new TextField("Специализация");
        binder.forField(specialization)
                .withValidator(new RegexpValidator("С болшой буквы, кирилицей", "^[А-Я][А-Яа-яёЁ\\s-]{1,30}$"))
                .bind(Doctors::getSpecialization, Doctors::setSpecialization);
        specialization.setRequiredIndicatorVisible(true);

        Button save = new Button("ОК");
        save.addClickListener(e -> this.save());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        Button cancel = new Button("Отменить");
        cancel.addClickListener(e -> close());

        HorizontalLayout horizontalLayout = new HorizontalLayout(save, cancel);
        VerticalLayout verticalLayout = new VerticalLayout(lastName, firstName, patronymic, specialization, horizontalLayout);
        setContent(verticalLayout);
    }

    public void setDoctors(Doctors doctors) {
        this.doctors = doctors;
        binder.setBean(doctors);
    }

    private void save() {
        try {
            if (binder.validate().hasErrors()) throw new IllegalArgumentException();
            doctorService.saveDoctor(doctors);
            mainUI.updateDoctorList();
            close();
        } catch (ConstraintViolationException | IllegalArgumentException e) {
            Notification.show("Заполните все необходимые поля");
        }
    }
}
