package com.example.service;

import com.example.entity.Goods;
import com.example.entity.Purchase;
import com.example.mapper.GoodsMapper;
import com.example.mapper.PurchaseMapper;
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
public class PurchaseService {

    @Resource
    private PurchaseMapper purchaseMapper;

    @Resource
    private GoodsService goodsService;
    /**
     * 新增
     */
    @Transactional
    public void add(Purchase purchase) {
        Integer num = purchase.getNum();
        //查询当前进货的商品信息
        Goods goods=goodsService.selectById(purchase.getGoodsId());
        goods.setStore(goods.getStore()+num);
        goodsService.updateById(goods);//更新数据
        purchaseMapper.insert(purchase);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        purchaseMapper.deleteById(id);
    }

    /**
     * 修改
     */
    public void updateById(Purchase purchase) {
        purchaseMapper.updateById(purchase);
    }

    /**
     * 根据ID查询
     */
    public Purchase selectById(Integer id) {
        return purchaseMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Purchase> selectAll(Purchase purchase) {
        return purchaseMapper.selectAll(purchase);
    }

    /**
     * 分页查询
     */
    public PageInfo<Purchase> selectPage(Purchase purchase, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Purchase> list = purchaseMapper.selectAll(purchase);
        return PageInfo.of(list);
    }
    

}