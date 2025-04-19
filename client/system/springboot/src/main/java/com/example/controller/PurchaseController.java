package com.example.controller;

import com.example.common.Result;
import com.example.entity.Purchase;
import com.example.service.PurchaseService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统商品前端操作接口
 **/
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Resource
    private PurchaseService purchaseService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Purchase purchase) {
        purchaseService.add(purchase);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        purchaseService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Purchase purchase) {
        purchaseService.updateById(purchase);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Purchase purchase = purchaseService.selectById(id);
        return Result.success(purchase);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Purchase purchase) {
        List<Purchase> list = purchaseService.selectAll(purchase);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Purchase purchase,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Purchase> page = purchaseService.selectPage(purchase, pageNum, pageSize);
        return Result.success(page);
    }

}