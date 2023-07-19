package com.oyx.campus.service.impl;


import com.oyx.campus.bean.Task;
import com.oyx.campus.mapper.TaskMapper;
import com.oyx.campus.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskMapper taskMapper;

	/*管理员使用---查找全部任务*/
	@Override
	public List<Task> getAllTask() {
		return taskMapper.getAllTask();
	}

	/*添加新任务*/
	@Override
	public int setNewTask(Task task) {

		return taskMapper.insert(task);
	}

	/*根据tid查看任务详情*/
	@Override
	public Task getTask(Integer tid) {
		return taskMapper.selectByPrimaryKey(tid);
	}
	/*根据tid更新任务*/
	@Override
	public int updateTask(Task task) {
		return taskMapper.updateByPrimaryKeySelective(task);
	}



	/*根据任务名和学校id查询所有任务*/
	@Override
	public List<Task> getTaskByKeys(String words, Integer schoolid) {
		// TODO Auto-generated method stub
		return taskMapper.selectTaskByKeys(words, schoolid);
	}

	/*根据任务名和学校id查询不受限制的任务*/
	@Override
	public List<Task> getTaskByKeysNoState(String words, Integer schoolid) {
		// TODO Auto-generated method stub
		return taskMapper.selectTaskByKeysNoState(words, schoolid);
	}


	//管理员的
	//根据学院id和任务类型查询符合条件的所有任务
	@Override
	public List<Task> getTaskBySchoolIdAndTaskType(String schoolid, String taskType) {
		List<Task> list = null;
		if(!schoolid.equals("undefined")&&!taskType.equals("undefined")){
			list = taskMapper.getTaskBySchoolIdAndTaskType(schoolid,taskType);
		}else if(schoolid.equals("undefined")&&!taskType.equals("undefined")){
			list = taskMapper.getTaskByTaskType(taskType);
		}else{
			list = taskMapper.getTaskBySchoolId(schoolid);
		}
		return list;
	}

	//用户的
	//根据学院id和任务类型查询符合条件的所有任务
	@Override
	public List<Task> getTaskBySchoolIdAndTaskTypeUser(String schoolid, String taskType) {
		List<Task> list = null;
		if(!schoolid.equals("undefined")&&!taskType.equals("undefined")){
			list = taskMapper.getTaskBySchoolIdAndTaskTypeUser(schoolid,taskType);
		}else if(schoolid.equals("undefined")&&!taskType.equals("undefined")){
			list = taskMapper.getTaskByTaskTypeUser(taskType);
		}else{
			list = taskMapper.getTaskBySchoolIdUser(schoolid);
		}
		return list;
	}


	//根据任务id删除任务
	@Override
	public int delTaskByTaskId(Integer taskid) {
		return taskMapper.deleteByPrimaryKey(taskid);
	}

	//批量删除任务
	@Override
	public int delTasks(List<Integer> ids) {
		return taskMapper.deleteTaskByIds(ids);
	}

	//查询已接受的任务列表
	@Override
	public List<Task> getAcceptedTasks(Integer uid) {
		return taskMapper.getAcceptedTasks(uid);
	}

	//放弃任务
	@Override
	public int cancelTask(Task task) {
		return taskMapper.updateByPrimaryKeySelective(task);
	}

	//查询用户发布的任务
	@Override
	public List<Task> getPublishedTasks(Integer pid) {
		return taskMapper.getPublishedTasks(pid);
	}

	//发布者编辑任务
	@Override
	public int editTask(Task task) {
		return taskMapper.updateByPrimaryKeySelective(task);
	}

	//发布者放弃任务
	@Override
	public int delTask(Integer tid) {
		return taskMapper.deleteByPrimaryKey(tid);
	}

	//发布者确认任务完成
	@Override
	public int updateTaskToFinished(Task task) {
		return taskMapper.updateByPrimaryKeySelective(task);
	}

	//发布者重新发布任务
	@Override
	public int rePublicTask(Task task) {
		return taskMapper.updateByPrimaryKeySelective(task);
	}

	//搜索栏关键字的模糊查询
	@Override
	public List<Task> getTasksByLike(String keys) {
		return taskMapper.selectByLike(keys);
	}

	@Override
	public Task getImagesByTid(Integer tid) {
		return taskMapper.selectByPrimaryKey(tid);
	}


}
