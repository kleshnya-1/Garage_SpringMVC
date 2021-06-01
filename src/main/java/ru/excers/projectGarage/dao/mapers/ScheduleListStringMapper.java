package ru.excers.projectGarage.dao.mapers;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ScheduleListStringMapper implements RowMapper {




    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        /*List<String> list = new ArrayList<>();
        list.add(*/

          return  resultSet.getString("busy_time");
    }
}
