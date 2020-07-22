package com.zby.client;

import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 任意延时生产者
 * 
 * @author zby
 *
 */
public class ArbitrarilyDelayProducer extends DefaultMQProducer {

	public ArbitrarilyDelayProducer(String producerGroup) {
		super(producerGroup);
	}

	public SendResult send(final Message msg, long delay, TimeUnit timeUnit)
			throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
		int delayTimeLevel = ArbitrarilyDelayUtil.getLatestDelayLevelFromDelayTime(timeUnit.toSeconds(delay));
		msg.setDelayTimeLevel(delayTimeLevel);
		msg.putUserProperty(ArbitrarityDelayConstants.REMAIN_DELAY_KEY, Long
				.toString(timeUnit.toSeconds(delay) - ArbitrarilyDelayUtil.getDelayTimeFromDelayLevel(delayTimeLevel)));
		return send(msg);
	}

}
