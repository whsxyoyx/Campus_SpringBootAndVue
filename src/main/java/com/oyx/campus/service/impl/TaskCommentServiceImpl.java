package com.oyx.campus.service.impl;

import com.oyx.campus.bean.TaskComment;
import com.oyx.campus.mapper.TaskCommentMapper;
import com.oyx.campus.service.TaskCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author oyx
 * @create 2023-04-05 12:33
 */
@Service
public class TaskCommentServiceImpl implements TaskCommentService {

    @Autowired
    private TaskCommentMapper taskCommentMapper;
    @Override
    public int save(TaskComment comment) {
        return taskCommentMapper.insertSelective(comment);
    }

    @Override
    public List<TaskComment> getCommentById(long tid) {
        List<TaskComment> list=taskCommentMapper.getCommentById(tid);
        return  list;
    }

    @Override
    public List<TaskComment>  getCommentByIdTest(long tid) {

        return taskCommentMapper.getCommentByIdTest(tid);
    }

 /*   public TaskComment getCommentByIdTest(long tid) {

       TaskComment list=taskCommentMapper.getCommentByIdTest(tid);
        return  list;
    }*/

}
