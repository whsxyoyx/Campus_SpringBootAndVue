package com.oyx.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//用户
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private School school;

    private Integer stuid;

    private String studentid;

    private String password;

    private Integer schoolid;

    private Integer sex;

    private String name;

    private Date registertime;

    private Double money;

    private Integer state;

    private String photo;

}
