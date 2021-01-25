package com.mogul.controller.workbench;

import com.mogul.domian.Result;
import com.mogul.pojo.Customer;
import com.mogul.pojo.Tran;
import com.mogul.pojo.TranHistory;
import com.mogul.pojo.User;
import com.mogul.service.*;
import com.mogul.util.DateTimeUtil;
import com.mogul.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping("workbench/tran")
public class TranController {

    @Autowired
    TranService tranService;
    @Autowired
    UserService userService;
    @Autowired
    ClueService clueService;
    @Autowired
    ContactsService contactsService;
    @Autowired
    CustomerService customerService;
    @Autowired
    TranHistoryService tranHistoryService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Result tranList(@RequestParam(value = "pageno",defaultValue = "1") int pageno,@RequestParam(value = "pagesize",defaultValue = "5")int pagesize,
                           String cname,String uname, String tname,String fullname, String type,String source, String stage){
        return Result.success(tranService.tranList(pageno,pagesize,cname,uname,tname,fullname,type,source, stage));
    }

    @RequestMapping(value = "getuser",method = RequestMethod.GET)
    public Result getUser(){
        return Result.success(userService.getName());
    }

    @RequestMapping(value = "getactivitybyname",method = RequestMethod.GET)
    public Result getActivity(String aname){
        return Result.success(clueService.getActivityByName(aname));
    }

    @RequestMapping(value = "getcontactsbyname",method = RequestMethod.GET)
    public Result getContactsByName(String name){
        return Result.success(contactsService.getContactsByName(name));
    }

    @RequestMapping(value = "getcustomername",method = RequestMethod.GET)
    public Result getCustomerName(String name){
        return Result.success(customerService.getCustomerName(name));
    }

    @RequestMapping(value = "getpossibility",method = RequestMethod.GET)
    public Result test(HttpServletRequest httpServletRequest){
        Map<String,String> map=(Map)httpServletRequest.getSession().getServletContext().getAttribute("pMap");
        return Result.success(map);
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public ModelAndView addTran(Tran tran, HttpSession httpSession){
        User user=(User) httpSession.getAttribute("user");
        tran.setCreateby(user.getName());
        tranService.add(tran);
        ModelAndView modelAndView=new ModelAndView("workbench/transaction/index");
        return modelAndView;
    }

    @RequestMapping(value = "gettranid",method = RequestMethod.GET)
    public Result getTranId(String id){
        return Result.success(tranService.getTran(id));
    }

    @RequestMapping(value = "gettranhistorybytranid",method = RequestMethod.GET)
    public Result getTranHistoryByTranId(String id){
        return Result.success(tranHistoryService.selectTranHistoryByTranId(id));
    }

    @RequestMapping(value = "getstage",method = RequestMethod.GET)
    public Result getStage(HttpSession httpSession){
        return Result.success(httpSession.getServletContext().getAttribute("stage"));
    }

    @RequestMapping(value = "updatetran",method = RequestMethod.POST)
    public Result updateTranAndTranHistroy(Tran tran,HttpSession httpSession){
        User user=(User) httpSession.getAttribute("user");
        tran.setEditby(user.getName());
        tran.setEdittime(DateTimeUtil.getSysTime());
        return Result.success(tranService.editTran(tran));
    }

    @RequestMapping(value = "getstagecount",method = RequestMethod.GET)
    public Result getStageCount(){
        return Result.success(tranHistoryService.stageCount());
    }
}
