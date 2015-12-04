/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.adminUser.dao.impl;

import com.joham.adminUser.bean.User;
import com.joham.adminUser.dao.UserDao;
import com.joham.util.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("UserDao")
public class UserDaoImpl extends BasicSqlSupport implements UserDao{

	public User checkLogin(Map<String, Object> paramMap) {
		return getSession().selectOne("com.ningpai.web.dao.UserMapper.checkLogin", paramMap);
	}

	public int save(User user) {
		return getSession().insert("com.ningpai.web.dao.UserMapper.save", user);
	}

	public User checkUserName(Map<String,Object> paramMap) {
		return getSession().selectOne("com.ningpai.web.dao.UserMapper.checkUserName", paramMap);
	}

	public User findUser(Map<String, Object> paramMap) {
		return getSession().selectOne("com.ningpai.web.dao.UserMapper.findUser", paramMap);
	}

	public int update(User user) {
		return getSession().update("com.ningpai.web.dao.UserMapper.update", user);
	}
}
