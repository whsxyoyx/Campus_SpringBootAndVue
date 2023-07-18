package com.oyx.campus.mapper;

import com.oyx.campus.bean.Daily;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author oyx
 * @create 2023-07-18 15:11
 */
public interface DailyMapper {

    /*
    * 批量删除日志---根据ids
    * */
    int deleteDailyByIds(List<Integer> ids);

    /*
    * 删除日志---根据主键删除
    * */
    @Delete("""
            delete from `daily`
            where id = #{id}
            """)
    int deleteByPrimaryKey(Integer id);

    /*
    * 更新日志----有选择性的更新
    * */
    int updateByPrimaryKeySelective(Daily daily);
    /*
    * 添加日志----有选择性的添加
    * */
    int insertSelective(Daily daily);

    /*
    * 查询日志----根据主键查询
    * */
    @Select("""
            select id, gain, question, suggest, subtime, lastupdatetime, sid
            from `daily`
            where id = #{id}
            """)
    Daily selectByPrimaryKey(Integer id);

    /*
    * 查询用户所有日志信息
    * */
    @Select("""
            select id, gain, question, suggest, subtime, lastupdatetime, sid,stuid, studentid, password, schoolid, sex, name, registertime, money, state, photo
                from daily d left join user u on d.sid=u.stuid
                where u.studentid=#{studentid}
            """)
    @Results(id = "dailyAndUserResultMap",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "gain",property = "gain"),
            @Result(column = "question",property = "question"),
            @Result(column = "suggest",property = "suggest"),
            @Result(column = "subtime",property = "subtime"),
            @Result(column = "subtime",property = "subtime"),
            @Result(column = "lastupdatetime",property = "lastupdatetime"),
            @Result(column = "sid",property = "sid"),
            @Result(column = "sid",property = "user",
                one = @One(select = "com.oyx.campus.mapper.UserMapper.selectByPrimaryKey",
                        fetchType = FetchType.LAZY)
            )
    })
    List<Daily> getAllDaily(String studentid);
}
