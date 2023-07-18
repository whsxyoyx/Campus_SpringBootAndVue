package com.oyx.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/*
   state
            0：待解决
            1：关闭任务
            2：解决中
            3：已完成
   */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {

    private Integer taskid;

    private String publishUserId;

    private String publishUserName;

    private Integer publishSchoolId;

    private Integer acceptUserId;

    private Double reward;

    private Date addtime;

    private Date endtime;

    private String taskname;

    private String taskcontext;

    private Integer state;

    private String img;

    private String tasktype;


}
