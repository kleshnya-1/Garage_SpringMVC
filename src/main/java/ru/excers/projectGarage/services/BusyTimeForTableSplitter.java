package ru.excers.projectGarage.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class BusyTimeForTableSplitter {




    public String split(String busyTime) {

        Pattern pattern = Pattern.compile("-");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");


            String[] strings = pattern.split(busyTime);
        System.out.println(strings);

       /* try {
            String ss = format.parse(busyTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        return "s";



    }
}
