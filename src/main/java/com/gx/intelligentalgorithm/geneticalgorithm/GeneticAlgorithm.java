package com.gx.intelligentalgorithm.geneticalgorithm;

import com.gx.intelligentalgorithm.basedata.CityData;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 遗传算法主方法
 */
public class GeneticAlgorithm implements CityData {

    /**
     * 遗传算法入口
     */
    @Test
    public void applicationByGA() {
        List<City> cities = GAUtil.getCities(cityData);
        for(City city : cities){
            System.out.println(city);
        }
    }

    public static void main(String[] args)
    {
        GlobalCoordinates source = new GlobalCoordinates(29.490295, 106.486654);
        GlobalCoordinates target = new GlobalCoordinates(29.615467, 106.581515);

        double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
        double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);

        System.out.println("Sphere坐标系计算结果："+meter1 + "米");
        System.out.println("WGS84坐标系计算结果："+meter2 + "米");
    }

    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid)
    {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }
}
