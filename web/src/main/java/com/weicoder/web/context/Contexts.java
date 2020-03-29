package com.weicoder.web.context;

import java.lang.reflect.Method;
import java.util.Map;

import com.weicoder.common.lang.Maps;

/**
 * 全局Selvert控制 
 * @author WD 
 * @version 1.0 
 */
public final class Contexts {
	// Action列表
	public final static Map<String, Object>					ACTIONS			= Maps.newMap();
	// 回调方法处理
	public final static Map<String, Map<String, Method>>	ACTIONS_METHODS	= Maps.newMap();
	// 回调方法处理
	public final static Map<String, Object>					METHODS_ACTIONS	= Maps.newMap();
	// 回调方法处理
	public final static Map<String, Method>					METHODS			= Maps.newMap();

	private Contexts() {}
}