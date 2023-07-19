package com.oyx.campus.service;

import com.oyx.campus.bean.TaskComment;

import java.util.List;

/**
 * @author oyx
 * @create 2023-04-05 12:33
 */
public interface TaskCommentService {
    int save(TaskComment comment);


    List<TaskComment> getCommentById(long tid);

    List<TaskComment>  getCommentByIdTest(long tid);


}
