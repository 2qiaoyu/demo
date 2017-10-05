package com.joham.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * 消息接受服务
 *
 * @author qiaoyu
 */

@Slf4j
@Component
public class ConsumerService {

    @Value("${amq.destination}")
    private String destination;

    /**
     * 监听消息
     */
    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "${amq.destination}")
    public void receiveQueue(Message message, String text) throws JMSException {
        log.info("从{}接收到消息{}", destination, text);
        if ("joham".equals(message.getStringProperty("title"))) {
            log.info("title{}", message.getStringProperty("title"));
        } else if ("joham1".equals(message.getStringProperty("title"))) {
            log.info("title{}", message.getStringProperty("title"));
        }
    }
}
