package com.oyx.campus.service.impl;


import com.oyx.campus.bean.School;
import com.oyx.campus.mapper.SchoolMapper;
import com.oyx.campus.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("schoolService")
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolMapper schoolMapper;

	/*查询所有学校*/
	@Override
	public List<School> getAllSchools() {

		return schoolMapper.selectAllSchool();
	}

	@Override
	public School getSchoolByID(Integer schoolid) {
		return schoolMapper.selectByPrimaryKey(schoolid);
	}

	@Override
	public int updateSchool(School school) {
		return schoolMapper.updateByPrimaryKeySelective(school);
	}

	@Override
	public List<School> getAllSchoolsNoState() {
		return schoolMapper.selectAllSchoolsNoState();
	}

	@Override
	public int setSchool(School school) {

		return schoolMapper.insert(school);
	}

	//删除学院
	@Override
	public int delSchool(Integer schoolid) {
		return schoolMapper.deleteByPrimaryKey(schoolid);
	}


}
