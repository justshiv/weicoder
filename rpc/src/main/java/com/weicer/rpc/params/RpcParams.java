package com.weicer.rpc.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.IpUtil;

/**
 * rpc参数
 * 
 * @author wudi
 */
public final class RpcParams {
	/** redis前缀 */
	public final static String PREFIX = "rpc";
	// Properties配置
	private final static Config CONFIG = ConfigFactory.getConfig(PREFIX);
	// 配置使用
	private final static String KEY_HOST = "host";
	private final static String KEY_PORT = "port";
	/** rpc服务端口 */
	public final static int     PORT     = Params.getInt("rpc.port", 6666);
	/** rpc协议 暂时给sofa rpc使用 默认bolt */
	public final static String  PROTOCOL = Params.getString("rpc.protocol", "bolt");
	/** rpc是否守护线程 给sofa rpc使用 */
	public final static boolean DAEMON   = Params.getBoolean("rpc.daemon", true);

	/**
	 * rpc服务器地址
	 * 
	 * @param  name 名
	 * @return      服务器地址
	 */
	public static String getHost(String name) {
		return CONFIG.getString(Params.getKey(name, KEY_HOST),
				Params.getString(getKey(name, KEY_HOST), IpUtil.LOCAL_IP));
	}

	/**
	 * rpc服务器端口
	 * 
	 * @param  name 名
	 * @return      端口
	 */
	public static int getPort(String name) {
		return CONFIG.getInt(Params.getKey(name, KEY_PORT), Params.getInt(getKey(name, KEY_PORT), 6666));
	}

	/**
	 * 用name替换键
	 * 
	 * @param  name 名称
	 * @param  key  键
	 * @return      替换后的键
	 */
	private static String getKey(String name, String key) {
		return Params.getKey(PREFIX, name, key);
	}

	private RpcParams() {
	}
}
