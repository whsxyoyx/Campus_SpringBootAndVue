package com.oyx.campus.mapper;

import com.oyx.campus.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author oyx
 * @create 2023-07-18 15:21
 */
public interface UserMapper {


    /*
    * 根据用户表中studentid查找学校名
    * */
    User getSchoolNameByStudentId(String studentId);

    /*
    * 解除所有被限制用户
    * */
    @Update("""
            update user set state = 0
            """)
    int openAllLimitedUsers();

    /*
    * 查询所有被限制用户
    * */
    @Select("""
            select * from `user` 
            where state = 1
            """)
    @ResultType(User.class)
    List<User> getLimitedUser();

    /*
    * 批量删除用户----根据ids
    * */
    int deleteUserByIds(List<Integer> ids);
    /*
    * 删除用户----根据用户id
    * */
    @Delete("""
            delete from `user`
            where stuid = #{id}
            """)
    int deleteByPrimaryKey(Integer id);

    /*
    * 查询用户列表----根据用户名和学校id组合查询
    * */
    @Select("""
            select * from `user` 
            where name like concat('%',#{name},'%')
            and schoolid = #{schoolid}
            """)
    @ResultType(User.class)
    List<User> getUsersByLikeAndSchoolId(String name,Integer schoolid);

    /*
    * 查询用户列表----根据用户名模糊查询
    * */
    @Select("""
            select * from `user` 
            where name like concat('%',#{name},'%')
            """)
    @ResultType(User.class)
    List<User> getUsersByLike(String name);

    /*
    * 更新用户信息----根据用户主键更新
    * */
    int updateByPrimaryKeySelective(User user);

    /*
    * 检查学号是否已存在
    * */
    @Select("""
            SELECT count(*) FROM `user` where studentid = #{studentid}
            """)
    @ResultType(Integer.class)
    int checkStudentId(String studentid);

    /*
    * 添加用户----可选择性的添加
    * */
    int insertSelective(User user);

    /*
    * 查询所有用户
    * */
    @Select("""
            select * from `user`
            """)
    @ResultType(User.class)
    List<User> selectAllUser();

    

    /**
     * 根据studentId查询学生信息
     * @param studentId
     * @return 返回的是一个user对象
     */
    @Select("""
            select stuid, studentid, password, schoolid, sex, name, registertime, money, state, photo
            from `user`
            where studentid = #{studentId}
            """)
    @ResultType(User.class)
    User selectByStudentId(String studentId);


    /*
    * 根据sid查询学生信息
    * */
    @Select("""
            select stuid, studentid, password, schoolid, sex, name, registertime, money, state, photo
            from `user`
            where stuid = #{uid}
            """)
    @ResultType(User.class)
    User selectByPrimaryKey(Integer uid);
}
