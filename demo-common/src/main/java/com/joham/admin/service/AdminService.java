package com.joham.admin.service;

import com.joham.admin.bean.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员服务
 *
 * @author joham
 */

public interface AdminService {
    /**
     * 登陆验证
     */
    int checkLogin(HttpServletRequest request, String username, String password);

    Admin checkUserName(String username);

    Admin findUser(String userId);

    int modifyPwd(Admin admin);
}
