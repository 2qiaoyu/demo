/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.adminUser.service;

import com.joham.adminUser.bean.User;
import com.joham.adminUser.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("UserService")
public class UserService {
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}
	@Resource(name="UserDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public User checkLogin(String username,String password){
		Map <String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("username", username);
		paramMap.put("password", password);
		return userDao.checkLogin(paramMap);
	}
	
	public int addUser(User user){
		return userDao.save(user);
	}
	
	public User CheckUserName(String username){
		Map <String,Object> map=new HashMap<String,Object>();
		map.put("username", username);
		return userDao.checkUserName(map);
	}
	
	public User findUser(String userId){
		Map <String,Object> map=new HashMap<String,Object>();
		map.put("userId", userId);
		return userDao.findUser(map);
	}
	
	public int modifyPwd(User user){
		return userDao.update(user);
	}
}
