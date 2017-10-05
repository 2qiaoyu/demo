package com.joham.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 发送消息服务
 *
 * @author qiaoyu
 */

@EnableJms
@Slf4j
@Service
public class ProducerService {

    /**
     * 消息模板
     */
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage(String destination, String msg ,Map<String, Object> map) {
        log.info("向{}发送消息{}", destination, msg);
        jmsMessagingTemplate.convertAndSend(destination, msg, map);
    }
}
