package com.weicoder.common.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.SystemConstants; 

/**
 * 定时任务工具类
 * 
 * @author  WD
 * @version 1.0
 */
public final class ScheduledUtile {
	/** 并发定时任务池 */
	public final static ScheduledExecutorService POOL = Executors.newScheduledThreadPool(SystemConstants.CPU_NUM * 2);

	/**
	 * 执行定时任务 按初始时间间隔
	 * 
	 * @param  command 线程任务
	 * @param  period  间隔时间 毫秒
	 * @return
	 */
	public static ScheduledFuture<?> rate(Runnable command, long period) {
		return POOL.scheduleAtFixedRate(command, period, period, TimeUnit.MILLISECONDS);
	}

	/**
	 * 执行定时任务 按初始时间间隔
	 * 
	 * @param  command 线程任务
	 * @param  period  间隔时间 秒
	 * @return
	 */
	public static ScheduledFuture<?> rate(Runnable command, int period) {
		return rate(command, period * DateConstants.TIME_SECOND);
	}

	/**
	 * 执行定时任务 按执行线程时间间隔
	 * 
	 * @param  command 线程任务
	 * @param  delay   间隔时间 毫秒
	 * @return
	 */
	public static ScheduledFuture<?> delay(Runnable command, long delay) {
		return POOL.scheduleWithFixedDelay(command, delay, delay, TimeUnit.MILLISECONDS);
	}

	/**
	 * 执行定时任务 按执行线程间隔
	 * 
	 * @param  command 线程任务
	 * @param  delay   间隔时间 秒
	 * @return
	 */
	public static ScheduledFuture<?> delay(Runnable command, int delay) {
		return delay(command, delay * DateConstants.TIME_SECOND);
	}

	private ScheduledUtile() {
	}
}
