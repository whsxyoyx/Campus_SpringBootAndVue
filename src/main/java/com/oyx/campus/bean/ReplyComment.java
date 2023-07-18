package com.oyx.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyComment {

    private Long id;

    private Long taskId;

    private String content;

    private String userName;

    private Date createtime;

    private Long replyId;

    private String img;


}
