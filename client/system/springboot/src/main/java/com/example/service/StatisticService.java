package com.example.service;

import com.example.entity.Statistic;
import com.example.mapper.StatisticMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticService {

    @Resource
    private StatisticMapper statisticMapper;

    /**
     * 获取用户统计数据
     */
    public List<Statistic> getStatisticsByUser() {
        return statisticMapper.statisticsByUser();
    }

    /**
     * 获取商品统计数据
     */
    public List<Statistic> getStatisticsByGoods() {
        return statisticMapper.statisticsByGoods();
    }

    /**
     * 获取用户和商品的详细统计数据
     */
    public List<Statistic> getStatisticsByUserAndGoods() {
        return statisticMapper.statisticsByUserAndGoods();
    }
    public Map<String, Object> getChartData() {
        List<String> dates = Arrays.asList("2025-04-20", "2025-04-21", "2025-04-22");
        List<Integer> orderCounts = Arrays.asList(10, 15, 8); // 示例数据
        List<Integer> salesSums = Arrays.asList(1000, 1500, 800); // 示例数据

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("dates", dates);
        chartData.put("orderCounts", orderCounts);
        chartData.put("salesSums", salesSums);

        return chartData;
    }
}