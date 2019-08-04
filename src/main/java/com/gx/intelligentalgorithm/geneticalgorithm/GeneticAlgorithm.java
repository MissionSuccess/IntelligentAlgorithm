package com.gx.intelligentalgorithm.geneticalgorithm;

import com.gx.intelligentalgorithm.basedata.CityData;
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
}
