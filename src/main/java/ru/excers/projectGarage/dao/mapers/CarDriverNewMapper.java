package ru.excers.projectGarage.dao.mapers;

import org.springframework.jdbc.core.RowMapper;
import ru.excers.projectGarage.models.Driver;

import java.sql.ResultSet;
import java.sql.SQLException;



public class CarDriverNewMapper implements RowMapper {
    @Override
    public Driver mapRow(ResultSet resultSet, int i) throws SQLException {

        Driver driver = new Driver() ;
        driver.setDriverId(resultSet.getInt("id"));
        driver.setName(resultSet.getString("name"));
        driver.setWatch(resultSet.getInt("watch"));
        driver.setMale(resultSet.getBoolean("is_male"));
        driver.setEarnedThisWeek(resultSet.getDouble("earned_this_week"));
        return driver;
    }
}
