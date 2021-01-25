package com.mogul.test;

import com.github.pagehelper.PageInfo;
import com.mogul.mapper.*;
import com.mogul.pojo.*;
import com.mogul.service.ClueService;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test1 {
    @Autowired
    ClueService clueService;
    @Autowired
    ClueMapper clueMapper;
    @Autowired
    CustomerMapper customerMapper;
    //联系人表
    @Autowired
    ContactsMapper contactsMapper;
    //交易记录
    @Autowired
    TranMapper tranMapper;
    @Autowired
    TranHistoryMapper tranHistoryMapper;

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

    @Test
    public void testClueAndActivity(){
        Activity activity=new Activity();
        activity.setId("004c3e7a839b4ee3a7f8fb5cf15a9188");
        activity.setName("采集");
        List<Activity> clues=clueMapper.selectByActivity("004c3e7a839b4ee3a7f8fb5cf15a9188","采集");
        System.out.println(clues.get(0).getOwner());
    }
    @Test
    public void testConandCus(){
        Clue clue=clueMapper.selectByPrimaryKey("000b40cdafec45c0a87d2342e4c29715");
        if(null==clue) System.out.println("失败");
        Customer customer=new Customer();
        Contacts contacts=new Contacts();
        customer.setId(UUIDUtil.getUUID());
        customer.setOwner(clue.getOwner());
        customer.setName(clue.getFullname());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateby("张三");
        customer.setCreatetime(DateTimeUtil.getSysTime());
        customer.setContactsummary(clue.getContactsummary());
        customer.setNextcontacttime(clue.getNextcontacttime());
        customer.setDescription(clue.getDescription());
        customer.setAddress(clue.getAddress());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerid(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setBirth("");
        contacts.setCreateby("张三");
        contacts.setCreatetime(DateTimeUtil.getSysTime());
        contacts.setDescription(clue.getDescription());
        contacts.setContactsummary(clue.getContactsummary());
        contacts.setNextcontacttime(clue.getNextcontacttime());
        contacts.setAddress(clue.getAddress());
        customerMapper.insertSelective(customer);
        contactsMapper.insertSelective(contacts);
        if(1==1){
            Tran tran=new Tran();
            tran.setId(UUIDUtil.getUUID());
            tran.setOwner(clue.getOwner());
            tran.setMoney("1000000");
            tran.setName("mogul");
            tran.setExpecteddate("2020-11-11");
            tran.setCustomerid(customer.getId());
            tran.setStage("90");
            tran.setActivityid("");
            tran.setContactsid(contacts.getId());
            tran.setCreateby("张三");
            tran.setCreatetime(DateTimeUtil.getSysTime());
            tranMapper.insertSelective(tran);
        }
        clueMapper.deleteByPrimaryKey("000b40cdafec45c0a87d2342e4c29715");
    }

    @Test
    public void testTran(){
        List<Tran> trans=tranMapper.selectByTran("","","","","","","");
        for(Tran t:trans){
            System.out.println(t);
        }
    }

    @Test
    public void testContacts(){
        for(Contacts c:contactsMapper.selectByName("m")){
            System.out.println(c.getFullname());
        }
    }

    @Test
    public void testTageAndPossibility(){
        ResourceBundle bundle=ResourceBundle.getBundle("tageAndPossibility");
        Enumeration<String> keys = bundle.getKeys();
        Map<String,String> map=new HashMap<>();
        while (keys.hasMoreElements()){
            String key=keys.nextElement();
            map.put(key,bundle.getString(key));
        }
        System.out.println(map);
    }

    @Test
    public void stageCount(){
        List<Map<String, Object>> mapList=tranHistoryMapper.stageCount();
        Map<String, Object> map=new HashMap<>();
        Integer total=0;
        Iterator<Map<String, Object>> iterator=mapList.iterator();
        while (iterator.hasNext())
            total+=Integer.parseInt(iterator.next().get("value").toString());
        map.put("total",total);
        map.put("datalist",mapList);
        System.out.println(map);
    }
}
