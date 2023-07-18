package com.oyx.campus.service;

import com.oyx.campus.bean.TaskType;

import java.util.List;

public interface TaskTypeService {
    //查询所有任务类型

    public List<TaskType> getAllTaskType();

    int addTaskType(TaskType taskType);

    int delTaskType(String tasktype);

    int updateTaskType(TaskType taskType,String name);
}
