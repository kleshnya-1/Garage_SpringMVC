package ru.excers.projectGarage.dao.mapers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuelMapper implements RowMapper {
    @Override
    public  Map<String, Double> mapRow(ResultSet resultSet, int i) throws SQLException {

        Map<String, Double> fuelPrice = new HashMap<>();
        fuelPrice.put(resultSet.getString("fuel_type"),resultSet.getDouble("price_in_byn"));

        List<Map<String, Double>> cover = new ArrayList<>();
        cover.add(fuelPrice);

        return fuelPrice;
    }
}
