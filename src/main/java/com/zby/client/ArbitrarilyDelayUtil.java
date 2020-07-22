package com.zby.client;

/**
 * 任意延时工具类
 * 
 * @author zby
 *
 */
public class ArbitrarilyDelayUtil {

	/**
	 * 延时时间
	 */
	private static final long[] delayArray = new long[] { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192,
			16384, 32768, 65536, 131072 };

	private ArbitrarilyDelayUtil() {
	}

	/**
	 * 根据延时等级获取延时时间
	 * 
	 * @param delayLevel 延时等级
	 * @return 延时时间
	 */
	public static long getDelayTimeFromDelayLevel(int delayLevel) {
		validDelayLevel(delayLevel);
		if (delayLevel == 0) {
			return 0;
		}
		return delayArray[delayLevel - 1];

	}

	/**
	 * 根据延时时间获取最近的延时等级
	 * 
	 * @param delayTime
	 * @return
	 */
	public static int getLatestDelayLevelFromDelayTime(long delayTime) {
		validDelayTime(delayTime);
		if (delayTime == 0) {
			return 0;
		}
		for (int i = 0; i < delayArray.length - 1; i++) {
			if (delayTime >= delayArray[i] && delayTime < delayArray[i + 1]) {
				return i + 1;
			}
		}
		return delayArray.length;
	}

	/**
	 * 校验延时时间
	 */
	private static void validDelayTime(long delayTime) {
		if (delayTime < 0) {
			throw new IllegalArgumentException("Not supported delay time:" + delayTime);
		}
	}

	/**
	 * 校验延时等级
	 */
	private static void validDelayLevel(int delayLevel) {
		if (delayLevel < 0 || delayLevel > delayArray.length) {
			throw new IllegalArgumentException("Not supported delay level:" + delayLevel);
		}
	}
}
