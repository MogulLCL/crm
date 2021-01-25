package com.mogul.service.impl;

import com.mogul.mapper.ContactsMapper;
import com.mogul.pojo.Contacts;
import com.mogul.pojo.ContactsExample;
import com.mogul.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    ContactsMapper contactsMapper;

    @Override
    public List<Contacts> getContactsByName(String name) {
        return contactsMapper.selectByName(name);
    }
}
