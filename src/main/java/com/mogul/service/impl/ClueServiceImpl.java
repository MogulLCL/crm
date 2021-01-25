package com.mogul.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mogul.mapper.*;
import com.mogul.pojo.*;
import com.mogul.service.ClueService;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ClueServiceImpl implements ClueService {
    //线索表
    @Autowired
    ClueMapper clueMapper;
    @Autowired
    ClueRemarkMapper clueRemarkMapper;
    //线索市场活动关联表
    @Autowired
    ClueActivityRelationMapper clueActivityRelationMapper;

    //客户表
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CustomerRemarkMapper customerRemarkMapper;

    //联系人表
    @Autowired
    ContactsMapper contactsMapper;
    @Autowired
    ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    ContactsActivityRelationMapper contactsActivityRelationMapper;

    //交易记录
    @Autowired
    TranMapper tranMapper;
    @Autowired
    TranHistoryMapper tranHistoryMapper;

    //用户表
    @Autowired
    UserMapper userMapper;

    @Override
    public int save(Clue clue) {
        clue.setId(UUIDUtil.getUUID());
        clue.setCreatetime(DateTimeUtil.getSysTime());
        return clueMapper.insertSelective(clue);
    }

    @Override
    public int addClueActivity(String cid, String aid) {
        String []aids=aid.split(",");
        if(aids.length==0){
            return 0;
        }
        for(String a:aids){
            ClueActivityRelation clueActivityRelation=new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueid(cid);
            clueActivityRelation.setActivityid(a);
            clueActivityRelationMapper.insertSelective(clueActivityRelation);
        }
        return 1;
    }

    /**
     * 添加客户和联系人
     * @param id
     * @param flag
     * @return
     */
    @Override
    public int addCosCon(String id,int flag, User user,Tran t) {
        Clue clue=clueMapper.selectByPrimaryKey(id);
        if(null==clue)return 0;
        //创建客户和联系人
        CustomerExample customerExample=new CustomerExample();
        customerExample.createCriteria().andNameEqualTo(clue.getCompany());
        List<Customer> customers=customerMapper.selectByExample(customerExample);
        if(0==customers.size()) {
            Customer customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setName(clue.getCompany());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateby(user.getName());
            customer.setCreatetime(DateTimeUtil.getSysTime());
            customer.setContactsummary(clue.getContactsummary());
            customer.setNextcontacttime(clue.getNextcontacttime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());
            customerMapper.insertSelective(customer);
            customers.add(customer);
        }
            Contacts contacts = new Contacts();
            contacts.setId(UUIDUtil.getUUID());
            contacts.setOwner(clue.getOwner());
            contacts.setSource(clue.getSource());
            contacts.setCustomerid(customers.get(0).getId());
            contacts.setFullname(clue.getFullname());
            contacts.setAppellation(clue.getAppellation());
            contacts.setEmail(clue.getEmail());
            contacts.setMphone(clue.getMphone());
            contacts.setJob(clue.getJob());
            contacts.setBirth("");
            contacts.setCreateby(user.getName());
            contacts.setCreatetime(DateTimeUtil.getSysTime());
            contacts.setDescription(clue.getDescription());
            contacts.setContactsummary(clue.getContactsummary());
            contacts.setNextcontacttime(clue.getNextcontacttime());
            contacts.setAddress(clue.getAddress());
            contactsMapper.insertSelective(contacts);
            //创建备注
            ClueRemarkExample clueRemarkExample=new ClueRemarkExample();
            clueRemarkExample.createCriteria().andClueidEqualTo(clue.getId());
            List<ClueRemark> clueRemarks=clueRemarkMapper.selectByExample(clueRemarkExample);
            if(0!=clueRemarks.size()){
                for(ClueRemark c:clueRemarks){
                    ContactsRemark contactsRemark=new ContactsRemark();
                    contactsRemark.setId(UUIDUtil.getUUID());
                    contactsRemark.setContactsid(contacts.getId());
                    contactsRemark.setNotecontent(c.getNotecontent());
                    contactsRemark.setCreateby(c.getCreateby());
                    contactsRemark.setCreatetime(c.getCreatetime());
                    contactsRemark.setEditby(c.getEditby());
                    contactsRemark.setEdittime(c.getEdittime());
                    contactsRemark.setEditflag(c.getEditflag());
                    CustomerRemark customerRemark=new CustomerRemark();
                    customerRemark.setId(UUIDUtil.getUUID());
                    customerRemark.setCustomerid(customers.get(0).getId());
                    customerRemark.setNotecontent(c.getNotecontent());
                    customerRemark.setCreateby(c.getCreateby());
                    customerRemark.setCreatetime(c.getCreatetime());
                    customerRemark.setEditby(c.getEditby());
                    customerRemark.setEdittime(c.getEdittime());
                    customerRemark.setEditflag(c.getEditflag());
                    contactsRemarkMapper.insertSelective(contactsRemark);
                    customerRemarkMapper.insertSelective(customerRemark);
                    clueRemarkMapper.deleteByPrimaryKey(c.getId());
                }
            }
            //创建联系人和市场活动
            ClueActivityRelationExample clueActivityRelationExample=new ClueActivityRelationExample();
            clueActivityRelationExample.createCriteria().andClueidEqualTo(clue.getId());
            List<ClueActivityRelation> clueActivityRelations=clueActivityRelationMapper.selectByExample(clueActivityRelationExample);
            if(0!=clueActivityRelations.size()){
                for(ClueActivityRelation car:clueActivityRelations){
                    ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
                    contactsActivityRelation.setId(UUIDUtil.getUUID());
                    contactsActivityRelation.setActivityid(car.getActivityid());
                    contactsActivityRelation.setContactsid(contacts.getId());
                    contactsActivityRelationMapper.insertSelective(contactsActivityRelation);
                    clueActivityRelationMapper.deleteByPrimaryKey(car.getId());
                }
            }
            //创建交易
            if (flag == 1) {
                TranExample tranExample=new TranExample();
                tranExample.createCriteria().andNameEqualTo(t.getName());
                List<Tran> trans=tranMapper.selectByExample(tranExample);

                if(0!=trans.size()) {
                    TranHistory tranHistory=new TranHistory();
                    tranHistory.setId(UUIDUtil.getUUID());
                    tranHistory.setStage(t.getStage());
                    tranHistory.setCreateby(user.getName());
                    tranHistory.setCreatetime(DateTimeUtil.getSysTime());
                    tranHistory.setExpecteddate(t.getExpecteddate());
                    tranHistory.setMoney(t.getMoney());
                    tranHistory.setTranid(trans.get(0).getId());
                    tranHistoryMapper.insertSelective(tranHistory);
                }else {
                    Tran tran = new Tran();
                    tran.setId(UUIDUtil.getUUID());
                    tran.setOwner(clue.getOwner());
                    tran.setMoney(t.getMoney());
                    tran.setName(t.getName());
                    tran.setExpecteddate(t.getExpecteddate());
                    tran.setCustomerid(customers.get(0).getId());
                    tran.setStage(t.getStage());
                    tran.setType("新业务");
                    tran.setActivityid(t.getActivityid());
                    tran.setContactsid(contacts.getId());
                    tran.setCreateby(user.getName());
                    tran.setCreatetime(DateTimeUtil.getSysTime());
                    tran.setDescription(clue.getDescription());
                    tran.setContactsummary(clue.getContactsummary());
                    tran.setNextcontacttime(clue.getNextcontacttime());
                    tran.setSource(clue.getSource());
                    tranMapper.insertSelective(tran);
                }
            }
            clueMapper.deleteByPrimaryKey(id);
        return 1;
    }

    @Override
    public List<Activity> getActivityByName(String aname) {
        return clueMapper.selectActivityByName(aname);
    }

    @Override
    public List<Activity> getActivity(String id, String name) {
        return clueMapper.selectByActivity(id,name);
    }

    @Override
    public List<Activity> getClueAndActivity(String id) {
        return clueMapper.selectByClueAndActivity(id);
    }

    @Override
    public int deleteClueAndActivity(String id) {
        return clueActivityRelationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo pageList(Integer pageno, Integer pagesize, Clue clue) {
        PageHelper.startPage(pageno,pagesize);
        ClueExample clueExample=new ClueExample();
        clueExample.setOrderByClause("a.createTime DESC");
        PageInfo pageInfo=new PageInfo<>(clueMapper.selectByJoin(clueExample));
        return pageInfo;
    }

    @Override
    public PageInfo pageList1(Integer pageno, Integer pagesize, Clue clue) {
        PageHelper.startPage(pageno,pagesize);
        ClueExample clueExample=new ClueExample();
        clueExample.setOrderByClause("createTime DESC");
        List<Clue> clues=clueMapper.selectByExample(clueExample);
        for(Clue c:clues)
            c.setOwner(userMapper.selectByPrimaryKey(c.getOwner()).getName());
        PageInfo pageInfo=new PageInfo<>(clues);
        return pageInfo;
    }

    @Override
    public Map<String, Object> getUSerAndClue(String id) {
        Map<String,Object> map=new HashMap<>();
        map.put("u",userMapper.selectByName());
        map.put("c",clueMapper.selectByPrimaryKey(id));
        return map;
    }

    @Override
    public int delete(String id) {
        return clueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Clue getDetail(String id) {
        Clue clue=clueMapper.selectByPrimaryKey(id);
        if(clue!=null)clue.setOwner(userMapper.selectByPrimaryKey(clue.getOwner()).getName());
        return clue;
    }
}
