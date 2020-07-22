/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zby.client;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * 任意延时消费者demo
 * 
 * @author zby
 *
 */
public class ArbitrarilyDelayConsumerDemo {

	public static void main(String[] args) throws MQClientException {

		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
				ArbitrarityDelayConstants.ARBITRARITY_DELAY_CONSUMER_GROUP);
		consumer.setNamesrvAddr(ArbitrarityDelayConstants.NAME_SERVER_ADDRESS);
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		consumer.subscribe(ArbitrarityDelayConstants.ARBITRARITY_DELAY_TOPIC, "*");

		ArbitrarilyDelayProducer arbitrarilyDelayProducer = new ArbitrarilyDelayProducer(
				ArbitrarityDelayConstants.ARBITRARITY_DELAY_PRODUCER_GROUP);
		arbitrarilyDelayProducer.setNamesrvAddr(ArbitrarityDelayConstants.NAME_SERVER_ADDRESS);
		arbitrarilyDelayProducer.start();

		ArbitrarilyDelayMessageListenerConcurrently arbitrarilyDelayMessageListenerConcurrently = new ArbitrarilyDelayMessageListenerConcurrently(
				arbitrarilyDelayProducer, (msgs, context) -> {
					System.out.printf("消费消息了，当前时间 %s：%s Receive New Messages: %s %n", new Date(),
							Thread.currentThread().getName(),
							new String(msgs.get(0).getBody(), StandardCharsets.UTF_8));
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				});
		consumer.registerMessageListener(arbitrarilyDelayMessageListenerConcurrently);

		consumer.start();
		System.out.printf("Consumer Started.%n");
	}
}
