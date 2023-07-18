package com.oyx.campus.mapper;

import com.oyx.campus.bean.Daily;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author oyx
 * @create 2023-07-18 15:53
 */
@SpringBootTest
public class DailyMapperTest {
    @Autowired
    private DailyMapper dailyMapper;

    @Test
    public void getAllDaily(){
        List<Daily> allDaily = dailyMapper.getAllDaily("2021204010222");
        allDaily.forEach(System.out::println);
    }

    @Test
    public void selectByPrimaryKeyTest(){
        Daily daily = dailyMapper.selectByPrimaryKey(5);
        System.out.println(daily);
    }

    @Test
    public void insertSelectiveTest(){
        Daily daily = new Daily();
        daily.setQuestion("马上就要就业了，害怕");
        daily.setGain("干就完了");
        daily.setSuggest("不要慌，有我在");
        daily.setSubtime(new Date());
        daily.setSid(1);
        dailyMapper.insertSelective(daily);
    }
    @Test
    public void updateByPrimaryKeySelectiveTest(){
        Daily daily = new Daily();
        daily.setId(5);
        daily.setGain("干就完了");
        daily.setSuggest("不要慌，有我在");
        dailyMapper.updateByPrimaryKeySelective(daily);
    }
    @Test
    public void deleteDailyByIdsTest(){
        List<Integer> list =new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(4);
        int i = dailyMapper.deleteDailyByIds(list);
        System.out.println(i);
    }
    @Test
    public void deleteByPrimaryKeyTest(){
        dailyMapper.deleteByPrimaryKey(4);
    }
}
