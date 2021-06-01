package ru.excers.projectGarage.services.calcsAndConverters;



import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.subModels.TimeInterval;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


@Component
public class ScheduleListToDateConverter {


    public TimeInterval convertMethodForOneString(String timeInterval) throws ParseException {


        Pattern pattern = Pattern.compile("\"");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String[] strings = pattern.split(timeInterval);
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();

        startDate.setTime( format.parse(strings[1]));
        endDate.setTime(format.parse(strings[3]));


        TimeInterval tInter = new TimeInterval(startDate,endDate);
        return tInter;

    }


    public List<TimeInterval> convertMethodRef(List<String> list) throws ParseException {

        if(list.size()==0) return null;
        else {
        List<TimeInterval> listTimeInterval = new ArrayList<>();

        for (String s: list){

            if (s!=null)
            listTimeInterval.add(convertMethodForOneString(s));

        }
        return listTimeInterval;}

    }


        public List<TimeInterval> convertMethod(List<String> list) throws ParseException {
        List<TimeInterval> listTimeInterval = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (String s: list){
            String[] strings = pattern.split(s);
            Calendar startDate = new GregorianCalendar();
            Calendar endDate = new GregorianCalendar();
            startDate.setTime( format.parse(strings[1]));
            endDate.setTime(format.parse(strings[3]));
            listTimeInterval.add(new TimeInterval(startDate,endDate)
            );

        }
        return listTimeInterval;
    }


}
