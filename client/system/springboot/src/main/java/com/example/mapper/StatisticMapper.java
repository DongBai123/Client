package com.example.mapper;

import com.example.entity.Statistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import java.util.List;


@Mapper
public interface StatisticMapper {

    @Select("SELECT " +
            "o.user_id as userId, " +
            "u.username as userName, " +
            "o.goods_id as goodsId, " +
            "g.name as goodsName, " +
            "SUM(o.num) as buyNum, " +
            "SUM(o.num * g.price) as sellSum," +
            "COUNT(o.id) as orderCount " +
            "FROM orders o " +
            "LEFT JOIN user u ON o.user_id = u.id " +
            "LEFT JOIN goods g ON o.goods_id = g.id " +
            "WHERE o.state IN ('待发货', '待收货', '已完成') " +  // 只统计已支付的订单
            "GROUP BY o.user_id, u.username, o.goods_id, g.name")
    List<Statistic> statisticsByUserAndGoods();

    @Select("SELECT " +
            "o.goods_id as goodsId, " +
            "g.name as goodsName, " +
            "SUM(o.num) as buyNum, " +
            "SUM(o.num * g.price) as sellSum, " +
            "COUNT(o.id) as orderCount " +
            "FROM orders o " +
            "LEFT JOIN goods g ON o.goods_id = g.id " +
            "WHERE o.state IN ('待发货', '待收货', '已完成') " +  // 只统计已支付的订单
            "GROUP BY o.goods_id, g.name")
    List<Statistic> statisticsByGoods();

    @Select("SELECT " +
            "o.user_id as userId, " +
            "u.username as userName, " +
            "SUM(o.num) as buyNum, " +
            "SUM(o.num * g.price) as sellSum, " +
            "COUNT(o.id) as orderCount " +
            "FROM orders o " +
            "LEFT JOIN user u ON o.user_id = u.id " +
            "LEFT JOIN goods g ON o.goods_id = g.id " +
            "WHERE o.state IN ('待发货', '待收货', '已完成') " +  // 只统计已支付的订单
            "GROUP BY o.user_id, u.username")
    List<Statistic> statisticsByUser();
}