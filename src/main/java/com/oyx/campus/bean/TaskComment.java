package com.oyx.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskComment {

    private Long commonId;

    private Long taskId;

    private String username;

    private Long userId;

    private String img;

    private String content;

    private Long parentCommentId;

    private Date createtime;

    private Date updatetime;

    private String target;

    //用于实现二级评论
    private List<ReplyComment> children;
}
