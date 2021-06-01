package ru.excers.projectGarage.dao;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.cars.AssignedCar;
import ru.excers.projectGarage.models.subModels.TimeInterval;
import ru.excers.projectGarage.models.subModels.TimeTable;
import ru.excers.projectGarage.services.calcsAndConverters.ScheduleListToDateConverter;
import ru.excers.projectGarage.dao.mapers.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class DriverScheduleDAO {

    @Autowired
    ScheduleListToDateConverter scheduleListToDateConverter;
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    QueriesToBaseForDriverSchedule queriesToBaseForDriverSchedule;

    @Autowired
    public DriverScheduleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }




    public List<String> getScheduleListString(int driverId) {
        return jdbcTemplate.query("select * from driver_schedule where driver_id = ?",
                new Object[]{driverId}, new ScheduleListStringMapper());
    }

    public List<TimeInterval> getScheduleListTimeInt(int driverId) {

        List<TimeInterval> l = new ArrayList<>();
        try {
            l = scheduleListToDateConverter.convertMethodRef(getScheduleListString(driverId));

            //System.out.println(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    public List<TimeTable> getTimeTable() {

        List<TimeTable> lt = jdbcTemplate.query
                ("select * from driver_schedule    left join drivers on driver_schedule.driver_id = drivers.id  left join cars on" +
                " driver_schedule.car_id =cars.id ORDER BY driver_schedule.busy_time ", new TimeTableMapper());


        log.info("из быза расписания водителей взято "+lt.size()+" записей");

        if (lt.size()!=0) return lt;
        else  return new ArrayList<>();



    }

    public void addInSchedule(AssignedCar assignedCar)  {

        try {
            queriesToBaseForDriverSchedule.makeNewOrderInSchedule(
                    assignedCar.getCarResultInAssignedCar().getDriver_id(),
                    assignedCar.getCarResultInAssignedCar().getId(),
                    assignedCar.getOrder(),
                    assignedCar.getNumOfElements(),
                    assignedCar.getTimeInterval().toStringForSQL(),
                    assignedCar.getPriceForOrder(),
                    assignedCar.getOrderDistance()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void makeOrderDone(int driverId, AssignedCar car) throws Exception {

        queriesToBaseForDriverSchedule.makeOrderDoneInSchedule
                ( driverId, car.getTimeInterval().toStringForSQL());

    }


}