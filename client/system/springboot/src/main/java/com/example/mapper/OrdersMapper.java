package com.example.mapper;

import com.example.entity.Orders;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作orders相关数据接口
*/
public interface OrdersMapper {

    /**
      * 新增
    */
    int insert(Orders orders);

    /**
      * 删除
    */
    @Delete("delete from orders where id = #{id}")
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Orders orders);

    /**
      * 根据ID查询
    */
    @Select("select * from orders where id = #{id}")
    Orders selectById(Integer id);

    /**
      * 查询所有
    */
    List<Orders> selectAll(Orders orders);

    /**
     * 统计订单数量
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM orders " +
            "WHERE 1=1 " +
            "<if test='userId != null'>AND user_id = #{userId}</if> " +
            "<if test='goodsId != null'>AND goods_id = #{goodsId}</if>" +
            "</script>")
    int countOrders(Integer userId, Integer goodsId);

    /**
     * 计算订单总金额
     */
    @Select("<script>" +
            "SELECT SUM(num * price) FROM orders " +
            "WHERE 1=1 " +
            "<if test='userId != null'>AND user_id = #{userId}</if> " +
            "<if test='goodsId != null'>AND goods_id = #{goodsId}</if>" +
            "</script>")
    int sumOrderAmount(Integer userId, Integer goodsId);

}