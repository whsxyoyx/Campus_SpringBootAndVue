package com.oyx.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//管理员
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin {

    private Integer aid;
    private String account;
    private String password;
    private String name;
    private Date addtime;
    private Integer state;


}
