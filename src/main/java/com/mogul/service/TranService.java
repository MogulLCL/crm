package com.mogul.service;

import com.github.pagehelper.PageInfo;
import com.mogul.pojo.Tran;

public interface TranService {
    PageInfo tranList(int pageno,int pagesize,String cname,String uname, String tname,String fullname,
                      String type,String source, String stage);

    int add(Tran tran);

    Tran getTran(String id);

    int editTran(Tran tran);
}
