package com.example.controller;

import com.example.common.Result;
import com.example.entity.Statistic;
import com.example.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    @GetMapping("/byUser")
    public Result getStatisticsByUser() {
        List<Statistic> statistics = statisticService.getStatisticsByUser();
        return Result.success(statistics);
    }

    @GetMapping("/byGoods")
    public Result getStatisticsByGoods() {
        List<Statistic> statistics = statisticService.getStatisticsByGoods();
        return Result.success(statistics);
    }

    @GetMapping("/byUserAndGoods")
    public Result getStatisticsByUserAndGoods() {
        List<Statistic> statistics = statisticService.getStatisticsByUserAndGoods();
        return Result.success(statistics);
    }
    //图
    @GetMapping("/chart")
    public ResponseEntity<?> getChartData() {
        // 假设服务层已实现统计数据的获取逻辑
        Map<String, Object> chartData = statisticService.getChartData();
        return ResponseEntity.ok(chartData);
    }
}