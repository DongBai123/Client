package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.entity.Invoice;
import com.example.mapper.InvoiceMapper;
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
public class InvoiceService {

    @Resource
    private InvoiceMapper invoiceMapper;

    /**
     * 新增
     */
    @Transactional
    public void add(Invoice invoice) {
        invoice.setState("待申请");
        invoice.setTime(DateUtil.now());
        invoice.setInvoiceNum(IdUtil.fastSimpleUUID());

        invoiceMapper.insert(invoice);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        invoiceMapper.deleteById(id);
    }

    /**
     * 修改
     */
    public void updateById(Invoice invoice) {
        invoiceMapper.updateById(invoice);
    }

    /**
     * 根据ID查询
     */
    public Invoice selectById(Integer id) {
        return invoiceMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Invoice> selectAll(Invoice invoice) {
        return invoiceMapper.selectAll(invoice);
    }

    /**
     * 分页查询
     */

    /**
     * 分页查询
     */
    /**
     * 分页查询
     */
    public PageInfo<Invoice> selectPage(Invoice invoice, Integer pageNum, Integer pageSize, String userRole, String userName) {
        System.out.println("Service - Processing request for userRole: " + userRole);

        PageHelper.startPage(pageNum, pageSize);

        // 重要：在设置用户名之前先清空
        invoice.setUserName(null);

        // 只有非管理员用户才设置用户名过滤
        if (!"ADMIN".equalsIgnoreCase(userRole)) {
            System.out.println("Setting user filter for non-admin user: " + userName);
            invoice.setUserName(userName);
        } else {
            System.out.println("Admin user detected - no user filter will be applied");
        }

        List<Invoice> list = invoiceMapper.selectAll(invoice);
        return PageInfo.of(list);
    }

}