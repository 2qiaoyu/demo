package com.joham.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息发送控制器
 *
 * @author qiaoyu
 */
@Controller
@Slf4j
public class MessageController {

    @Value("${amq.destination}")
    private String destination;

    @Autowired
    private ProducerService producer;

    /**
     * 发送消息
     *
     * @param msg 消息内容
     */
    @RequestMapping(value = "/SendMessage", method = RequestMethod.POST)
    @ResponseBody
    public String send(String msg, String title) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("title", title);
        producer.sendMessage(destination, msg, map);
        return "1";
    }
}
