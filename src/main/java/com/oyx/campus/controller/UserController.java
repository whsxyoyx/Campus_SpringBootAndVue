package com.oyx.campus.controller;


import com.oyx.campus.bean.Msg;
import com.oyx.campus.bean.User;
import com.oyx.campus.service.AdminService;
import com.oyx.campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * ***********用户基本**************
 * 用户登录 *
 * 用户注册*
 *
 * 用户信息更新*
 * 用户密码更新*
 *
 * @author
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	public UserService userService;

	@Autowired
	public AdminService adminService;

	// 登录
	@ResponseBody
	@RequestMapping("/login")
	public Msg login(String studentid, String password, Model model, HttpServletRequest req, HttpSession session ) {
		System.out.println("------------>"+req.getRequestURI());
		User user = null;
		user = userService.getUserByStudentid(studentid);
		if (user == null) {
			return Msg.fail("用户名不存在！！！");
		}
		if (password.equals(user.getPassword())) {
			return Msg.success("登录成功,欢迎登录");
		} else {
			return Msg.fail("登录失败,请确定账号或密码正确");
		}

	}

	//检查学号是否已存在
	@ResponseBody
	@GetMapping("/checkStuId")
	public Msg checkStuId(String studentid){
		int i = userService.checkUserId(studentid);
		if(i==0){
			return Msg.success();
		}else{
			return Msg.fail("学号已存在,如有疑问请联系管理员");
		}
	}
	// 注册
	@ResponseBody
	@RequestMapping("/register")
	public Msg register(@RequestBody User user, Model model) {
		System.out.println(user);
		// 检查学号账号重复
		int countnum = userService.checkUserId(user.getStudentid());
		if (countnum > 0) {
			return Msg.fail("该学号已经注册").add("user", user);
		}
		user.setStuid(0);
		user.setRegistertime(new Date());
		user.setMoney(50.00);
		user.setState(0);
		System.out.println(user.toString());
		int result = userService.saveUser(user);
		if (result <= 0) {
			return Msg.fail("注册失败").add("user", user);
		}
		return Msg.success().add("msg", "注册成功，请登录");
	}

	// 查询当前用户信息 studentid
	@ResponseBody
	@RequestMapping("/getCurrentUser")
	public Msg getCurrentUser(String studentid) {
		User user = userService.getUserByStudentid(studentid);
		/*System.out.println("<<<<<<<"+studentid+">>>>>>>");*/
		if(studentid.equals("undefined")){
			return Msg.fail("兄弟,去登陆啊！！！");
		}
		//根据studentid查询出学校名
		String schoolName =  userService.getSchoolNameByStudentid(studentid);
		System.out.println(schoolName);
		return  Msg.success().add("user",user).add("schoolName", schoolName);
	}

	// 查询发布者信息用户编号 stuid
	@ResponseBody
	@RequestMapping("/getCurrentUser/{stuid}")
	public Msg getCurrentUserByStuId(@PathVariable("stuid") Integer stuid) {
		User user = userService.getUserByStuId(stuid);
		String studentid = user.getStudentid();
		String schoolName =  userService.getSchoolNameByStudentid(studentid);
		System.out.println("发布者信息"+user);

		System.out.println("发布者信息"+schoolName);
		return  Msg.success().add("publicUser",user).add("schoolName", schoolName);
	}



	// 更新用户信息
	@ResponseBody
	@PutMapping("/updateUserInfo")
	public Msg updateUserInfo(@RequestBody User user) {
		int r = userService.updateUserInfo(user);
		if (r > 0) {
			return Msg.success("更新成功");
		}
		return Msg.fail("更新失败");
	}

	// 更新密码
	@ResponseBody
	@PutMapping("/updatePwd")
	public Msg updatePwd( @RequestBody User user) {
	/*	System.out.println(user);
		System.out.println(oldPass);
		String studentid = user.getStudentid();
		User olduser = userService.getUserByStudentid(studentid);
		if (!olduser.getPassword().equals(oldPass)) {
			return Msg.fail("原密码错误");
		}*/
		int r = userService.updateUserInfo(user);
		if (r > 0) {
			return Msg.success("修改成功，请重新登录");
		}
		return Msg.fail("修改失败");
	}











	//到登录注册页面
	@RequestMapping("/toRegister")
	public String toRegisterPage(){
		return "reAndlo";
	}
	//到登录注册页面
	@RequestMapping("/toIndex")
	public String toIndexPage(){
		return "index";
	}
}
