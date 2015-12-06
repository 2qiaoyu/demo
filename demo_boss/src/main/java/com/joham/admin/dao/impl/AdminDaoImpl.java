/*
 * Copyright 2013 joham, Inc.All rights reserved.
 * joham PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.admin.dao.impl;

import com.joham.admin.bean.Admin;
import com.joham.admin.dao.AdminDao;
import com.joham.util.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("UserDao")
public class AdminDaoImpl extends BasicSqlSupport implements AdminDao {

	public Admin checkLogin(Map<String, Object> paramMap) {
		return getSession().selectOne("com.joham.web.dao.AdminMapper.checkLogin", paramMap);
	}

	public int save(Admin admin) {
		return getSession().insert("com.joham.web.dao.AdminMapper.save", admin);
	}

	public Admin checkUserName(Map<String,Object> paramMap) {
		return getSession().selectOne("com.joham.web.dao.AdminMapper.checkUserName", paramMap);
	}

	public Admin findUser(Map<String, Object> paramMap) {
		return getSession().selectOne("com.joham.web.dao.AdminMapper.findUser", paramMap);
	}

	public int update(Admin admin) {
		return getSession().update("com.joham.web.dao.AdminMapper.update", admin);
	}
}
