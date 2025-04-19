package com.example.service;

import cn.hutool.db.sql.Order;
import com.example.entity.Goods;
import com.example.entity.Statistic;
import com.example.mapper.StatisticMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统公告业务处理
 **/
@Transactional
@Service
public class StatisticService {

    @Resource
    private StatisticMapper statisticMapper;
    @Resource
    private GoodsService goodsService;
    @Resource
    private OrdersService ordersService;
    /**
     * 新增
     */
    public void add(Statistic statistic) {

        statisticMapper.insert(statistic);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        statisticMapper.deleteById(id);
    }

    /**
     * 修改
     */
    public void updateById(Statistic statistic) {
        statisticMapper.updateById(statistic);
    }

    /**
     * 根据ID查询
     */
    public Statistic selectById(Integer id) {
        return statisticMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Statistic> selectAll(Statistic statistic) {
        return statisticMapper.selectAll(statistic);
    }

    /**
     * 分页查询
     */
    public PageInfo<Statistic> selectPage(Statistic statistic, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Statistic> list = statisticMapper.selectAll(statistic);
        return PageInfo.of(list);
    }
    

}