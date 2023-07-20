package com.oyx.campus.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oyx.campus.bean.*;
import com.oyx.campus.service.SchoolService;
import com.oyx.campus.service.TaskService;
import com.oyx.campus.service.TaskTypeService;
import com.oyx.campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * *****任务********
 * 用户发布新任务*
 * 用户读取自己发布的任务*
 * 用户读取自己接受的任务*
 * 用户确认任务完成*
 * 用户关闭未被人接受的任务*
 * 用户接受任务*
 * 读取任务的信息*
 * 搜索任务*
 *
 * @author
 *
 */

@RestController
@RequestMapping(value = "/task")
public class TaskController {

	@Autowired
	public TaskService taskService;

	@Autowired
	public UserService userService;
	@Autowired
	private TaskTypeService taskTypeService;

	@Autowired
	private SchoolService schoolService;
	// 读取全部院校
	@ResponseBody
	@RequestMapping("/getAllSchool")
	public Msg getAllschool() {
		List<School> list = schoolService.getAllSchoolsNoState();
		/*list.forEach(System.out::println);*/
		return Msg.success().add("schoolList",list);
	}
	//查询所有任务类型
	@RequestMapping("/getAllTaskType")
	public Msg getAllTaskType(){
		List<TaskType> allTaskType = taskTypeService.getAllTaskType();
//		allTaskType.forEach(System.out::println);

		return Msg.success().add("taskTypes",allTaskType);
	}



	//根据条件查询任务
	//用于用户根据学院id和任务类型查询符合条件的所有任务
	@ResponseBody
	@RequestMapping("/getTaskByCondition/{pn}/{pageSize}")
	public Msg getTaskBySchoolIdAndTaskType(String schoolid,String taskType,@PathVariable("pn")Integer pn,
											@PathVariable("pageSize")Integer pageSize) {
		System.out.println("schoolid:"+schoolid+" tasktype:"+taskType);
		if(schoolid==""){
			schoolid="undefined";

		}else if(taskType==""){
			taskType="undefined";
		}
		PageHelper.startPage(pn,pageSize);
		List<Task> list=taskService.getTaskBySchoolIdAndTaskTypeUser(schoolid,taskType);
		for(Task t:list){
			String img = t.getImg();
			if(img.contains("http")){
				String[] s = img.split(" ");
				t.setImg(s[0]);
			}

		}
		PageInfo<Task> page = new PageInfo<Task>(list);
		if(list.size()>0){
			return Msg.success("查询成功！！！").add("list", page);
		}else{
			return Msg.fail("没有查询到数据");
		}
	}



	//查询所有任务未被接受的任务
	@RequestMapping("/getAllTasks/{pn}/{pageSize}")
	public Msg selectTaskByKeysNoState(@PathVariable("pn")Integer pn,
									   @PathVariable("pageSize")Integer pageSize){
		PageHelper.startPage(pn,pageSize);
		List<Task> allTaskType = taskService.getTaskByKeysNoState(null,null);
		for(Task task:allTaskType){
			String img = task.getImg();
			if(img.contains(" ")){
				String[] s = img.split(" ");
				task.setImg(s[0]);
			}
		}
//		allTaskType.forEach(System.out::println);
		PageInfo<Task> pageInfo = new PageInfo<Task>(allTaskType);
		return Msg.success().add("tasks",pageInfo);
	}


	//搜索栏关键字的模糊查询
	@RequestMapping("/getAllTasksLike/{pn}/{pageSize}")
	public Msg getAllTasksLike(@PathVariable("pn")Integer pn,
									   @PathVariable("pageSize")Integer pageSize,String keys){
		PageHelper.startPage(pn, pageSize);
		String key="%"+keys+"%";
		List<Task> list = taskService.getTasksByLike(key);
		System.out.println("-----start-----");
		for(Task t:list){
			System.out.println(t);
			String img = t.getImg();
			if(img.contains("http")){
				String[] s = img.split(" ");
				t.setImg(s[0]);
			}
		}
		System.out.println("-----end-----");
		if(list.size()>0){
			PageInfo<Task> pageInfo = new PageInfo<Task>(list);
			return Msg.success().add("tasksLike", pageInfo);
		}else{
			return Msg.fail("暂无数据");
		}


	}

	//根据taskid查询该任务详细信息
	// 获取任务详细信息
	@RequestMapping("/getTaskInfo")
	public Msg getTaskInfo(String taskid) {
		System.out.println("----->>>>>>>>>"+taskid);
		int tid = 0;
		try {
			tid = Integer.parseInt(taskid);
		} catch (Exception e) {
			e.printStackTrace();
			return Msg.fail("服务器内部出错啦，请稍等，程序猿小哥哥正在熬夜加班修复中");
		}
		Task theTask = taskService.getTask(tid);
		return  Msg.success().add("taskInfo",theTask);
	}

	//发布新任务
	@PostMapping("/addTask")
	public Msg addTask(@RequestBody Task task) {
		Integer publishUserId = Integer.parseInt(task.getPublishUserId());
		User user = userService.getUserById(publishUserId);

		if (user.getState() > 0) {
			return Msg.fail("发布失败,用户状态受限");
		}

		if (user.getMoney()<task.getReward()) {
			return Msg.fail("发布失败,余额不足,请联系管理员添加余额");
		}
		user.setMoney(user.getMoney()-task.getReward());
		task.setAddtime(new Date());
		task.setAcceptUserId(0);
		System.out.println("---》"+task.getImg());
		if(task.getImg()==""){
			task.setImg("http://localhost:8080/Campus_Help/upload/83a770a9-f331-4b5f-b737-bdbb9023b439.jpg");
		}
		int ruser = userService.updateUserInfo(user);
		if (ruser> 0) {
			int r = taskService.setNewTask(task);
			if (r > 0) {
				return Msg.success("发布成功");
			}else {
				user.setMoney(user.getMoney()+task.getReward()*2);
				int i = userService.updateUserInfo(user);
				return Msg.fail("发布失败");
			}
		}else {
			return Msg.fail("发布失败");
		}
	}

	//点击接受任务
	@RequestMapping("/taskAccept")
	public Msg taskAccept( @RequestBody Map<String,Object> map) {

		//接受者id
		Integer aId = (Integer)map.get("aId");
		User userById = userService.getUserById(aId);
		if(userById.getState()==0){
			Integer taskId = (Integer)map.get("taskId");
			Task theTask = taskService.getTask(taskId);
			if ((aId + "").equals(theTask.getPublishUserId())) {
				return Msg.fail("不能接受自己的任务啊");
			}
			theTask.setAcceptUserId(aId);
			theTask.setState(2);
			int r = taskService.updateTask(theTask);
			if (r > 0) {
				return Msg.success("接受任务成功");
			} else {
				return Msg.fail("接受任务失败");
			}
		}else{
			return Msg.fail("账户被限制了，请联系管理员解除限制");
		}

	}

	//查询指定任务id的图片集
	@GetMapping("/getImagesByTid/{tid}")
	public Msg getImagesByTid( @PathVariable("tid") Integer tid) {

		Task task = taskService.getImagesByTid(tid);
		String[] imgs = task.getImg().split(" ");
		//System.out.println("test--->"+Arrays.toString(imgs));
		return Msg.success().add("imgs", imgs);
	}
	//查询用户接受的任务
	@GetMapping("/taskHasAccepted/{uid}/{pn}/{pagesize}")
	public Msg getTaskHasAccepted( @PathVariable("uid") Integer uid,
								@PathVariable("pn") Integer pn,
								@PathVariable("pagesize") Integer pagesize) {
		System.out.println(uid+"---"+pn+"--"+pagesize);
		PageHelper.startPage(pn,pagesize);
		List<Task> list = taskService.getAcceptedTasks(uid);
//		list.forEach(System.out::println);
		PageInfo pageInfo = new PageInfo(list);
		return Msg.success().add("acceptedTasks", pageInfo);
	}
	//查询用户发布的任务
	@GetMapping("/taskHasPublished/{pid}/{pn}/{pagesize}")
	public Msg getTaskHasPublished( @PathVariable("pid") Integer pid,
								@PathVariable("pn") Integer pn,
								@PathVariable("pagesize") Integer pagesize) {
		System.out.println(pid+"---"+pn+"--"+pagesize);
		PageHelper.startPage(pn,pagesize);
		List<Task> list = taskService.getPublishedTasks(pid);
		//list.forEach(System.out::println);
		PageInfo pageInfo = new PageInfo(list);
		return Msg.success().add("publishedTasks", pageInfo);
	}

	//接受者放弃任务
	@PutMapping("/cancelTask/{taskid}")
	public Msg cancelTask(@PathVariable("taskid") Integer taskid) {
		Task task = taskService.getTask(taskid);
		task.setState(1);
		task.setAcceptUserId(0);
		int i = taskService.cancelTask(task);
		if(i>0){
		return Msg.success("操作成功");
		}else{
			return Msg.fail("操作失败");

		}
	}
	//发布者编辑任务
	@PutMapping("/editTask")
	public Msg editTask(@RequestBody Task task) {
		int i = taskService.editTask(task);
		if(i>0){
		return Msg.success("操作成功");
		}else{
			return Msg.fail("操作失败");

		}
	}
	//发布者放弃任务
	@DeleteMapping("/delTask/{tid}")
	public Msg delTask(@PathVariable("tid") Integer tid) {
		int i = taskService.delTask(tid);
		if(i>0){
		return Msg.success("操作成功");
		}else{
			return Msg.fail("操作失败");
		}
	}
	//发布者重新发布任务
	@PutMapping("/rePublicTask/{tid}")
	public Msg rePublicTask(@PathVariable("tid") Integer tid) {
		Task task = new Task();
		task.setState(0);
		task.setTaskid(tid);
		int i = taskService.rePublicTask(task);
		if(i>0){
		return Msg.success("操作成功");
		}else{
			return Msg.fail("操作失败");
		}
	}



	//发布者确认任务完成
	@PutMapping("/finishedTask")
	public Msg updateTaskToFinished(@RequestBody Task task) {
		String publishUserId = task.getPublishUserId();
		//报酬
		Double reward = task.getReward();
		//修改发布者信息
		User publishUser = userService.getUserById(Integer.parseInt(publishUserId));
		Double pMoney= publishUser.getMoney();
		publishUser.setMoney(pMoney-reward);
		int pRest = userService.updateUserInfo(publishUser);

		//修改接受者信息
		Integer acceptUserId = task.getAcceptUserId();
		User acceptUser = userService.getUserById(acceptUserId);
		Double aMoney = acceptUser.getMoney();
		acceptUser.setMoney(aMoney+reward);
		int aRest = userService.updateUserInfo(acceptUser);

		if(pRest>0 && aRest>0){
			task.setState(3);
			int i = taskService.updateTaskToFinished(task);
			if(i>0){
				return Msg.success("操作成功");
			}else{
				return Msg.fail("操作失败");
			}
		}else{
			return Msg.fail("操作失败");
		}
	}








}
