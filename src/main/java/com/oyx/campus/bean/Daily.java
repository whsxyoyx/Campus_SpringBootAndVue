package com.oyx.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


//日志类
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Daily {

    private Integer id;

    private User user;

    private String gain;

    private String question;

    private String suggest;

    private Date subtime;

    private Date lastupdatetime;

    private Integer sid;


}
