package com.oyx.campus.mapper;

import com.oyx.campus.bean.TaskComment;

import java.util.List;

/**
 * @author oyx
 * @create 2023-07-19 9:46
 */
public interface TaskCommentMapper {


    /*
    *
    * */
    List<TaskComment> getCommentByIdTest(long id);
    /*
    * 根据任务id查找所属的评论列表----目前不使用该方式
    * */
    List<TaskComment> getCommentById(long id);

    /*
    * 添加评论
    * */
    int insertSelective(TaskComment taskComment);
}
