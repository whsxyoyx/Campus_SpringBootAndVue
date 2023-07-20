package com.oyx.campus.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oyx.campus.bean.Daily;
import com.oyx.campus.bean.Msg;
import com.oyx.campus.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/daily")
public class DailyController {
    @Autowired
    private DailyService dailyService;
    //查询所有日志
    @RequestMapping("/getAllDaily/{uid}/{pn}/{pageSize}")
    public Msg getAllDaily(@PathVariable("uid")String uid,@PathVariable("pn")Integer pn,
                           @PathVariable("pageSize")Integer pageSize){
        PageHelper.startPage(pn,pageSize);
        List<Daily> list = dailyService.getAllDaily(uid);
        Daily daily = list.get(0);
        System.out.println(daily);
        Integer stuid = daily.getUser().getStuid();
        PageInfo<Daily> pageInfo = new PageInfo<Daily>(list);
        return Msg.success().add("list",pageInfo).add("sid",stuid);

    }
    //根据id查询日志信息
    @RequestMapping("/getDaily/{id}")
    public Msg getDaily(@PathVariable("id") Integer id){
        Daily dailyById = dailyService.getDailyById(id);
        return  Msg.success().add("daily",dailyById);
    }
    //添加日志
    @RequestMapping("/addDaily")
    public Msg addDaily(@RequestBody Daily daily){
        daily.setSubtime(new Date());
        System.out.println(daily);
        int i= dailyService.addDaily(daily);
        if(i>0){
            return  Msg.success("添加成功");
        }else{
            return  Msg.fail("添加失败");
        }
    }
    //根据id查询日志信息
    @RequestMapping("/getDailyById/{id}")
    public Msg getDailyById(@PathVariable("id") Integer id){
        Daily dailyById = dailyService.getDailyById(id);
        return  Msg.success().add("daily",dailyById);
    }
    //修改日志
    @RequestMapping("/updateDaily")
    public Msg updateDailyById(@RequestBody Daily daily){
        System.out.println(daily);
        daily.setLastupdatetime(new Date());
        int i = dailyService.updateDailyById(daily);
        if(i>0){
            return  Msg.success("修改成功");
        }else{
            return  Msg.fail("修改失败");
        }
    }
    //删除日志
    @RequestMapping("/delDailyById/{id}")
    public Msg delDailyById(@PathVariable("id") Integer id){
        int i = dailyService.delDailyById(id);
        if(i>0){
            return  Msg.success("删除成功");
        }else{
            return  Msg.fail("删除失败");
        }
    }
    //批量删除日志
    @RequestMapping("/delDailys/{dIds}")
    public Msg delDailys(@PathVariable("dIds") String dIds){
        String[] split = dIds.split("-");
        Integer[] ids=new Integer[20];
        for(int i = 0;i<split.length;i++){
            ids[i]=Integer.parseInt(split[i]);
            System.out.print(ids[i]);
        }
        int i = dailyService.delDailys(Arrays.asList(ids));
        if(i>0){
            return  Msg.success("删除成功");
        }else{
            return  Msg.fail("删除失败");
        }
    }


}
