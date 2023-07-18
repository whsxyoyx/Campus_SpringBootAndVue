package com.oyx.campus.mapper;

import com.oyx.campus.bean.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * @author oyx
 * @create 2023-07-18 10:48
 */
@SpringBootTest
public class AdminMapperTest {
    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void deleteByPrimaryKeyTest(){
        int i = adminMapper.deleteByPrimaryKey(4);
        System.out.println(i);
    }
    @Test
    public void selectAllAdminTest(){
        List<Admin> admins = adminMapper.selectAllAdmin();
        admins.forEach(System.out::println);
    }
    @Test
    public void selectByPrimaryKeyTest(){
        Admin admin = adminMapper.selectByPrimaryKey(1);
        System.out.println(admin);

    }
    @Test
    public void insertSelectiveTest(){
        Admin admin = new Admin(null,"xuan","123456","靓仔",new Date(),0);
        int i = adminMapper.insertSelective(admin);
        System.out.println(i);
    }




    @Test
    public void selectAdminByAccountTest(){
        Admin admin = adminMapper.selectAdminByAccount("admin");
        System.out.println(admin);
    }
    @Test
    public void selectAccountCountTest(){
        int i = adminMapper.selectAccountCount("admin");
        System.out.println(i);
    }
    @Test
    public void updateByPrimaryKeyTest(){
        Admin admin = new Admin(4, "炫炫来咯", "565573704", "欧阳炫", new Date(), 0);
        int i = adminMapper.updateByPrimaryKey(admin);
        System.out.println(i);
    }
    @Test
    public void updateByPrimaryKeySelectiveTest(){
        //Admin admin = new Admin(4, "炫炫来咯", "565573704", "欧阳炫", new Date(), 0);
        Admin admin = new Admin();
        admin.setAid(4);
        admin.setName("炫仔第一");
        int i = adminMapper.updateByPrimaryKeySelective(admin);
        System.out.println(i);
    }

}
