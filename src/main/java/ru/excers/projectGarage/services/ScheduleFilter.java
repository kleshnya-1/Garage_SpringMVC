package ru.excers.projectGarage.services;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.cars.CarResult;
import ru.excers.projectGarage.models.Order;
import ru.excers.projectGarage.models.subModels.TimeInterval;
import ru.excers.projectGarage.dao.DriverScheduleDAO;
import ru.excers.projectGarage.services.calcsAndConverters.ScheduleListToDateConverter;
import ru.excers.projectGarage.services.calcsAndConverters.TimeForOrderCalc;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Log4j2
@Component
public class ScheduleFilter {

    DriverScheduleDAO driverScheduleReaderDAO;
    ScheduleListToDateConverter scheduleListToDateConverter;
    TimeForOrderCalc timeForOrderCalc;

    public ScheduleFilter(DriverScheduleDAO driverScheduleReaderDAO,
                          ScheduleListToDateConverter scheduleListToDateConverter,
                          TimeForOrderCalc timeForOrderCalc) {
        this.driverScheduleReaderDAO = driverScheduleReaderDAO;
        this.scheduleListToDateConverter = scheduleListToDateConverter;
        this.timeForOrderCalc = timeForOrderCalc;
    }

    public List<CarResult> filter(List<CarResult> l, Order sOrder) throws ParseException {

        List<CarResult> filteredCars = new ArrayList<>(l);
        log.debug("cars before schedule filter " + filteredCars.size());
        TimeInterval timeIntervalForThisOrder = new TimeInterval();
        Calendar start = sOrder.getOrderStartTime();

        timeIntervalForThisOrder.setStartDate(start);


        for (CarResult c : l) {
            int minutesForOrder = timeForOrderCalc.minutesForOrderExpected(sOrder.getDistanceKm());
            start.add(Calendar.MINUTE, -timeForOrderCalc.getMinutesOneWayTime());
            Calendar finish = new GregorianCalendar();
            finish.setTime(start.getTime());
            finish.add(Calendar.MINUTE, minutesForOrder);
            timeIntervalForThisOrder.setEndDate(finish);

            List<TimeInterval> timeListForDriverC = driverScheduleReaderDAO.getScheduleListTimeInt(c.getDriver_id());

            for (TimeInterval t : timeListForDriverC) {
                if (t.isCrossedChecker(timeIntervalForThisOrder)) {
                    log.debug(timeIntervalForThisOrder + " и " + t + " пересекаются, машина удалена");
                    filteredCars.remove(c);

                }

            }


        }

        log.debug("cars after schedule filter  " + filteredCars.size());


        return filteredCars;

    }


}
