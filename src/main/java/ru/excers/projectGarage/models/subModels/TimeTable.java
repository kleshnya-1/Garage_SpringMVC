package ru.excers.projectGarage.models.subModels;



import lombok.Data;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.services.calcsAndConverters.ScheduleListToDateConverter;
import ru.excers.projectGarage.services.BusyTimeForTableSplitter;

import java.text.ParseException;

@Data
@Component
public class TimeTable {
    private String driverName;
    private String carId;
    private String carBrand;
    private String order;
    private String busyTime;

    private String numOfEl ;
    private String distance=" " ;
    private String price;
    private String time=" " ;
    private String note =" " ;


    ScheduleListToDateConverter scheduleListToDateConverter = new ScheduleListToDateConverter();
    BusyTimeForTableSplitter busyTimeForTableSplitter = new BusyTimeForTableSplitter();
    public void setBusyTime(String busyTime) {
        try {
            this.busyTime = scheduleListToDateConverter.convertMethodForOneString(busyTime).toStringForTimeTable();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



}
