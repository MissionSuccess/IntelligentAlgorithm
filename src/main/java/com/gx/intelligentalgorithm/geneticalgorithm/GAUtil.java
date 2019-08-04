package com.gx.intelligentalgorithm.geneticalgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 遗传算法工具类
 */
public class GAUtil {

    /**
     * 获取各个城市的信息
     * @param cityData
     * @return
     */
    public static List<City> getCities(String[] cityData) {

        List<City> cities = new ArrayList<>();
        for (String c : cityData) {
            String[] cityInfo = c.split(",");
            double latitude = Double.valueOf(cityInfo[1]);
            double longitude = Double.valueOf(cityInfo[2]);
            City city = new City(cityInfo[0], latitude, longitude);
            cities.add(city);
        }
        return cities;
    }
}
