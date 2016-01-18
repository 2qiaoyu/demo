package com.joham.admin.service.impl;

import com.joham.admin.bean.Admin;
import com.joham.admin.dao.AdminDao;
import com.joham.admin.service.AdminService;
import com.joham.util.MD5;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service("AdminService")
public class AdminServiceImpl implements AdminService {
    @Resource(name = "AdminDao")
    private AdminDao adminDao;

    /**
     * 登陆验证
     *
     * @param request
     * @param username
     * @param password
     * @return
     */
    public int checkLogin(HttpServletRequest request, String username, String password) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("username", username);
        Admin admin = adminDao.selectAdminByUserName(paramMap);
        if (admin != null) {
            //判断密码是否正确
            if (MD5.GetMD5Code(password).equals(admin.getPassword())) {
                request.getSession().setAttribute("admin", admin);
                //登陆成功
                return 1;
            } else {
                //密码错误
                return 2;
            }
        } else {
            //用户名不存在
            return 3;
        }
    }

    public int addUser(Admin admin) {
        return adminDao.save(admin);
    }

    public Admin CheckUserName(String username) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        return adminDao.checkUserName(map);
    }

    public Admin findUser(String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        return adminDao.findUser(map);
    }

    public int modifyPwd(Admin admin) {
        return adminDao.update(admin);
    }
}
