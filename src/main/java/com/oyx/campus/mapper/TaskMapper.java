package com.oyx.campus.mapper;

import com.oyx.campus.bean.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author oyx
 * @create 2023-07-18 20:02
 */
public interface TaskMapper {



    /*
    * 查询用户发布的所有任务----根据stuid
    * */
    @Select("""
            select * from `task`
            where publish_user_id = #{uid}
            order by addtime desc
            """)
    @ResultType(Task.class)
    List<Task> getPublishedTasks(Integer uid);


    /*
    * 查询已接受的任务列表
    * */
    @Select("""
            select * from `task`
            where accept_user_id = #{id}
            order by addtime desc
            """)
    @ResultType(Task.class)
    List<Task> getAcceptedTasks(Integer uid);

    /*
    * 批量删除任务
    * */
    int deleteTaskByIds(List<Integer> ids);
    /*
    * 根据任务id删除任务
    * */
    @Delete("""
            delete from `task`
            where taskid = #{id}
            """)
    int deleteByPrimaryKey(Integer id);

    /*
    * 搜索栏关键字的模糊查询
    * */
    @Select("""
            select * from `task`
            where taskname like concat('%',#{key},'%')
            and state = 0
            """)
    List<Task> selectByLike(String key);

    //用户的------start---------------------------
    /*
     *条件查询-----根据学校id查询
     * */
    @Select("""
            select * from `task`
            where publish_school_id = #{schoolid}
            AND state = 0
            order by addtime desc
            """)
    @ResultType(Task.class)
    List<Task> getTaskBySchoolIdUser(String schoolid);

    /*
     *条件查询-----根据任务类型查询
     * */
    @Select("""
            select * from `task`
            where tasktype = #{taskType} 
            AND state = 0 
            order by addtime desc
            """)
    @ResultType(Task.class)
    List<Task> getTaskByTaskTypeUser(String taskType);

    /*
     *条件查询-----根据学校id和任务类型查询
     * */
    @Select("""
            select * from `task`  
            where publish_school_id = #{schoolid} 
            AND tasktype = #{taskType} 
            AND state = 0
            order by addtime desc
            """)
    @ResultType(Task.class)
    List<Task> getTaskBySchoolIdAndTaskTypeUser(String schoolid, String taskType);


    //用户的------end---------------------------


    //管理员的------start---------------------------
    /*
    *条件查询-----根据学校id查询
    * */
    @Select("""
            select * from `task`
            where publish_school_id = #{schoolid}
            order by addtime desc
            """)
    @ResultType(Task.class)
    List<Task> getTaskBySchoolId(String schoolid);

    /*
    *条件查询-----根据任务类型查询
    * */
    @Select("""
            select * from `task`
            where tasktype = #{taskType}
            order by addtime desc
            """)
    @ResultType(Task.class)
    List<Task> getTaskByTaskType(String taskType);

    /*
    *条件查询-----根据学校id和任务类型查询
    * */
    @Select("""
            select * from `task`
            where publish_school_id = #{schoolid}
            and tasktype = #{taskType}
            order by addtime desc
            """)
    @ResultType(Task.class)
    List<Task> getTaskBySchoolIdAndTaskType(String schoolid, String taskType);
    //管理员的------end---------------------------

    /*
    * 根据任务名和学校id查询不受限制的任务
    * */
    List<Task> selectTaskByKeysNoState(String words, Integer schoolid);

    /*
    * 根据任务名和学校id查询所有任务
    * */
    List<Task> selectTaskByKeys(String words, Integer schoolid);
    /*
    *更新任务信息----有选择性的
    * */
    int updateByPrimaryKeySelective(Task task);

    /*
    * 根据主键查询任务详情
    * */
    @Select("""
            select * from `task`
            where taskid = #{id}
            """)
    @ResultType(Task.class)
    Task selectByPrimaryKey(Integer id);

    /*
    * 添加任务
    * */
    @Insert("""
              insert into task (taskid, publish_user_id, publish_user_name,
              publish_school_id, accept_user_id, reward,
              addtime, endtime, taskname,
              taskcontext, state, img,
              taskType)
              values (#{taskid}, #{publishUserId}, #{publishUserName},
              #{publishSchoolId}, #{acceptUserId}, #{reward},
              #{addtime}, #{endtime}, #{taskname},
              #{taskcontext}, #{state}, #{img},
              #{tasktype})
            """)
    int insert(Task task);

    /*
    * 查询所有任务，逆序排列----管理员使用
    * */
    @Select("""
            select * from `task` ORDER BY addtime DESC
            """)
    @ResultType(Task.class)
    List<Task> getAllTask();
}
