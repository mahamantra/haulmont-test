package com.example.demo7.view;

import com.example.demo7.domain.Doctors;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Window;
import lombok.Data;

import java.util.*;

public class StatWindow extends Window {
    MainUI mainUI;

    public StatWindow(MainUI mainUI) {
        super("Статистика по количеству рецептов");
        this.mainUI = mainUI;
        center();
        setModal(true);
        setWidthUndefined();
     setResizable(false);

        List<Doctors> doctorsList = mainUI.getDoctorService().allDoctors();
        List<Stat> statList = new ArrayList<>();
        Grid<Stat> grid = new Grid<>(Stat.class);
        grid.setWidth("600px");


        doctorsList.forEach(doctor -> {
            Stat stat = new Stat();
            stat.setCount(mainUI.getRecipeService().getStat(doctor.getId()).size());
if (stat.count>0)
            {stat.setDoctors(doctor);
            statList.add(stat);}
        });
        Collections.sort(statList);
        grid.setItems(statList);

        grid.setColumns("doctors", "count");
        grid.getColumn("doctors").setCaption("Врач");
        grid.getColumn("count").setCaption("Количество");

        setContent(grid);

    }

    @Data
  public   static class Stat implements Comparable<Stat>{
        Doctors doctors;
        Integer count;

        @Override
        public int compareTo(Stat o) {
            return o.count.compareTo(this.count);
        }
    }
}
