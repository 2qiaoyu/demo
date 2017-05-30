package com.joham.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by joham on 2017/4/9.
 */
@Service("producerService")
public class ProducerService {

    private Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    public void sendMessage(Destination destination, final String msg) {
        System.out.println("向队列" + destination.toString() + "发送消息---------------------->" + msg);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    public void sendMessage(final String msg) {
        String destination = jmsTemplate.getDefaultDestinationName();
        System.out.println("向队列" + destination + "发送消息---------------------->" + msg);
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    public void sendMessage() {
        Object message1 = "corn消息内容：111";
        //分 时 天 月 星期几
        jmsTemplate.convertAndSend(message1, new ScheduleMessagePostProcessor("40 22 * * *"));
        logger.info("消息1：[" + message1 + "] 延时发送成功！");

        jmsTemplate.convertAndSend(message1, new ScheduleMessagePostProcessor("50 22 * * *"));
        logger.info("消息1：[" + message1 + "] 延时发送成功！");


        Object message2 = "message：222";
        jmsTemplate.convertAndSend(message2, new ScheduleMessagePostProcessor(30 * 1000));//延时30秒
        jmsTemplate.convertAndSend(message2, new ScheduleMessagePostProcessor(60 * 1000));//延时1分钟
        logger.info("消息2：[" + message2 + "] 延时发送成功！");
    }
}
