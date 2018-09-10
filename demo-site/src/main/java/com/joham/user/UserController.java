package com.joham.user;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author joham
 */

@Controller
public class UserController {

    @RequestMapping(value = "saveUser", method = RequestMethod.POST)
    @ResponseBody
    public String save(@Valid UserSaveRequest userSaveRequest, BindingResult result) {
        System.out.println(result.getErrorCount());
        return JSON.toJSONString(userSaveRequest);
    }

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    @ResponseBody
    public List<UserSaveRequest> get() {
        List<UserSaveRequest> userSaveRequestList = new ArrayList<>();
        UserSaveRequest userSaveRequest = new UserSaveRequest();
        userSaveRequest.setId(1L);
        userSaveRequest.setName("小明");
        userSaveRequest.setAge(1);
        userSaveRequestList.add(userSaveRequest);
        return userSaveRequestList;
    }

    @RequestMapping(value = "getUser1", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String get1() {
        List<UserSaveRequest> userSaveRequestList = new ArrayList<>();
        UserSaveRequest userSaveRequest = new UserSaveRequest();
        userSaveRequest.setId(1L);
        userSaveRequest.setName("小明");
        userSaveRequest.setAge(1);
        userSaveRequestList.add(userSaveRequest);
        return JSON.toJSONString(userSaveRequestList);
    }

    @RequestMapping(value = "getUser2", method = RequestMethod.GET)
    @ResponseBody
    public Integer get2() {
        return 1;
    }
}
