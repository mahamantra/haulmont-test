package com.example.demo7.view;

import com.example.demo7.domain.Doctor;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import lombok.Data;

import java.util.*;

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
            stat.setCount(mainUI.getRecieptMyService().getStat(doctor.getId()).size());
if (stat.count>0)
            {stat.setDoctor(doctor);
            statList.add(stat);}
        });
        Collections.sort(statList);
        grid.setItems(statList);

        setContent(grid);

    }

    @Data
  public   static class Stat implements Comparable<Stat>{
        Doctor doctor;
        Integer count;

        @Override
        public int compareTo(Stat o) {
            return o.count.compareTo(this.count);
        }
    }
}
