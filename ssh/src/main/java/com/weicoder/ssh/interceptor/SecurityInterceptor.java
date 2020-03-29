package com.weicoder.ssh.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.weicoder.ssh.action.StrutsAction;
import com.weicoder.ssh.params.SecurityParams;
import com.weicoder.web.util.IpUtil;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.EmptyUtil;

/**
 * 基于动态映射方法的安全拦截器 出来掉不合法的一些请求
 * @author WD 
 * @version 1.0 
 */
public final class SecurityInterceptor extends BasicInterceptor<StrutsAction> {
	private static final long			serialVersionUID	= -7879736892830147087L;
	// 方法对应实体Map
	private Map<String, List<String>>	methods;

	@Override
	public void init() {
		// 实例化Map
		methods = Maps.newMap();
		// 安全验证方法
		for (String method : SecurityParams.SECURITY_METHODS) {
			methods.put(method, SecurityParams.getModules(method));
		}
	}

	@Override
	protected boolean before(StrutsAction action, HttpServletRequest request) {
		// 过滤IP
		if (SecurityParams.SECURITY_POWER_IP) {
			// 获得IP
			String ip = IpUtil.getIp(request);
			// 如果不存在允许列表中
			if (!SecurityParams.SECURITY_IPS.contains(ip)) { return false; }
		}
		// 过滤方法
		if (SecurityParams.SECURITY_POWER_METHOD) {
			// 获得执行的方法
			String method = action.getMethod();
			// 实体
			String module = action.getModule();
			// 实体玉方法相同 为自定义方法 直接通过
			if (module.equals(method)) { return true; }
			// 获得方法下的实体列表
			List<String> modules = methods.get(method);
			// 判断是可执行实体方法
			return EmptyUtil.isEmpty(modules) ? false : modules.contains(module);
		}
		// 如果都不过滤 返回true
		return true;
	}
}
