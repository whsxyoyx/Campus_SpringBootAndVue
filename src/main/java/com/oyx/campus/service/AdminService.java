package com.oyx.campus.service;


import com.oyx.campus.bean.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminService {

	/**
     * 查找账号个数
     * @param account
     * @return
     */
    public int getAccountCount(@Param("account")String account);

    /**
     * 根据账号查找返回admin
     * @param account
     * @return
     */
    public Admin getAdminByAccount(@Param("account")String account);

    /**
	 * 更新信息
	 * @param admin
	 * @return
	 */
	public int updateAdminInfo(Admin admin);

	/**
	 * 登录
	 * @param account
	 * @return
	 */
	public Admin login(String account);

	/**
	 * 添加管理员
	 * @return
	 */
	public int setAdmin(Admin admin);

	/**
	 * 根据aid查询管理员信息
	 * @param aid
	 * @return
	 */
	public Admin getAdminByAid(Integer aid);


	//查询所有管理员
    List<Admin> getAllAdmin();

	//删除管理员
	int delAdmin(Integer aid);
}
