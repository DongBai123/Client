package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.entity.Goods;
import com.example.entity.Orders;
import com.example.exception.CustomException;
import com.example.mapper.OrdersMapper;
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
public class OrdersService {

    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private GoodsService goodsService;
    /**
     * 新增
     */
    @Transactional
    public void add(Orders orders) {
        orders.setOrderNum(IdUtil.fastSimpleUUID());//订单编号
        orders.setTime(DateUtil.now());//设置时间
        orders.setState("待支付");
        Goods goods= goodsService.selectById(orders.getGoodsId());
        if(goods==null)
        {
            throw new CustomException("货物不存在");
        }
        int store=goods.getStore()-orders.getNum();
        if(store<0)
        {
            throw new CustomException("货物库存不足");
        }
        goods.setStore(store);
        goodsService.updateById(goods);
        ordersMapper.insert(orders);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        ordersMapper.deleteById(id);
    }

    /**
     * 修改
     */
    public void updateById(Orders orders) {
        if("已取消".equals(orders.getState())){
            Integer goodsId=orders.getGoodsId();
            Goods goods=goodsService.selectById(goodsId);
            if(goods!= null)
            {
                goods.setStore(goods.getStore()+orders.getNum());
                goodsService.updateById(goods);
            }
        }
        ordersMapper.updateById(orders);
    }

    /**
     * 根据ID查询
     */
    public Orders selectById(Integer id) {
        return ordersMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Orders> selectAll(Orders orders) {
        return ordersMapper.selectAll(orders);
    }

    /**
     * 分页查询
     */
    public PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize, String userRole, String userName) {
        System.out.println("Current user role: " + userRole);
        System.out.println("Current user name: " + userName);

        PageHelper.startPage(pageNum, pageSize);

        // 清空可能存在的userName，防止干扰
        orders.setUserName(null);

        // 如果不是管理员，只能查看自己的订单
        if (!"ADMIN".equals(userRole)) {
            System.out.println("Setting filter for non-admin user: " + userName);
            orders.setUserName(userName);
        } else {
            System.out.println("Admin user - no filter applied");
        }

        List<Orders> list = ordersMapper.selectAll(orders);
        return PageInfo.of(list);
    }
    

}