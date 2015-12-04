/*
 * Copyright 2013 NINGPAI, Inc.All rights reserved.
 * NINGPAI PROPRIETARY / CONFIDENTIAL.USE is subject to licence terms.
 */
package com.joham.util;

import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;

public class BasicSqlSupport {
	private SqlSession session;

	public SqlSession getSession() {
		return session;
	}
	@Resource(name="sqlSession")
	public void setSession(SqlSession session) {
		this.session = session;
	}
}
