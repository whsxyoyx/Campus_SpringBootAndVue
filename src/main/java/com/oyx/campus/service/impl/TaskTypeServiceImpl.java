package com.oyx.campus.service.impl;

import com.oyx.campus.bean.TaskType;
import com.oyx.campus.mapper.TaskTypeMapper;
import com.oyx.campus.service.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {
    @Autowired
    private TaskTypeMapper taskTypeMapper;
    @Override
    public List<TaskType> getAllTaskType() {

        return taskTypeMapper.selectAllTaskType();
    }

    @Override
    public int addTaskType(TaskType taskType)
    {
        return taskTypeMapper.insert(taskType);
    }

    @Override
    public int delTaskType(String tasktype) {
        return taskTypeMapper.delTaskType(tasktype);
    }

    @Override
    public int updateTaskType(TaskType  taskType,String name) {
        String tasktype = taskType.getTasktype();
        return taskTypeMapper.updateTaskType(tasktype, name);
    }
}
