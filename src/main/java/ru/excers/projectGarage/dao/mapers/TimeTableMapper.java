package ru.excers.projectGarage.dao.mapers;

import org.springframework.jdbc.core.RowMapper;
import ru.excers.projectGarage.models.subModels.TimeTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeTableMapper implements RowMapper {
    @Override
    public TimeTable mapRow(ResultSet resultSet, int i) throws SQLException {

        TimeTable t = new TimeTable();

        t.setDriverName(resultSet.getString("name"));
        t.setCarId(resultSet.getString("car_id"));
        t.setCarBrand(resultSet.getString("brand"));
        t.setOrder(resultSet.getString("order_info"));
        t.setPrice(resultSet.getString("order_price"));
        t.setNumOfEl(resultSet.getString("num_of_elements"));
        t.setDistance(resultSet.getString("distance_km"));

        t.setBusyTime(resultSet.getString("busy_time"));
        t.setNote(resultSet.getString("note"));


        return t;
    }
}
