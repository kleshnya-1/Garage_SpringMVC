package ru.excers.projectGarage.models.subModels;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Data
@Log4j2
public class TimeInterval {

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    DateFormat formatShort = new SimpleDateFormat("MM/dd HH:mm");
    DateFormat formatShortMore = new SimpleDateFormat("HH:mm");
       Calendar startDate = new GregorianCalendar();
    Calendar endDate = new GregorianCalendar();

    public TimeInterval(Calendar startDate, Calendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public TimeInterval() {
        this.startDate = startDate;
        this.endDate = endDate;
    }



    public boolean isCrossedChecker(TimeInterval t){
        //проверяет пересечение временных интервалов

        boolean result =(t.getEndDate().after(this.getStartDate())&&this.getEndDate().after(t.getStartDate())) ;

log.debug("is "+t.toString() + " and " + toString()+ " crossed? I think it's "+ result);
        return result;

    }

    public String toString(){

        return  (format.format(this.startDate.getTime())+" - "+
                format.format(this.endDate.getTime()));

    }

    public String toStringForTimeTable(){

        return  (formatShort.format(this.startDate.getTime())+"-"+
                formatShortMore.format(this.endDate.getTime()));

    }

    public String toStringForSQL(){


        return  ("'["+format.format(this.startDate.getTime())+", "+
                format.format(this.endDate.getTime())+"]'");

    }

   // public TimeInterval generateTimeTinterval()


}
