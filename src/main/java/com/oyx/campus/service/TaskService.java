package com.oyx.campus.service;


import com.oyx.campus.bean.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskService {

	/**
	 * 读取全部任务
	 * @return
	 */
	public List<Task> getAllTask();

	/**
	 * 添加新任务
	 * @param task
	 * @return
	 */
	public int setNewTask(Task task);


	/**
	 * 读取1个任务
	 * @return
	 */
	public Task getTask(Integer tid);

	/**
	 * 更新任务
	 * @param task
	 * @return
	 */
	public int updateTask(Task task);

	/**
     * selectTaskByKeys
     * @param
     * @return
     */
    public List<Task> getTaskByKeys(String words,Integer schoolid);

    /**
     * selectTaskByKeysNoState
     * @param
     * @return
     */
    public List<Task> getTaskByKeysNoState(@Param("words")String words, @Param("schoolid")Integer schoolid);



	//根据学院id和任务类型查询符合条件的所有任务
    List<Task> getTaskBySchoolIdAndTaskType(String schoolid, String taskType);


	//用户的
	//根据学院id和任务类型查询符合条件的所有任务
	List<Task> getTaskBySchoolIdAndTaskTypeUser(String schoolid, String taskType);

	//根据任务id删除任务
	int delTaskByTaskId(Integer taskid);

	//批量删除任务
	int delTasks(List<Integer> asList);

	//查询已接受的任务列表
    List<Task> getAcceptedTasks(Integer uid);

    int cancelTask(Task task);
	//查询用户发布的任务
	List<Task> getPublishedTasks(Integer pid);
	//发布者编辑任务
	int editTask(Task task);

	//发布者放弃任务
	int delTask(Integer tid);



	//发布者确认任务完成
	int updateTaskToFinished(Task task);

	//发布者重新发布任务
	int rePublicTask( Task task);


	//搜索栏关键字的模糊查询
	List<Task> getTasksByLike(String keys);

	//查询指定任务id的图片集
	Task getImagesByTid(Integer tid);
}
