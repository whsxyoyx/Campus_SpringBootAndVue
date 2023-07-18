package com.oyx.campus.service.impl;


import com.oyx.campus.bean.Admin;
import com.oyx.campus.mapper.AdminMapper;
import com.oyx.campus.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("adminService")
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	/* 查找账号个数  添加管理员检查账号重复*/
	@Override
	public int getAccountCount(String account) {
		return adminMapper.selectAccountCount(account);
	}

	/*根据账号查找返回admin  登录时根据账号获取该帐号信息 */
	@Override
	public Admin getAdminByAccount(String account) {
		return adminMapper.selectAdminByAccount(account);
	}

	@Override
	public int updateAdminInfo(Admin admin) {
		int result = 0;
		try {
			result = adminMapper.updateByPrimaryKeySelective(admin);
		} catch (Exception e) {

		}
		return result;
	}

	@Override
	public Admin login(String account) {
		Admin admin = null;
		admin = adminMapper.selectAdminByAccount(account);
		return admin;
	}

	@Override
	public int setAdmin(Admin admin) {
		int result = 0;
		try {
			result = adminMapper.insertSelective(admin) ;
		} catch (Exception e) {
		}
		return result;
	}

	//根据aid查询管理员信息
	@Override
	public Admin getAdminByAid(Integer aid) {

		return adminMapper.selectByPrimaryKey(aid);
	}

	/*查询所有管理员*/
	@Override
	public List<Admin> getAllAdmin() {
		return adminMapper.selectAllAdmin();
	}

	//删除管理员
	@Override
	public int delAdmin(Integer aid) {
		return adminMapper.deleteByPrimaryKey(aid);
	}


}
