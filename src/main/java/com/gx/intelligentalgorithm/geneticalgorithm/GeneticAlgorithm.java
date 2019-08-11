package com.gx.intelligentalgorithm.geneticalgorithm;

import com.gx.intelligentalgorithm.basedata.city.City;
import com.gx.intelligentalgorithm.basedata.city.CityUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 遗传算法主方法
 */
public class GeneticAlgorithm {

    /**
     * 遗传算法入口
     */
    @Test
    public void applicationByGA() {
        Map<City, Map<City, Double>> cityNearMatrix = CityUtil.getCityNearMatrix();
        City beijing = CityUtil.getCityByName("北京");
        City shanghai = CityUtil.getCityByName("上海");
        System.out.println(beijing);
        System.out.println(shanghai);
        for (Entry<City, Map<City, Double>> map : cityNearMatrix.entrySet()) {
            City key = map.getKey();
            for(Entry<City, Double> m : map.getValue().entrySet()) {
                System.out.println(key.getName() + "," + m.getKey().getName() + "," + m.getValue());
            }
        }
    }

}
