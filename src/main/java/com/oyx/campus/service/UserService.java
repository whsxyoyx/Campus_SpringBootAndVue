package com.oyx.campus.service;


import com.oyx.campus.bean.User;

import java.util.List;

public interface UserService {

	/**
	 * 用户注册
	 * @return
	 */
	public int saveUser(User record);

	/**
	 * 检查学号是否已存在
	 * @param studentid
	 * @return
	 */
	public int checkUserId(String studentid);

	/**
	 * 根据studentid返回user
	 * @param studentid
	 * @return 返回的是一个user对象
	 */
	public User getUserByStudentid(String studentid);

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	/**
     * 根据学院编号或姓名查找返回user
     * @return
     */
	List<User> getUsersByNameAndSchoolid(String name,Integer schoolid);

    /**
     * 根据uid主键查找返回user
     * @param uid
     * @return
     */
    public User getUserById(Integer uid);

	/*根据用户表中studentid查找学校名*/
	public String getSchoolNameByStudentid(String studentid);

	/*查询所有用户*/
	public List<User> getAllUsers();



	//根据任务id删除任务
	int delUserByUserId(Integer stuid);

	//批量删除任务
	int delUsers(List<Integer> asList);

	//查询所有被限制用户
	List<User> getLimitUsers();
	//解除所有被限制用户
	int openAllLimitedUsers();

	User getUserByStuId(Integer stuid);
}
