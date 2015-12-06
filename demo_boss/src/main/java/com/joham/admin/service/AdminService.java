/*
 * Copyright 2013 joham, Inc.All rights reserved.
 * joham PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.admin.service;

import com.joham.admin.bean.Admin;
import com.joham.admin.dao.AdminDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("AdminService")
public class AdminService {
	private AdminDao adminDao;

	public AdminDao getAdminDao() {
		return adminDao;
	}
	@Resource(name="UserDao")
	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
	
	public Admin checkLogin(String username,String password){
		Map <String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("username", username);
		paramMap.put("password", password);
		return adminDao.checkLogin(paramMap);
	}
	
	public int addUser(Admin admin){
		return adminDao.save(admin);
	}
	
	public Admin CheckUserName(String username){
		Map <String,Object> map=new HashMap<String,Object>();
		map.put("username", username);
		return adminDao.checkUserName(map);
	}
	
	public Admin findUser(String userId){
		Map <String,Object> map=new HashMap<String,Object>();
		map.put("userId", userId);
		return adminDao.findUser(map);
	}
	
	public int modifyPwd(Admin admin){
		return adminDao.update(admin);
	}
}
