package com.oyx.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//任务类型
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskType {

    private Date addtime;

    private String tasktype;

}
