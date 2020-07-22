package com.zby.server;

import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.common.message.Message;

/**
 * 消息延时工具类
 * 
 * @author zby
 *
 */
public class DelayUtil {

	public static final String PROPERTY_MSG_REMAIN_DELAY = "MSG_REMAIN_DELAY_SERVER";

	private DelayUtil() {
	}

	/**
	 * 消息任意延时发送
	 * 
	 * @param msg       消息
	 * @param delayTime 延迟时间
	 * @param timeUnit  延迟时间单位
	 */
	public static void delayMsg(Message msg, long delayTime, TimeUnit timeUnit) {
		msg.putUserProperty(PROPERTY_MSG_REMAIN_DELAY, Long.toString(timeUnit.toSeconds(delayTime)));
	}
}
