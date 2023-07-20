package com.oyx.campus.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oyx.campus.bean.*;
import com.oyx.campus.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ****管理员功能****
 * -------
 * 管理员登录 .
 * 管理员个人信息更新 .
 * 密码更新 .
 * 添加管理员 .
 * ------
 * 获取任务列表 .
 * 关闭待解决任务.
 * ------
 * 获取用户列表 .
 * 读取一个用户.
 * 添加用户余额.
 * 解除用户限制 .
 * 添加用户限制 .
 * ----------
 * 获取学校列表.
 * 读取单个学校信息 .
 * 更新学校信息 .
 * 添加院校.
 *
 * @author
 *
 */
@Controller
@SessionAttributes({ "nowadmin" })
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	public AdminService adminService;

	@Autowired
	public SchoolService schoolService;

	@Autowired
	public TaskService taskService;

	@Autowired
	public UserService userService;

	@Autowired
	private TaskTypeService taskTypeService;



	/*------<<<<用户管理start>>>>-----*/

	//查询所有的用户
	@RequestMapping("/getUsers/{pn}/{pageSize}")
	@ResponseBody
	public Msg getAllUsers(@PathVariable("pn")Integer pn,
						   @PathVariable("pageSize")Integer pageSize){
		PageHelper.startPage(pn, pageSize);
		List<User> list = userService.getAllUsers();
		PageInfo<User> page =new PageInfo<User>(list) ;

		/*allUsers.forEach(System.out::println);*/
		return Msg.success().add("userList",page);
	}

	// 查询当前用户信息
	@ResponseBody
	@RequestMapping("/getCurrentUser/{studentid}")
	public Msg getCurrentUser(@PathVariable("studentid") String studentid) {
		User user = userService.getUserByStudentid(studentid);
		return  Msg.success().add("user",user);
	}

	//搜索栏通用查询 用于以下三种情况
	// 1,都不为空
	// 2，schoolid为空
	// 3，name为空
	//查询用户根据用户名和他的学院id（用于搜索栏搜索）
	@ResponseBody
	@GetMapping("/search/{pn}/{pageSize}")
	public Msg getUsersByNameAndSchoolid(String name,String schoolid,@PathVariable("pn")Integer pn,
										 @PathVariable("pageSize")Integer pageSize){
		if(schoolid.equals("0")){
			return Msg.fail("学院id不能为0");
		}
		int id=0;
		if(!schoolid.equals("")){
			 id = Integer.parseInt(schoolid);
		}
		name = "%" + name + "%";

		PageHelper.startPage(pn, pageSize);
		List<User> list = userService.getUsersByNameAndSchoolid(name, id);
		PageInfo<User> page =new PageInfo<User>(list) ;
//		list.forEach(System.out::println);

		if(list.size()>0){
			return Msg.success("查询成功！！！").add("users",page);
		}else{
			return Msg.fail("没有查询到数据");
		}
	}

	//修改用户
	@ResponseBody
	@PutMapping("/updateUser")
	public Msg updateUser(@RequestBody User user){
		System.out.println("----->"+user);
		int i = userService.updateUserInfo(user);
		if(i>0){
			return  Msg.success("修改成功");
		}else{
			return  Msg.fail("修改失败");
		}
	}

	//删除用户
	@ResponseBody
	@DeleteMapping("/delUser/{stuid}")
	public Msg delUser(@PathVariable("stuid") String stuid){
		int uid=Integer.parseInt(stuid);
		int i = userService.delUserByUserId(uid);
		if(i>0){
			return  Msg.success("删除成功");
		}else{
			return  Msg.fail("删除失败");
		}
	}
	//批量删除用户
	@ResponseBody
	@DeleteMapping("/delUsers/{userids}")
	public Msg delUsers(@PathVariable("userids")String stuids){
		System.out.println(stuids);
		String[] split = stuids.split("-");
		Integer[] ids=new Integer[20];
		for(int i = 0;i<split.length;i++){
			ids[i]=Integer.parseInt(split[i]);
			System.out.print(ids[i]);
		}
		int i = userService.delUsers(Arrays.asList(ids));
		if(i>0){
			return  Msg.success("删除成功");
		}else{
			return  Msg.fail("删除失败");
		}
	}



	// 限制用户
	/*先根据stuidstr（stuid）获取用户信息，再更改该用户权限
	* 最后keys 指的是用户名/stuid 用于最后的修改权限后查询该用户数据后返回该用户 */
	@ResponseBody
	@PutMapping("/limitUser/{stuid}")
	public Msg limitUser( @PathVariable("stuid") Integer stuid, Model model) {
		try {
			if (stuid == 0) {
				return Msg.fail("出现错误");
			}
		} catch (Exception e) {
			return Msg.fail("出现错误");
		}
		User theUser = userService.getUserById(stuid);
		theUser.setState(1);
		int r = userService.updateUserInfo(theUser);
		if (r <=0) {
			return Msg.fail("修改失败");
		}
		return Msg.success("修改成功");
	}

	/*分页查询所有被限制的用户 */
	@ResponseBody
	@GetMapping("/getLimitedUsers/{pn}/{pageSize}")
	public Msg getLimitedUsers(@PathVariable("pn")Integer pn,
							   @PathVariable("pageSize")Integer pageSize){
		PageHelper.startPage(pn, pageSize);
		List<User> list = userService.getLimitUsers();
		PageInfo<User> page =new PageInfo<User>(list) ;
		return Msg.success().add("limitedUsers",page);
	}
	// 解除用户限制  stuidstr:stuid
	@PutMapping("/openUser/{stuid}")
	@ResponseBody
	public Msg openUser(@PathVariable("stuid")String stuid, Model model) {
		System.out.println("----->>"+stuid);
		if (stuid == null) {
			/*model.addAttribute("msg", "出现错误");*/
			return Msg.fail("出现错误");
		} else {
			if (stuid.length() == 0) {
				/*model.addAttribute("msg", "出现错误");*/
				return Msg.fail("出现错误");
			}
		}
		int stuId = 0;
		try {
			stuId = Integer.parseInt(stuid);
			if (stuId == 0) {
				/*model.addAttribute("msg", "出现错误");*/
				return Msg.fail("出现错误");
			}
		} catch (Exception e) {
			/*model.addAttribute("msg", "出现错误");*/
			return Msg.fail("服务器内部出错啦，请稍等，程序猿小哥哥正在熬夜加班修复中");
		}

		User theUser = userService.getUserById(stuId);
		theUser.setState(0);

		int r = userService.updateUserInfo(theUser);
		if (r > 0) {
			return Msg.success("修改成功");
		} else {
			return Msg.fail("修改失败");
		}

	}

	/*// 解除所有用户限制
	@PutMapping("/openAllUsers/{stuids}")
	@ResponseBody
	public Msg openAllUsers(@PathVariable("stuids")String stuids, Model model) {*/
	// 解除所有用户限制
	@PutMapping("/openAllUsers")
	@ResponseBody
	public Msg openAllUsers() {
		int i = userService.openAllLimitedUsers();
		if (i > 0) {
			return Msg.success("操作成功");
		} else {
			return Msg.fail("操作失败");
		}

	}


	/*------<<<<用户管理end>>>>-----*/
	/*------<<<<学院管理start>>>>-----*/

	// 读取全部院校
	@ResponseBody
	@RequestMapping("/getAllSchool")
	public Msg getAllschool() {
		List<School> list = schoolService.getAllSchoolsNoState();
		/*list.forEach(System.out::println);*/
		return Msg.success().add("schoolList",list);
	}


	// 读取全部院校 分页版
	@ResponseBody
	@RequestMapping("/getAllSchool/{pn}/{pageSize}")
	public Msg getAllschoolPage(@PathVariable("pn")Integer pn,
							@PathVariable("pageSize")Integer pageSize) {
		PageHelper.startPage(pn, pageSize);
		List<School> list = schoolService.getAllSchools();
		PageInfo<School> page =new PageInfo<School>(list) ;
		/*list.forEach(System.out::println);*/
		return Msg.success().add("schoolList",page);
	}

	// 读取一个院校信息
	@ResponseBody
	@RequestMapping("/getSchoolInfo/{schoolid}")
	public Msg getSchoolInfo(@PathVariable("schoolid") String schoolid) {
		if (schoolid== null) {
		/*	model.addAttribute("msg", "出现错误");*/
			return Msg.fail("出现错误");
		} else {
			if (schoolid.length() == 0) {
				/*model.addAttribute("msg", "出现错误");*/
				return Msg.fail("出现错误");
			}
		}
		int schooliId = 0;
		try {
			schooliId = Integer.parseInt(schoolid);
			if (schooliId == 0) {
				/*model.addAttribute("msg", "出现错误");*/
				return Msg.fail("出现错误");
			}
		} catch (Exception e) {
			/*model.addAttribute("msg", "出现错误");*/
			return Msg.fail("出现错误");
		}
		School theSchool = schoolService.getSchoolByID(schooliId);
		if (theSchool != null) {
			/*model.addAttribute("theSchool", theSchool);*/
			return Msg.success().add("schoolInfo",theSchool);
		} else {
			/*model.addAttribute("msg", "读取失败");*/
			return Msg.fail("读取失败");
		}
	}

	// 更新院校
	@PutMapping("/updateSchool")
	@ResponseBody
	public Msg updateSchool(@RequestBody School school) {
		System.out.println(school);
		int r = 0;

		r = schoolService.updateSchool(school);

		if (r > 0) {
			/*model.addAttribute("msg", "修改成功-刷新页面重新加载显示");*/
		/*	School theSchool = schoolService.getSchoolByID(school.getSchoolid());
			//发生修改成功提示信息，并且带上修改后的数据返回
			return Msg.success("修改成功").add("school",theSchool);*/
			return Msg.success("修改成功");
		} else {
			/*model.addAttribute("msg", "修改失败");*/
			return Msg.fail("修改失败");
		}
	}

	// 添加院校
	@ResponseBody
	@PostMapping("/addSchool")
	public Msg addSchool(@RequestBody Map<String,Object> sname) {
		String name=(String)sname.get("name");
		System.out.println(sname);
		if (name == null) {
			/*model.addAttribute("msg", "添加失败");*/
			return Msg.fail("添加失败");
		} else {
			if (name.length() == 0) {
				/*model.addAttribute("msg", "添加失败");*/
				return Msg.fail("添加失败");
			}
		}
		School theSchool = new School(null, name, new Date(), 0);
		int r = 0;
		r = schoolService.setSchool(theSchool);
		if (r > 0) {
		/*	model.addAttribute("msg", "添加成功");
			model.addAttribute("flag", "添加成功");*/
			return Msg.success("添加成功");
		} else {
			/*model.addAttribute("msg", "添加失败");*/
			return Msg.fail("添加失败");
		}
	}
	// 删除院校
	@ResponseBody
	@DeleteMapping("/delSchool/{schoolid}")
	public Msg delSchool(@PathVariable("schoolid") Integer schoolid) {
		if (schoolid == null) {
			/*model.addAttribute("msg", "添加失败");*/
			return Msg.fail("删除失败");
		}
		int r = schoolService.delSchool(schoolid);
		if (r > 0) {
			return Msg.success("删除成功");
		} else {
			return Msg.fail("删除失败");
		}
	}


	/*------<<<<学院管理end>>>>-----*/


	/*------<<<<管理员管理start>>>>-----*/
	// 管理员登录 根据数据库中account和password字段位账户名和密码
	@ResponseBody
	@GetMapping("/adminlogin")
	public Msg adminlogin(String account, String password, Model model) {
		Admin admin = null;
		admin = adminService.login(account);
		if (admin == null) {
			/*model.addAttribute("msg", "登录账号不存在");*/
			return Msg.fail("登录账号不存在");
		}
		if (password.equals(admin.getPassword())) {
			/*model.addAttribute("nowadmin", admin);*/
			/*return "adminIndex";*/
			return Msg.success("登录成功");
		} else {
			/*model.addAttribute("msg", "密码验证错误");*/
			return Msg.fail("密码错误");
		}

	}

	//查询所有管理员
	@ResponseBody
	@GetMapping("/getAllAdmin")
	public Msg getAllAdmin() {
		List<Admin> list = adminService.getAllAdmin();
//		list.forEach(System.out::println);
		return Msg.success().add("adminList",list);
	}

	// 更新管理员信息
	@PutMapping("/updateAdmin")
	@ResponseBody
	public Msg updateAdmin(@RequestBody Admin admin) {
		System.out.println("--->"+admin);
		int r = adminService.updateAdminInfo(admin);
		if (r > 0) {
			return Msg.success("更新成功");
		}
		return Msg.fail("更新失败");
	}

	// 更新管理员密码 参数中admin要提供提供管理员的account，和更新的密码
	@PutMapping("/updatepwd")
	@ResponseBody
	public Msg updatePwd(HttpServletRequest request, String oldpassword, Admin admin, Model model) {
		/*Admin oldadmin = (Admin) request.getSession(false).getAttribute("nowadmin");
		System.out.println(oldadmin.getPassword());
		System.out.println(oldpassword);*/
		Admin oldAdmin = adminService.getAdminByAccount(admin.getAccount());
		if (!oldAdmin.getPassword().equals(oldpassword)) {
			/*model.addAttribute("msg", "原密码错误");*/
			return Msg.fail("原密码错误");
		}
		int r = adminService.updateAdminInfo(admin);
		if (r > 0) {
		/*	model.addAttribute("msg", "修改成功，请重新登录");
			request.getSession(false).removeAttribute("nowadmin");*/
			return Msg.success("修改成功，请重新登录");
		}
		/*model.addAttribute("msg", "修改失败");*/
		return  Msg.fail("修改失败");
	}
	//根据aid查询管理员信息
	@ResponseBody
	@RequestMapping("/getAdmin/{aid}")
	public Msg getAdminByAid(@PathVariable("aid") String aidstr) {
		int aid = 0;
		try {
			aid = Integer.parseInt(aidstr);
		} catch (Exception e) {
			/*model.addAttribute("msg", "出现错误");*/
			return Msg.fail("出现错误");
		}
		if (aid == 0) {
			/*model.addAttribute("msg", "出现错误");*/
			return Msg.fail("出现错误");
		}
		Admin admin = adminService.getAdminByAid(aid);
		return Msg.success().add("admin",admin);
	}
	//添加管理员
	@ResponseBody
	@PostMapping("/addAdmin")
	public Msg addAdmin(@RequestBody Admin admin) {
		System.out.println(admin);
		String account=admin.getAccount();
		String name=admin.getName();
		// 检查账号重复
		int countnum = adminService.getAccountCount(account);
		if (countnum > 0) {
			/*model.addAttribute("msg",account +"该账号已经被使用");*/
			return Msg.fail(account +"该账号已经被使用");
		}
		Admin ad = new Admin(null, account, "123456", name, new Date(), 0);

		int result = adminService.setAdmin(ad);

		if (result <= 0) {
			/*model.addAttribute("msg", "注册失败");*/
			return Msg.fail("注册失败");
		}
		/*model.addAttribute("msg", "注册成功，可以登录。默认密码：123456");*/
		return Msg.success("注册成功,可以登录。默认密码：123456");
	}

	//删除管理员
	@ResponseBody
	@DeleteMapping("/delAdmin/{aid}")
	public Msg delAdmin(@PathVariable("aid") Integer aid){
		int i = adminService.delAdmin(aid);
		if(i>0){
			return Msg.success("删除成功");
		}else{
			return Msg.fail("删除失败");
		}
	}

	/*------<<<<<管理员管理end>>>>>-----*/


	/*------<<<<<任务管理start>>>>>-----*/

	//根据条件查询任务
	//用于管理员根据学院id和任务类型查询符合条件的所有任务
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
		List<Task> list=taskService.getTaskBySchoolIdAndTaskType(schoolid,taskType);
		PageInfo<Task> page = new PageInfo<Task>(list);
		if(list.size()>0){
			return Msg.success("查询成功！！！").add("list", page);
		}else{
			return Msg.fail("没有查询到数据");
		}
	}

	//根据taskid查询该任务详细信息
	// 获取任务详细信息
	@ResponseBody
	@RequestMapping("/getTaskInfo/{taskid}")
	public Msg getTaskInfo(@PathVariable("taskid") String taskid) {
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

	//修改任务
	@ResponseBody
	@RequestMapping("/updateTask")
	public Msg updateTask(@RequestBody Task task ){
		System.out.println(task);
		if(task.getTaskname().length()>18){
			return  Msg.fail("任务名不能超过18个汉字");
		}
		int i = taskService.updateTask(task);
		if(i>0){
			return  Msg.success("修改成功");
		}else{
			return  Msg.fail("修改失败");
		}
	}

	//删除任务
	@ResponseBody
	@DeleteMapping("/delTask/{taskid}")
	public Msg delTask(@PathVariable("taskid") String taskid){
		int tid=Integer.parseInt(taskid);
		int i = taskService.delTaskByTaskId(tid);
		if(i>0){
			return  Msg.success("删除成功");
		}else{
			return  Msg.fail("删除失败");
		}
	}
	//批量删除任务
	@ResponseBody
	@DeleteMapping("/delTasks/{taskids}")
	public Msg delTasks(@PathVariable("taskids")String taskIds){
		System.out.println(taskIds);
		String[] split = taskIds.split("-");
		Integer[] ids=new Integer[20];
		for(int i = 0;i<split.length;i++){
			ids[i]=Integer.parseInt(split[i]);
			System.out.print(ids[i]);
		}
		int i = taskService.delTasks(Arrays.asList(ids));
		if(i>0){
			return  Msg.success("删除成功");
		}else{
			return  Msg.fail("删除失败");
		}
	}


	// 查询所有任务
	@ResponseBody
	@RequestMapping("/getAllTask/{pn}/{pageSize}")
	public Msg getAllTasks(@PathVariable("pn")Integer pn,
						   @PathVariable("pageSize")Integer pageSize) {
		PageHelper.startPage(pn, pageSize);
		List<Task> list = taskService.getAllTask();
		/*for(Task task:list){
			String img = task.getImg();
			if(img.contains(" ")){
				String[] s = img.split(" ");
				task.setImg(s[0]);
			}
		}*/
		PageInfo<Task> page =new PageInfo<Task>(list) ;
		System.out.println(page);
		return Msg.success().add("taskList",page);
	}
	//查询所有任务类型
	@ResponseBody
	@RequestMapping("/getAllTaskType")
	public Msg getAllTaskType(){
		List<TaskType> allTaskType = taskTypeService.getAllTaskType();
//		allTaskType.forEach(System.out::println);

		return Msg.success().add("taskTypes",allTaskType);
	}
	/*------<<<<<任务管理end>>>>>-----*/






















/*



@RequestMapping("/getTask")
	public String gettasks(String words, @RequestParam(required = true, defaultValue = "-1") String schoolidstr,
			Model model) {
		model.addAttribute("words", words);
		model.addAttribute("schoolidstr", schoolidstr);
		int schoolid = -1;
		if (!schoolidstr.equals("-1")) {
			try {
				schoolid = Integer.parseInt(schoolidstr);
			} catch (Exception e) {
				System.err.println("err");
			}
		}
		if (words != null) {
			words = "%" + words + "%";
		} else {
			words = "%%";
		}
		List<Task> list = taskService.getTaskByKeys(words, schoolid);
		model.addAttribute("list", list);
		return "adminTask";
	}

	// to1.管理员点击关闭删除取消
	@RequestMapping("taskclose.do")
	public String taskclose(String tidstr, String words,
			@RequestParam(required = true, defaultValue = "-1") String schoolidstr, HttpServletRequest request,
			Model model) {
		int tid = 0;
		try {
			tid = Integer.parseInt(tidstr);
		} catch (Exception e) {
			model.addAttribute("msg", "出现错误");
			return gettasks(words, schoolidstr, model);
		}
		if (tid == 0) {
			model.addAttribute("msg", "出现错误");
			return gettasks(words, schoolidstr, model);
		}
		Admin admin = null;
		try {
			admin = (Admin) request.getSession(false).getAttribute("nowadmin");
			int uid = 0;
			uid = admin.getAid();
			if (admin == null || uid == 0) {
				model.addAttribute("msg", "请检查登录状况");
				return gettasks(words, schoolidstr, model);
			}
		} catch (Exception e) {
			model.addAttribute("msg", "请检查登录状况");
			return "login";
		}

		Task theTask = taskService.getTask(tid);

		theTask.setState(1);
		int r = taskService.updateTask(theTask);
		if (r > 0) {
			model.addAttribute("msg", "成功");
		} else {
			model.addAttribute("msg", "失败");
		}

		return gettasks(words, schoolidstr, model);
	}



	// 读取一个用户,添加余额时使用
	@RequestMapping("/getuser")
	public String getuser(String stuidstr, Model model) {
		int stuid = 0;
		try {
			stuid = Integer.parseInt(stuidstr);
		} catch (Exception e) {
			model.addAttribute("msg", "出现错误");
			return "userInfo";
		}
		if (stuid == 0) {
			model.addAttribute("msg", "出现错误");
			return "userInfo";
		}
		User user = userService.getUserById(stuid);
		model.addAttribute("theuser", user);
		return "adminUserMoney";
	}

	// 添加用户余额
	@RequestMapping("addusermoney.do")
	public String addusermoney(String moneystr, String stuidstr, Model model) {
		double money = 0.00;
		try {
			money = Double.parseDouble(moneystr);
		} catch (Exception e) {
			model.addAttribute("msg", "金额出现错误");
			return getuser(stuidstr, model);
		}

		if (stuidstr == null) {
			model.addAttribute("msg", "出现错误");
			return getuser(stuidstr, model);
		} else {
			if (stuidstr.length() == 0) {
				model.addAttribute("msg", "出现错误");
				return getuser(stuidstr, model);
			}
		}
		int stuid = 0;
		try {
			stuid = Integer.parseInt(stuidstr);
			if (stuid == 0) {
				model.addAttribute("msg", "出现错误");
				return getuser(stuidstr, model);
			}
		} catch (Exception e) {
			model.addAttribute("msg", "出现错误");
			return getuser(stuidstr, model);
		}

		User theUser = userService.getUserById(stuid);
		theUser.setMoney(theUser.getMoney() + money);

		int r = userService.updateUserInfo(theUser);
		if (r > 0) {
			model.addAttribute("msg", "修改成功");
		} else {
			model.addAttribute("msg", "修改失败");
		}
		return getuser(stuidstr, model);
	}








	@RequestMapping("/getschools")
	public String getschools(Model model) {

		List<School> list = schoolService.getAllSchools();

		model.addAttribute("list", list);

		return "adminSchool";
	}






	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(ServletRequestDataBinder bin) {
		bin.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}*/

}
