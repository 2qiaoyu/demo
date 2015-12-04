/*
 * Copyright 2013 joham, Inc.All rights reserved.
 * joham PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.adminUser.dao;

import com.joham.adminUser.bean.User;

import java.util.Map;

public interface UserDao {
	/*
	 *登陆验证 
	 */
	User checkLogin(Map<String, Object> paramMap);
	
	/*
	 *判断用户名是否存在
	 */
	User checkUserName(Map<String, Object> paramMap);
	
	/*
	 * 用户注册
	 */
	int save(User user);
	
	/*
	 * 根据id查询用户
	 */
	
	User findUser(Map<String, Object> paramMap);
	
	/*
	 * 修改密码
	 */
	int update(User user);
}
