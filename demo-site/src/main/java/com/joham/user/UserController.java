package com.joham.user;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
