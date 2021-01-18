package com.mogul.controller.workbench;

import com.mogul.domian.Result;
import com.mogul.pojo.Clue;
import com.mogul.pojo.User;
import com.mogul.service.ClueService;
import com.mogul.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("workbench/clue")
public class ClueController {
    @Autowired
    UserService userService;
    @Autowired
    ClueService clueService;

    @RequestMapping(value = "getuser",method = RequestMethod.GET)
    public Result getUser(){
        return Result.success(userService.getName());
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public Result save(@Valid Clue clue, HttpSession httpSession){
        User user=(User)httpSession.getAttribute("user");
        clue.setCreateby(user.getName());
        return Result.success(clueService.save(clue));
    }
    @RequestMapping(value = "pagelist",method = RequestMethod.GET)
    public Result pageList(@RequestParam(value = "pageno",defaultValue = "1") Integer pageno,
                           @RequestParam(value = "pagesize",defaultValue = "5") Integer pagesize,
                           Clue clue){
        return Result.success(clueService.pageList(pageno,pagesize,clue));
    }
    @RequestMapping(value = "delete",method = RequestMethod.GET)
    public Result delete(String id){
        return Result.success(clueService.delete(id));
    }
    @RequestMapping(value = "getuserandclue",method = RequestMethod.POST)
    public Result getUserAndClue(String id){
        return Result.success(clueService.getUSerAndClue(id));
    }
    @RequestMapping(value = "edit",method = RequestMethod.POST)
    public Result edit(Clue clue,HttpSession httpSession){
        return Result.success("");
    }

    /**
     * detail
     * @param id
     * @return
     */
    @RequestMapping(value = "getdetail",method = RequestMethod.GET)
    public Result getDetail(String id){
        return Result.success(clueService.getDetail(id));
    }






    @RequestMapping(value = "getclueandactivity",method = RequestMethod.GET)
    public Result getIdAndActivity(String id){
        return Result.success(clueService.getClueAndActivity(id));
    }

    @RequestMapping(value = "deleteclueandactivity",method = RequestMethod.GET)
    public Result deleteClueAndActivity(String id){
        return Result.success(clueService.deleteClueAndActivity(id));
    }

    @RequestMapping(value = "getactivity",method = RequestMethod.GET)
    public Result getActivity(String name,String id){
        return Result.success(clueService.getActivity(id,name));
    }
    @RequestMapping(value = "getactivitybyname",method = RequestMethod.GET)
    public Result getActivity(String aname){
        return Result.success(clueService.getActivityByName(aname));
    }
    @RequestMapping(value = "addclueactivity",method = RequestMethod.POST)
    public Result addClueActivity(String cid,String aid){
        System.out.println(cid);
        System.out.println(aid);
        return Result.success(clueService.addClueActivity(cid,aid));
    }

    /**
     * 线索转换
     */
    @RequestMapping(value = "addCosCon",method = RequestMethod.POST)
    public Result addCosCon(String id,int flag,String money,String name,String expecteddate,String stage,String activityid,HttpSession httpSession){
        User user=(User) httpSession.getAttribute("user");
        return Result.success(clueService.addCosCon(id,user,flag,money,name,expecteddate,stage,activityid));
    }

}
