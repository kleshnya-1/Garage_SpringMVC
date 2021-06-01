package ru.excers.projectGarage.dao;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.excers.projectGarage.models.subModels.FuelType;

import ru.excers.projectGarage.dao.mapers.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






@Slf4j
@Component
public class GasStationDAO {


    private final JdbcTemplate jdbcTemplate;

    public GasStationDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Double> getWrongFuelMap(){

        List< Map<String, Double>> list=
                jdbcTemplate.query("select * from fuel_price", new FuelMapper());
        Map<String, Double> n = new HashMap<>();
        Map<String, Double> map = list.get(0);

        list.forEach(m ->
                m.entrySet().stream().map
                        (entry -> n.put(entry.getKey(), entry.getValue()))

        );

        list.forEach(mapins -> mapins.entrySet().forEach(e -> map.put(e.getKey(), e.getValue())));





        return map;


    }


   /* public Map<String, Double> getFuelMap() {
        List<Map<String, Object>> listPriceFuel = getWrongFuelMap();


        Map<String, Double> mapFuelPrice = new HashMap<>();


        listPriceFuel.forEach(m -> m.forEach(
                (s, o) ->
                        mapFuelPrice.put(o.toString(), Double.parseDouble(s))

        ));

        // Map<String, Object> wnong;
*//*
        wnong.forEach((s, o) ->
                mapFuelPrice.put(o.toString(), Double.parseDouble(s))
        );*//*
        return mapFuelPrice;
    }*/


    public double getFuelPrice (FuelType fuelType){
        String s =fuelType.toString();
       Map m = getWrongFuelMap();
       log.info(m+""+s);
        return (double) getWrongFuelMap().get(fuelType.toString());

       /* return jdbcTemplate.queryForObject("select price_in_byn from fuel_price where fuel_type=?",
                new Object[]{fuelType.toString()}, Double.class);*/
    }



    /* public Map getFuelMap(){
         Map<String, Double> fuelMap = new HashMap<String, Double>();

        try {
            ResultSet resultSet = queryToGasStationToFuelPrice.askerToGasStationToFuelPrice(connection);

            while ( resultSet.next()){

                fuelMap.put(resultSet.getString("fuel_type"),resultSet.getDouble("price_in_byn"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug(fuelMap.toString());
         return fuelMap;
     }*/


}
