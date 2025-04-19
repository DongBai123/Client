package com.example.mapper;

import com.example.entity.Statistic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作statistic相关数据接口
*/
public interface StatisticMapper {

    /**
      * 新增
    */
    int insert(Statistic statistic);

    /**
      * 删除
    */
    @Delete("delete from statistic where id = #{id}")
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Statistic statistic);

    /**
      * 根据ID查询
    */
    @Select("select * from statistic where id = #{id}")
    Statistic selectById(Integer id);

    /**
      * 查询所有
    */
    List<Statistic> selectAll(Statistic statistic);



}