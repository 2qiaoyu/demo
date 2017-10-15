package com.joham.admin.service.impl;

import com.joham.admin.bean.Admin;
import com.joham.admin.dao.AdminDao;
import com.joham.admin.service.AdminService;
import com.joham.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员服务实现
 *
 * @author qiaoyu
 */

@Service("AdminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    /**
     * 登陆验证
     */
    @Override
    public int checkLogin(HttpServletRequest request, String username, String password) {
        Map<String, Object> paramMap = new HashMap<>(16);
        paramMap.put("username", username);
        Admin admin = adminDao.selectAdminByUserName(paramMap);
        if (admin != null) {
            //判断密码是否正确
            if (MD5.getMD5Code(password).equals(admin.getPassword())) {
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

    @Override
    public Admin checkUserName(String username) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("username", username);
        return adminDao.checkUserName(map);
    }

    @Override
    public Admin findUser(String userId) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", userId);
        return adminDao.findUser(map);
    }

    @Override
    public int modifyPwd(Admin admin) {
        return adminDao.update(admin);
    }
}
