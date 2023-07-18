package com.oyx.campus.mapper;


import com.oyx.campus.bean.TaskType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author oyx
 * @create 2023-07-18 19:48
 */
@SpringBootTest
public class TaskTypeMapperTest {
    @Autowired
    private TaskTypeMapper taskTypeMapper;

    @Test
    @DisplayName("更新任务类型")
    public void updateTaskTypeTest(){
        int i = taskTypeMapper.updateTaskType("失物招领", "欧阳炫炫");
        assertEquals(1,i,"更新失败");
    }
    @Test
    public void delTaskTypeTest(){
        int nihao = taskTypeMapper.delTaskType("nihao");
        assertEquals(1,nihao,"删除失败");
    }
    @Test
    public void insertTest(){
        int i = taskTypeMapper.insert(new TaskType(new Date(),"炫仔牌洗发水"));
        assertEquals(1,i,"添加失败");
    }
    @Test
    public void selectAllTaskTypeTest(){
        List<TaskType> list = taskTypeMapper.selectAllTaskType();
        list.forEach(System.out::println);

    }

}
