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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    //线索表
    @Autowired
    ClueMapper clueMapper;
    //用户表
    @Autowired
    UserMapper userMapper;
    //线索市场活动关联表
    @Autowired
    ClueActivityRelationMapper clueActivityRelationMapper;
    //客户表
    @Autowired
    CustomerMapper customerMapper;
    //联系人表
    @Autowired
    ContactsMapper contactsMapper;

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
     * @param money
     * @param name
     * @param expecteddate
     * @param stage
     * @param activityid
     * @return
     */
    @Override
    public int addCosCon(String id, User user, int flag, String money, String name, String expecteddate, String stage, String activityid) {
        Clue clue=clueMapper.selectByPrimaryKey(id);
        if(null==clue)return 0;
        Customer customer=new Customer();
        Contacts contacts=new Contacts();
        customer.setId(UUIDUtil.getUUID());
        customer.setOwner(clue.getOwner());
        customer.setName(clue.getFullname());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateby(user.getName());
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
        contacts.setCreateby(user.getName());
        contacts.setCreatetime(DateTimeUtil.getSysTime());
        contacts.setDescription(clue.getDescription());
        contacts.setContactsummary(clue.getContactsummary());
        contacts.setNextcontacttime(clue.getNextcontacttime());
        contacts.setAddress(clue.getAddress());
        customerMapper.insertSelective(customer);
        contactsMapper.insertSelective(contacts);
        if(flag==1){

        }
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
