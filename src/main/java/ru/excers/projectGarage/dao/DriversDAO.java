package ru.excers.projectGarage.dao;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.Driver;
import ru.excers.projectGarage.dao.mapers.*;

import java.util.List;




@Component
@Log4j2
public class DriversDAO {
    private final JdbcTemplate jdbcTemplate;
    private static int COUNT;
    private List<Driver> NEWDriverList;


@Autowired
    public DriversDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Driver> index(){
        return jdbcTemplate.query("select * from drivers order by id", new CarDriverNewMapper());
    }

    public Driver getPeersonById(int id){

    Driver d = (Driver)(jdbcTemplate.query("select * from drivers where id=?",
            new Object[]{id}, new CarDriverNewMapper())).get(0);
       return  d;
    }


    public void addMoney(int id, double earned){



    jdbcTemplate.update("update drivers set earned_this_week =earned_this_week+? " +
            "where id=?", new Object[]{earned, id});
        log.info("Водитель "+ id + " заработал " + earned);

//        Statement statement = connection.createStatement();
//        PreparedStatement preparedStatement = connection.prepareStatement(
//                "update  drivers set earned_this_week  =earned_this_week+? " +
//                        "where id=?");
//
//        preparedStatement.setDouble(1, earnedMoney);
//        preparedStatement.setInt(2, driverId);
//        log.info("Водитель "+ driverId + " заработал " + earnedMoney);
//        preparedStatement.executeUpdate();


}







}
