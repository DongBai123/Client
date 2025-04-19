package com.example.mapper;

import com.example.entity.Purchase;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 操作purchase相关数据接口
*/
public interface PurchaseMapper {

    /**
      * 新增
    */
    int insert(Purchase purchase);

    /**
      * 删除
    */
    @Delete("delete from purchase where id = #{id}")
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Purchase purchase);

    /**
      * 根据ID查询
    */
    @Select("select * from purchase where id = #{id}")
    Purchase selectById(Integer id);

    /**
      * 查询所有
    */
    List<Purchase> selectAll(Purchase purchase);



}