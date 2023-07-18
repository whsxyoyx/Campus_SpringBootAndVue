package com.oyx.campus.mapper;

import com.oyx.campus.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author oyx
 * @create 2023-07-18 17:23
 */
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;


    @Test
    public void selectByPrimaryKeyTest(){
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }
    @Test
    public void selectByStudentIdTest(){
        User user = userMapper.selectByStudentId("2021204010222");
        System.out.println(user);
    }
    @Test
    public void selectAllUserTest(){
        List<User> list = userMapper.selectAllUser();
        list.forEach(System.out::println);
    }
    @Test
    public void checkStudentIdTest(){
        int i = userMapper.checkStudentId("2021204010222");
        System.out.println(i);
    }
    @Test
    public void updateByPrimaryKeySelectiveTest(){
        User user = new User();
        user.setStuid(8);
        user.setName("炫炫最帅");
        user.setMoney(5201314d);
        userMapper.updateByPrimaryKeySelective(user);
    }
    @Test
    public void getUsersByLikeTest(){
        List<User> list = userMapper.getUsersByLike("炫");
        list.forEach(System.out::println);
    }
    @Test
    public void getUsersByLikeAndSchoolIdTest(){
        List<User> list = userMapper.getUsersByLikeAndSchoolId("欧", 1);
        list.forEach(System.out::println);
    }
    @Test
    public void deleteByPrimaryKeyTest(){
        int i = userMapper.deleteByPrimaryKey(9);
        System.out.println(i);
    }

    @Test
    public void deleteUserByIdsTest(){
        int i = userMapper.deleteUserByIds(Arrays.asList(10,11,12,13));
        System.out.println(i);
    }
    @Test
    public void getLimitedUserTest(){
        List<User> list = userMapper.getLimitedUser();
        list.forEach(System.out::println);
    }
    @Test
    public void openAllLimitedUsersTest(){
        int i = userMapper.openAllLimitedUsers();
        System.out.println(i);
    }
    @Test
    public void getSchoolNameByStudentIdTest(){
        User schoolNameByStudentId = userMapper.getSchoolNameByStudentId("2021204010222");
        System.out.println(schoolNameByStudentId);
    }
}
