package com.mogul.test;

import com.github.pagehelper.PageInfo;
import com.mogul.pojo.Clue;
import com.mogul.service.ClueService;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test1 {
    @Autowired
    ClueService clueService;

    @Test
    public void addClue(){
        Clue clue=new Clue();
        clue.setId(UUIDUtil.getUUID());
        clue.setCreatetime(DateTimeUtil.getSysTime());
        clue.setCreateby("李四");
        clue.setAddress("地球村");
        clue.setAppellation("博士");
        clue.setCompany("alibaba");
        clue.setContactsummary("fbiwlbvewbfofwobbwbfrb给i让我广播");
        clue.setDescription("vefubavufebvub");
        clue.setEmail("2222222@qq.com");
        clue.setState("将来联系");
        clue.setPhone("188888888");
        clue.setMphone("199999999");
        clue.setWebsite("www.alibaba.com");
        clue.setJob("ccbb");
        clue.setFullname("mogul");
        clue.setNextcontacttime("2020-12-08");
        clue.setOwner("06f5fc056eac41558a964f96daa7f27c");
        clue.setSource("web调研");
        Long startTime=System.currentTimeMillis();
        for(int a=1000000;a >=0;a--){
            clue.setFullname("mogul"+a);
            System.out.println(clueService.save(clue));
            System.out.println(1);
        }
        Long endTime=System.currentTimeMillis();
        System.out.println(endTime-startTime+"毫秒");
        //System.out.println(clueService.pageList(1,5,new Clue()));
    }
    @Test
    public void testClueControllerDetail(){
        PageInfo list=clueService.pageList(1,1,new Clue());
        Clue clue= (Clue) list.getList().get(0);
        System.out.println(clueService.getDetail(clue.getId()).getOwner());
    }

}
