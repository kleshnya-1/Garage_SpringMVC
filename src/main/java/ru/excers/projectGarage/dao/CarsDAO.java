package ru.excers.projectGarage.dao;

import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.cars.CarResult;
import ru.excers.projectGarage.models.cars.Car;
import ru.excers.projectGarage.models.Order;
import ru.excers.projectGarage.models.OrderWithSpecialConditions;
import ru.excers.projectGarage.services.ScheduleFilter;
import ru.excers.projectGarage.services.SubOrderWithSpecialConditionsFilter;
import ru.excers.projectGarage.services.calcsAndConverters.WatchCalc;
import ru.excers.projectGarage.dao.mapers.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class CarsDAO {

    private final JdbcTemplate jdbcTemplate;
    private WatchCalc watchCalc;
    private SubOrderWithSpecialConditionsFilter subOrderWithSpecialConditionsFilter;
    private ScheduleFilter scheduleFilter;

    public CarsDAO(JdbcTemplate jdbcTemplate,
                   WatchCalc watchCalc,
                   SubOrderWithSpecialConditionsFilter subOrderWithSpecialConditionsFilter,
                   ScheduleFilter scheduleFilter) {
        this.jdbcTemplate = jdbcTemplate;
        this.watchCalc = watchCalc;
        this.subOrderWithSpecialConditionsFilter = subOrderWithSpecialConditionsFilter;
        this.scheduleFilter = scheduleFilter;
    }


    public List<Car> index() {
        return jdbcTemplate.query("select * from cars order by id", new CarMapper());
    }

    public List<Car> getById(String id) {
        return jdbcTemplate.query("select * from cars where  id=?", new Object[]{id}, new CarMapper());
    }

    public List<CarResult> getCarListSuitableCars(Order order, OrderWithSpecialConditions orderWithSpecialConditions) {
        List<CarResult> carList ;
        List<CarResult> carListFilteredSchedule = new ArrayList<>();

        carList =
         jdbcTemplate.query(" select * " +
                "from cars left join drivers on cars.driver_id = drivers.id where " +
                " seats >=? and carrying >=? and is_ok = true and watch=?  ", new Object[]{
               order.getNumOfPass(), order.getNumOfKg(), watchCalc.returnWatch(order)}, new CarResultMapper());

        if (orderWithSpecialConditions != null) {
            carList = subOrderWithSpecialConditionsFilter.filter(carList, orderWithSpecialConditions);
            log.info("Машин подходит по дополнительным условиям: "+carList.size());

        }

        try {
            carListFilteredSchedule = scheduleFilter.filter(carList, order);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        log.info("Машин свободно на время заказа: "+carListFilteredSchedule.size());
        return carListFilteredSchedule;
    }

    public void addInfoForCars(String id, Boolean techState, int odometer, double fuel) {
        jdbcTemplate.update(" update  cars set is_ok=?, odometer_km=odometer_km+?, " +
                "fuel_in_tank=fuel_in_tank-? " +
                "where  id  =? ", new Object[]{techState, odometer, fuel, id});
    }
}


