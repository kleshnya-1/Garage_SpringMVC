package ru.excers.projectGarage.dao.mapers;


import org.springframework.jdbc.core.RowMapper;
import ru.excers.projectGarage.services.calcsAndConverters.FuelTupeConverter;
import ru.excers.projectGarage.models.cars.CarResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarResultMapper implements RowMapper {
    @Override
    public CarResult mapRow(ResultSet resultSet, int i) throws SQLException {
        FuelTupeConverter fuelTupeConverterToEnum = new FuelTupeConverter();

        CarResult car = new CarResult();

        car.setId(resultSet.getString("id"));
        car.setBrand(resultSet.getString("brand"));
        car.setModel(resultSet.getString("model"));
        car.setDriver_id(resultSet.getInt("driver_id"));
        car.setDriverName(resultSet.getString("name"));
         car.setEarnedThisWeek(resultSet.getDouble("earned_this_week"));
        car.setIs_male(resultSet.getBoolean("is_male"));
        car.setFuelType(fuelTupeConverterToEnum.fuelTypeChooser(resultSet.getString("fuel")));
        car.setConsumption(resultSet.getInt("consumption"));
        car.setConsumption_loaded(resultSet.getInt("consumption_loaded"));
        car.setReliabilityInPercents(resultSet.getInt("reliability_%"));
        car.setFuel_in_tank(resultSet.getInt("fuel_in_tank"));
        car.setTankCapacity(resultSet.getInt("tank_capacity"));

        car.setSeats(resultSet.getInt("seats"));
        car.setMaxLoadKg(resultSet.getInt("carrying"));
        car.setOdometer(resultSet.getInt("odometer_km"));

        return car;
    }
}
