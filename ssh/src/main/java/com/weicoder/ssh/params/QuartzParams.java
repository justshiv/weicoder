package com.weicoder.ssh.params;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.params.Params;

/**
 * Quartz任务读取参数
 * @author WD 
 * @version 1.0 
 */
public final class QuartzParams {
	// 前缀
	private final static String		PREFIX	= "quartz";
	/** 任务开关 */
	public final static boolean		POWER	= Params.getBoolean("quartz.power", false);
	/** Spring任务开关 */
	public final static boolean		SPRING	= Params.getBoolean("quartz.spring", false);
	/** 执行任务名称数组 */
	public final static String[]	NAMES	= Params.getStringArray("quartz.names", ArrayConstants.STRING_EMPTY);

	/**
	 * 获得Quartz执行任务类<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: quartz.*.class = ? <br/>
	 * XML: {@literal <quartz><*><class>?</class></*></quartz>}</h2>
	 * @return Quartz执行任务名称数组
	 */
	public static String getClass(String name) {
		return Params.getString(Params.getKey(PREFIX, name, "class"));
	}

	/**
	 * 获得Quartz执行任务类执行时间<br/>
	 * 需在配置文件中配置<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: quartz.*.trigger = ? <br/>
	 * XML: {@literal <quartz><*><trigger>?</trigger></*></quartz>}</h2>
	 * @return Quartz执行任务名称数组
	 */
	public static String[] getTrigger(String name) {
		return Params.getStringArray(Params.getKey(PREFIX, name, "trigger"), ArrayConstants.STRING_EMPTY);
	}

	private QuartzParams() {}
}
