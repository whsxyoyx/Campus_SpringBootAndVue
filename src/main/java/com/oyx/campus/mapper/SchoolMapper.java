package com.oyx.campus.mapper;

import com.oyx.campus.bean.School;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author oyx
 * @create 2023-07-19 9:31
 */
public interface SchoolMapper {

    /*
    * 根据主键删除
    * */
    @Delete("""
            delete from `school`
            where schoolid = #{id}
            """)
    int deleteByPrimaryKey(Integer id);

    /*
    * 添加学院
    * */
    @Insert("""
            insert into `school`
            values(null,#{name},#{addtime},#{state})
            """)
    int insert(School school);
    /*
    * 查找全部----不含受限的
    * */
    List<School> selectAllSchoolsNoState();

    /*
    * 更新学院信息
    * */
    int updateByPrimaryKeySelective(School school);

    /*
    * 根据主键查询
    * */
    @Select("""
            select * from `school`
            where schoolid = #{id}
            """)
    @ResultType(School.class)
    School selectByPrimaryKey(Integer id);

    /*
    * 查询所有学校
    * */
    @Select("""
            select * from `school`
            """)
    @ResultType(School.class)
    List<School> selectAllSchool();


}
