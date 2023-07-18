package com.oyx.campus.service.impl;

import com.oyx.campus.bean.Daily;
import com.oyx.campus.mapper.DailyMapper;
import com.oyx.campus.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DailyServiceImpl implements DailyService {
    @Autowired
    private DailyMapper dailyMapper;
    @Override
    public List<Daily> getAllDaily(String uid) {

        return dailyMapper.getAllDaily(uid);
    }

    @Override
    public Daily getDailyById(Integer id) {
        return dailyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addDaily(Daily daily) {
        return dailyMapper.insertSelective(daily);
    }

    @Override
    public int updateDailyById(Daily daily) {
        return dailyMapper.updateByPrimaryKeySelective(daily);
    }

    @Override
    public int delDailyById(Integer id) {
        return dailyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int delDailys(List<Integer> ids) {

        return dailyMapper.deleteDailyByIds(ids);
    }
}
