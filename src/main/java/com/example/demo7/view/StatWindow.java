package com.example.demo7.view;

import com.example.demo7.domain.Doctor;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StatWindow extends Window {
    MainUI mainUI;

    public StatWindow(MainUI mainUI) {
        super("Statistic");
        this.mainUI = mainUI;
        center();
        setModal(true);
        setWidthUndefined();

        List<Doctor> doctorList = mainUI.getDoctorMyService().allDoctors();
        List<Stat> statList = new ArrayList<>();
        Grid<Stat> grid = new Grid<>(Stat.class);
        grid.setWidth("600px");


        doctorList.forEach(doctor -> {
            Stat stat = new Stat();
            stat.setDoctor(doctor);
            stat.setCount(mainUI.getRecieptMyService().getStat(doctor.getId()).size());
            statList.add(stat);
        });
        grid.setItems(statList);

        setContent(grid);

    }

    @Data
  public   static class Stat {
        Doctor doctor;
        Integer count;

    }
}
