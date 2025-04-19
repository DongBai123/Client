package com.example.controller;

import com.example.common.Result;
import com.example.entity.Statistic;
import com.example.service.StatisticService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统商品前端操作接口
 **/
@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Statistic statistic) {
        statisticService.add(statistic);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        statisticService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Statistic statistic) {
        statisticService.updateById(statistic);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Statistic statistic = statisticService.selectById(id);
        return Result.success(statistic);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Statistic statistic) {
        List<Statistic> list = statisticService.selectAll(statistic);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Statistic statistic,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Statistic> page = statisticService.selectPage(statistic, pageNum, pageSize);
        return Result.success(page);
    }

}