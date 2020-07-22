package com.zby.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 任意延时消费者监听器代理
 * 
 * @author zby
 *
 */
public class ArbitrarilyDelayMessageListenerConcurrently implements MessageListenerConcurrently {

	private ArbitrarilyDelayProducer arbitrarilyDelayProducer;

	private MessageListenerConcurrently proxyListener;

	public ArbitrarilyDelayMessageListenerConcurrently(ArbitrarilyDelayProducer arbitrarilyDelayProducer,
			MessageListenerConcurrently proxyListener) {
		this.arbitrarilyDelayProducer = arbitrarilyDelayProducer;
		this.proxyListener = proxyListener;
	}

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		MessageExt messageExt = msgs.get(0);
		String remainDelay = messageExt.getUserProperty(ArbitrarityDelayConstants.REMAIN_DELAY_KEY);
		if (remainDelay != null && Long.parseLong(remainDelay) > 0) {
			try {
				System.out.println("msgId:" + messageExt.getMsgId() + ",remainDelay:" + remainDelay);
				arbitrarilyDelayProducer.send(messageExt, Long.parseLong(remainDelay), TimeUnit.SECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
		return proxyListener.consumeMessage(msgs, context);
	}

}
