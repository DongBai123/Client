package com.example.controller;

import com.example.common.Result;
import com.example.entity.Invoice;
import com.example.service.InvoiceService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统商品前端操作接口
 **/
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Resource
    private InvoiceService invoiceService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Invoice invoice) {
        invoiceService.add(invoice);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        invoiceService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Invoice invoice) {
        invoiceService.updateById(invoice);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Invoice invoice = invoiceService.selectById(id);
        return Result.success(invoice);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Invoice invoice) {
        List<Invoice> list = invoiceService.selectAll(invoice);
        return Result.success(list);
    }


    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Invoice invoice,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String userRole,
                             @RequestParam(required = false) String userName) {
        // 添加日志
        System.out.println("Controller - Received userRole: " + userRole);
        System.out.println("Controller - Received userName: " + userName);

        PageInfo<Invoice> page = invoiceService.selectPage(invoice, pageNum, pageSize, userRole, userName);
        return Result.success(page);
    }
}