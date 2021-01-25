package com.mogul.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogul.mapper.CustomerMapper;
import com.mogul.mapper.TranHistoryMapper;
import com.mogul.mapper.TranMapper;
import com.mogul.pojo.Customer;
import com.mogul.pojo.CustomerExample;
import com.mogul.pojo.Tran;
import com.mogul.pojo.TranHistory;
import com.mogul.service.TranService;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    TranMapper tranMapper;
    @Autowired
    TranHistoryMapper tranHistoryMapper;
    @Autowired
    CustomerMapper customerMapper;


    @Override
    public PageInfo tranList(int pageno,int pagesize,String cname,String uname, String tname,String fullname,
                             String type,String source, String stage) {
        PageHelper.startPage(pageno,pagesize);
        List<Tran> trans=tranMapper.selectByTran(cname,uname,tname,fullname,type,source, stage);
        PageInfo pageInfo=new PageInfo<>(trans);
        return pageInfo;
    }

    @Override
    public int add(Tran tran) {
        CustomerExample customerExample=new CustomerExample();
        customerExample.createCriteria().andNameEqualTo(tran.getCustomerid());
        List<Customer> customers=customerMapper.selectByExample(customerExample);
        if(customers.size()==0){
            Customer customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setDescription(tran.getDescription());
            customer.setContactsummary(tran.getContactsummary());
            customer.setNextcontacttime(tran.getNextcontacttime());
            customer.setCreatetime(DateTimeUtil.getSysTime());
            customer.setCreateby(tran.getCreateby());
            customer.setName(tran.getCustomerid());
            customerMapper.insertSelective(customer);
            customers.add(customer);
        }
        tran.setCustomerid(customers.get(0).getId());
        tran.setCreatetime(DateTimeUtil.getSysTime());
        tran.setId(UUIDUtil.getUUID());
        tran.setEditby("");
        tran.setEdittime("");
        tranMapper.insertSelective(tran);
        TranHistory tranHistory=new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranid(tran.getId());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpecteddate(tran.getExpecteddate());
        tranHistory.setCreateby(tran.getCreateby());
        tranHistory.setCreatetime(DateTimeUtil.getSysTime());
        tranHistory.setStage(tran.getStage());
        tranHistoryMapper.insertSelective(tranHistory);
        return 1;
    }

    @Override
    public Tran getTran(String id) {
        return tranMapper.selectById(id);
    }

    @Override
    public int editTran(Tran tran) {
        int i=tranMapper.updateByPrimaryKeySelective(tran);
        if(i==1){
            System.out.println("更新成功!");
        }
        TranHistory history=new TranHistory();
        history.setId(UUIDUtil.getUUID());
        history.setStage(tran.getStage());
        history.setMoney(tran.getMoney());
        history.setExpecteddate(tran.getExpecteddate());
        history.setCreateby(tran.getEditby());
        history.setCreatetime(DateTimeUtil.getSysTime());
        history.setTranid(tran.getId());
        int a = tranHistoryMapper.insertSelective(history);
        if (a==1){
            System.out.println("tranHistory更新成功");
        }
        return 1;
    }
}
