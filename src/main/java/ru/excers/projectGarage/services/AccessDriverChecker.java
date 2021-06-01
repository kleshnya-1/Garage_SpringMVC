package ru.excers.projectGarage.services;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.dao.DriverScheduleDAO;
import ru.excers.projectGarage.models.cars.AssignedCar;
import ru.excers.projectGarage.models.subModels.TimeTable;

import java.util.List;

@Log4j2
@Component
public class AccessDriverChecker {


     DriverScheduleDAO driverScheduleReaderDAO;



    public AccessDriverChecker(DriverScheduleDAO driverScheduleReaderDAO) {

        this.driverScheduleReaderDAO = driverScheduleReaderDAO;
    }

    public AccessDriverChecker( ) {

    }


    public boolean check(int driverId, AssignedCar car) {

        //проверяет, если ли в рассписании за этим водителем такая закрепленная машина

        boolean decision = false;
        List<TimeTable> l = driverScheduleReaderDAO.getTimeTable();

        for (TimeTable t : l) {

            if (t.getCarId().equals(car.getCarResultInAssignedCar().getId()) &&
                    car.getCarResultInAssignedCar().getDriver_id() == driverId) {
                log.info("Подтверждаю доступ к " + t.getCarId());
                decision = true;
                break;

            }


        }

        return decision;
    }
}
