package com.zby.client;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 任意延时生产者demo
 * 
 * @author mac
 *
 */
public class ArbitrarilyDelayProducerDemo {

	public static void main(String[] args) throws Exception {
		ArbitrarilyDelayProducer arbitrarilyDelayProducer = new ArbitrarilyDelayProducer(
				ArbitrarityDelayConstants.ARBITRARITY_DELAY_PRODUCER_GROUP);
		arbitrarilyDelayProducer.setNamesrvAddr(ArbitrarityDelayConstants.NAME_SERVER_ADDRESS);
		arbitrarilyDelayProducer.start();
		Message msg = new Message(ArbitrarityDelayConstants.ARBITRARITY_DELAY_TOPIC,
				("消息发送时间 :" + new Date()).getBytes(StandardCharsets.UTF_8));
		SendResult sendResult = arbitrarilyDelayProducer.send(msg, 27, TimeUnit.SECONDS);
		System.out.println("消息发送结果:" + sendResult.getSendStatus());
		arbitrarilyDelayProducer.shutdown();
	}
}
