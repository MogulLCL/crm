package com.mogul.service;

import com.mogul.pojo.Contacts;

import java.util.List;

public interface ContactsService {
    List<Contacts> getContactsByName(String name);
}
