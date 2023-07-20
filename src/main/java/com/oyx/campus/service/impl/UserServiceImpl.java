package com.oyx.campus.service.impl;

import com.oyx.campus.bean.User;
import com.oyx.campus.mapper.UserMapper;
import com.oyx.campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Service  默认bean名称userServiceImpl
 * @author Administrator
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;




	/*添加用户*/
	@Override
	public int saveUser(User record) {
		int result = 0;
		try {
			result = userMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/*检查学号是否已存在*/
	@Override
	public int checkUserId(String studentid) {
		int result = 0;
		result = (int) userMapper.checkStudentId(studentid);
		return result;
	}



	/*更新用户信息*/
	@Override
	public int updateUserInfo(User user) {
		int result = 0;
		try {
			result = userMapper.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//搜索栏通用查询 用于以下三种情况
	// 1,都不为空
	// 2，schoolid为空
	// 3，name为空
	/*根据所属学院的编号或姓名查找返回users
	* select *from user WHERE ( schoolid = ? )and( name = ? )
	* */
	@Override
	public List<User> getUsersByNameAndSchoolid(String name,Integer schoolid) {
		List<User> list = null;
		//用户名不为空，schoolid为空的情况(schoolid为空 参数传0)
		if(schoolid==0){
			list = userMapper.getUsersByLike(name);
		}else{
			list = userMapper.getUsersByLikeAndSchoolId(name,schoolid);
		}
		return list;

	}



	/*根据uid主键查找返回user*/
	@Override
	public User getUserById(Integer uid) {
		User user = null;
		user = userMapper.selectByPrimaryKey(uid);
		return user;
	}

	/*根据studentid返回user*/
	@Override
	public User getUserByStudentid(String studentid) {
		User user = null;
		user= userMapper.selectByStudentId(studentid);
		return user;
	}

	/*查询所有用户*/
	public List<User> getAllUsers(){
		return userMapper.selectAllUser();
	}


	//根据id删除用户
	@Override
	public int delUserByUserId(Integer stuid) {
		return userMapper.deleteByPrimaryKey(stuid);
	}

	//批量删除用户
	@Override
	public int delUsers(List<Integer> ids) {
		return userMapper.deleteUserByIds(ids);
	}

	//查询所有被限制用户
	@Override
	public List<User> getLimitUsers()
	{
		return userMapper.getLimitedUser();
	}

	//解除所有被限制用户
	@Override
	public int openAllLimitedUsers() {
		int i=userMapper.openAllLimitedUsers();
		return i;
	}

	//根据用户编号查询用户信息
	@Override
	public User getUserByStuId(Integer stuid) {
		return userMapper.selectByPrimaryKey(stuid);
	}


	/*根据用户表中studentId查找学校名*/
	@Override
	public String getSchoolNameByStudentid(String studentId) {
		User user = userMapper.getSchoolNameByStudentId(studentId);
		System.out.println(user);
		return user.getSchool().getName();
	}


}
