package com.joham.admin.dao.impl;

import com.joham.admin.bean.Admin;
import com.joham.admin.dao.AdminDao;
import com.joham.util.BasicSqlSupport;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("AdminDao")
public class AdminDaoImpl extends BasicSqlSupport implements AdminDao {

    public Admin selectAdminByUserName(Map<String, Object> paramMap) {
        return getSession().selectOne("com.joham.admin.dao.AdminDao.selectAdminByUserName", paramMap);
    }

    public int save(Admin admin) {
        return getSession().insert("com.joham.admin.dao.AdminDao.save", admin);
    }

    public Admin checkUserName(Map<String, Object> paramMap) {
        return getSession().selectOne("com.joham.admin.dao.AdminDao.checkUserName", paramMap);
    }

    public Admin findUser(Map<String, Object> paramMap) {
        return getSession().selectOne("com.joham.admin.dao.AdminDao.findUser", paramMap);
    }

    public int update(Admin admin) {
        return getSession().update("com.joham.admin.dao.AdminDao.update", admin);
    }
}
