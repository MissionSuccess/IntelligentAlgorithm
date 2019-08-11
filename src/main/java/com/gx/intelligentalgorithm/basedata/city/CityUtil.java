package com.gx.intelligentalgorithm.basedata.city;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CityUtil implements CityData {

    /**
     * 获取各个城市的信息
     * @return
     */
    public static List<City> getCities() {

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

    /**
     * 获取城市与城市间的邻接矩阵
     * @return
     */
    public static Map<City, Map<City,Double>> getCityNearMatrix(){
        List<City> cityList = getCities();
        Map<City, Map<City,Double>> cityNearMatrix = new HashMap<>();
        for(int i = 0; i < cityList.size(); i++) {
            City firstCity = cityList.get(i);
            List<City> tempCityList = new ArrayList<>(cityList);
            tempCityList.remove(firstCity);
            Map<City, Double> mat = new HashMap<>();
            tempCityList.stream().forEach(secondCity -> {
                double distance = getCityDistance(firstCity, secondCity);
                mat.put(secondCity, distance);
            });
            cityNearMatrix.put(firstCity, mat);
        }
        return cityNearMatrix;
    }

    /**
     * 获取两个城市间的距离
     * @param firstCity 第一个城市
     * @param secondCity 第二个城市
     * @return
     */
    public static double getCityDistance(City firstCity, City secondCity) {
        GlobalCoordinates source = new GlobalCoordinates(firstCity.getLatitude(), firstCity.getLongitude());
        GlobalCoordinates target = new GlobalCoordinates(secondCity.getLatitude(), secondCity.getLongitude());

        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, source, target);

        return geoCurve.getEllipsoidalDistance();
    }

    /**
     * 根据城市名获取城市信息
     * @param cityName 城市名
     * @return
     */
    public static City getCityByName(String cityName) {
        List<City> cities = getCities();
        List<City> res = cities.stream().filter(city -> city.getName().equals(cityName)).collect(Collectors.toList());
        return res.isEmpty() ? null : res.get(0);
    }
}
