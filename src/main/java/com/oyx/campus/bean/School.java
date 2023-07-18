package com.oyx.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//学校
@AllArgsConstructor
@NoArgsConstructor
@Data
public class School {

    private Integer schoolid;

    private String name;

    private Date addtime;

    private Integer state;

}
