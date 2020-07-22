
package com.zby.server;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 生产者
 * 
 * @author zby
 *
 */
public class Producer {
	public static void main(String[] args) throws Exception {

		DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
		producer.setNamesrvAddr("localhost:9876");
		producer.start();

		Message msg = new Message("TopicTest", ("消息发送时间 :" + new Date()).getBytes(StandardCharsets.UTF_8));
		DelayUtil.delayMsg(msg, 27, TimeUnit.SECONDS);
		SendResult sendResult = producer.send(msg);
		System.out.println("消息发送结果:" + sendResult.getSendStatus());
		producer.shutdown();
	}
}
