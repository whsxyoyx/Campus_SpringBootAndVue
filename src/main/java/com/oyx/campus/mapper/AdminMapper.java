package com.oyx.campus.mapper;

import com.oyx.campus.bean.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author oyx
 * @create 2023-07-18 10:32
 */
public interface AdminMapper {

    /*
    * 删除管理员----根据aid删除
    * */
    @Delete("""
            delete from `admin`
            where aid = #{aid}
            """)
    int deleteByPrimaryKey(Integer aid);

    /*
    * 查询所有的管理员
    * */
    @Select("""
            select * from `admin`
            """)
    @ResultType(Admin.class)
    List<Admin> selectAllAdmin();
    /*
    * 查询管理员----根据管理员id相关信息
    * */
    @Select("""
            select aid, account, password, name, addtime, state
            FROM `admin` 
            WHERE aid = #{aid}
            """)
    @ResultType(Admin.class)
    Admin selectByPrimaryKey(Integer aid);


    /*
    * 添加管理员----参数可选方式
    * */
    int insertSelective(Admin record);
    /*
     * 根据管理员id修改管理员信息----参数可选方式*/
    int updateByPrimaryKeySelective(Admin record);

    /*
    * 修改管理员----根据管理员id修改管理员信息
    * */
    @Update("""
            update admin
                       set account = #{account},
                         password = #{password},
                         name = #{name},
                         addtime = #{addtime},
                         state = #{state}
                       where aid = #{aid}
            """)
    int updateByPrimaryKey(Admin record);

    /**
     * 查找账号个数
     * 用于添加管理员时判断检查账号是否重复
     */
    @Select("""
            SELECT COUNT(*) FROM `admin` WHERE account = #{account}
            """)

    @ResultType(Admin.class)
    int selectAccountCount(String account);
    /**
     * 根据账号查找返回admin
     * 登录时根据账号获取该帐号信息
     */
    @Select("""
            select aid, account, password, name, addtime, state
            FROM `admin` WHERE account = #{account};
            """)
    @ResultType(Admin.class)
    Admin selectAdminByAccount(String account);


}
