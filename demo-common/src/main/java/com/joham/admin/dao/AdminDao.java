package com.joham.admin.dao;

import com.joham.admin.bean.Admin;

import java.util.Map;

public interface AdminDao {

    /**
     * 根据用户名查询管理员
     */
    Admin selectAdminByUserName(Map<String, Object> paramMap);

    /**
     * 判断用户名是否存在
     */
    Admin checkUserName(Map<String, Object> paramMap);

    /**
     * 根据id查询用户
     */
    Admin findUser(Map<String, Object> paramMap);

    /**
     * 修改密码
     */
    int update(Admin admin);
}
