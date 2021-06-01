package ru.excers.projectGarage.services.calcsAndConverters;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;



@Log4j2
@Component
public class TimeForOrderCalc extends Calculator {


    public int minutesForOrderExpected(double distance) {

        double minutesForOrder = distance*60 / getSpeedMiddleKmH();
        double extraTimeMinutes = (super.getReserveTimeHours() + getTimeForLandingFullHours() * 2)*60;
        return (int) (minutesForOrder + extraTimeMinutes + getMinutesOneWayTime()*2);
    }

    public int timeExpectedForOrderInt(double distance, int load) {

        double timeDouble = minutesForOrderExpected(distance);
        int time = (int) Math.ceil(timeDouble);
        log.debug("на перевозку " + load + "кг. на " + distance +
                "км потребуется " + timeDouble + " (" + time + ") ч.");
        return time;
    }

    public int getMinutesOneWayTime() {

         double oneWayTime = getDistanceFromGarageKm()/getSpeedMiddleKmH();
         return (int)(oneWayTime*60);
    }

}
