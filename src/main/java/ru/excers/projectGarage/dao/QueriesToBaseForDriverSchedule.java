package ru.excers.projectGarage.dao;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.services.ConnectionMaker;


import java.sql.*;

@Log4j2
@Component

public class QueriesToBaseForDriverSchedule {

Connection connection = new ConnectionMaker().makeConnection();



    public void makeNewOrderInSchedule( int driverId, String carId, String order, int num, String timeInterval, double orderPrice, double distance) throws Exception {

        Statement statement = connection.createStatement();

        String up =
                "INSERT INTO " +
                        "driver_schedule (driver_id, car_id, order_info, num_of_elements, busy_time, order_price, distance_km) " +
                        "VALUES (" + driverId + ", '" + carId + "', '" + order + "'," + num + "," + timeInterval + "," + orderPrice +", "+ distance +")";
        log.debug(up);
        statement.executeUpdate(up);
        log.info("Запись водителю " + driverId + " на " + timeInterval + " сделана в базу на машину " + carId);
    }




    public void makeOrderDoneInSchedule( int driverId , String timeInterval) throws Exception {

        Statement statement = connection.createStatement();
        String up =
                "update  driver_schedule set note = 'выполнен'" +
                        "where  driver_id = "+driverId+" and busy_time = "+timeInterval;

        log.debug(up);
        statement.executeUpdate(up);

    }

}
