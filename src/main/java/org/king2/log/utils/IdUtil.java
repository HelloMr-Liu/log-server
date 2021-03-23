package org.king2.log.utils;

import java.time.Instant;

public class IdUtil {
	
	/** 工作机器ID(0~31) */
	private static long workerId = 2L;

	/** 数据中心ID(0~31) */
	private static long dataCenterId = 1L;
	
	
	/** 开始时间截 (2015-01-01) */
	private final static long twepoch = 1489111610226L;

	/** 机器id所占的位数 */
	private final static long workerIdBits = 5L;

	/** 数据标识id所占的位数 */
	private final static long dataCenterIdBits = 5L;

	/** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
//	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

	/** 支持的最大数据标识id，结果是31 */
//	private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

	/** 序列在id中占的位数 */
	private final static long sequenceBits = 12L;

	/** 机器ID向左移12位 */
	private final static long workerIdShift = sequenceBits;

	/** 数据标识id向左移17位(12+5) */
	private final static long dataCenterIdShift = sequenceBits + workerIdBits;

	/** 时间截向左移22位(5+5+12) */
	private final static long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

	/** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

	

	/** 毫秒内序列(0~4095) */
	private static long sequence = 0L;

	/** 上次生成ID的时间截 */
	private static long lastTimestamp = -1L;
	/**
	 * 时间戳偏差值
	 */
	private static long offsetTimestamp = 0L;

	
	public static void setworker(long workerId,long dataCenterId,long offsetTimestamp) {
		IdUtil.workerId=workerId%32;
		IdUtil.dataCenterId = dataCenterId%32;
		IdUtil.offsetTimestamp = offsetTimestamp;
	}
	// ==============================Methods==========================================
	/**
	 * 获得下一个ID (该方法是线程安全的)
	 * 
	 * @return SnowflakeId
	 */
	public static synchronized long nextId() {
		long timestamp = timeGen();

		// 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format(
					"Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		// 如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			// 毫秒内序列溢出
			if (sequence == 0) {
				// 阻塞到下一个毫秒,获得新的时间戳
				timestamp = tilNextMillis(lastTimestamp);
			}
		}
		// 时间戳改变，毫秒内序列重置
		else {
			sequence = 0L;
		}
		
		// 上次生成ID的时间截
		lastTimestamp = timestamp;

		// 移位并通过或运算拼到一起组成64位的ID
		return ((timestamp - twepoch) << timestampLeftShift) //
				| (dataCenterId << dataCenterIdShift) //
				| (workerId << workerIdShift) //
				| sequence;
	}

	/**
	 * 阻塞到下一个毫秒，直到获得新的时间戳
	 * 
	 * @param lastTimestamp 上次生成ID的时间截
	 * @return 当前时间戳
	 */
	private static long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * 返回以毫秒为单位的当前时间
	 * 
	 * @return 当前时间(毫秒)
	 */
	private static long timeGen() {
		return Instant.now().toEpochMilli()+offsetTimestamp;
	}
}
