package com.oyx.campus.mapper;

import com.oyx.campus.bean.TaskType;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author oyx
 * @create 2023-07-18 19:32
 */
public interface TaskTypeMapper {

    /*
     * 更新任务类型----根据任务类型更新
     * */
    @Update("""
            update `tasktype`
            set tasktype = #{name}
            where tasktype = #{tasktype}
            """)
    int updateTaskType(String tasktype,String name);

    /*
    * 删除任务类型----根据任务类型删除
    * */
    @Delete("""
            delete from `tasktype`
            where tasktype = #{tasktype}
            """)
    int delTaskType(String tasktype);
    /*
    * 添加任务类型
    * */
    @Insert("""
            insert into tasktype(addTime, taskType)
            values (#{addtime}, #{tasktype}) 
            """)
    int insert(TaskType taskType);
    /*
    * 查询所有任务类型
    * */
    @Select("""
            select * from `tasktype`
            """)
    @ResultType(TaskType.class)
    List<TaskType> selectAllTaskType();
}
