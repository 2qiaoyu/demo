package com.joham.admin.service;

import com.joham.admin.bean.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    /**
     * 登陆验证
     * @param request
     * @param username
     * @param password
     * @return
     */
    int checkLogin(HttpServletRequest request, String username, String password);

    int addUser(Admin admin);

    Admin CheckUserName(String username);

    Admin findUser(String userId);

    int modifyPwd(Admin admin);
}
