package com.oyx.campus.service;

import com.oyx.campus.bean.Daily;

import java.util.List;

public interface DailyService {
    //查询所有日志
    List<Daily>getAllDaily(String uid);
    //根据id查询日志信息
    Daily getDailyById(Integer id);
    //添加日志
    int addDaily(Daily daily);
    //修改日志
    int updateDailyById(Daily daily);
    //删除日志
    int delDailyById(Integer id);
    //批量删除日志
    int delDailys(List<Integer> ids);

}
