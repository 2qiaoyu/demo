package com.joham.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * quartz定时器控制器
 *
 * @author joham
 */

@Controller
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    /**
     * 新增一个定时任务
     *
     * @param time
     * @return
     * @throws Exception
     */
    @RequestMapping("/addQuartz")
    @ResponseBody
    public String addQuartz(String time) throws Exception {
        quartzService.startSchedule("test", "test", time);
        return "1";
    }

}
