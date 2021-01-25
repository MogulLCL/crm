package com.mogul.service.impl;

import com.mogul.mapper.CustomerMapper;
import com.mogul.pojo.Customer;
import com.mogul.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerMapper customerMapper;

    @Override
    public List<String> getCustomerName(String name) {
        return customerMapper.selectByName(name);
    }
}
