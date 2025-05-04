package com.example.entity;

/**
 * Statistic 实体类
 * 对应 statistic 数据库表
 */
public class Statistic {
    private Integer id;          // 主键ID
    private Integer userId;      // 用户ID
    private Integer goodsId;     // 商品ID
    private Integer buyNum;      // 订单数量
    private Double sellSum;      // 销售总额
    private String userName;
    private String goodsName;
    private Integer orderCount;

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Double getSellSum() {
        return sellSum;
    }

    public void setSellSum(Double sellSum) {
        this.sellSum = sellSum;
    }
}