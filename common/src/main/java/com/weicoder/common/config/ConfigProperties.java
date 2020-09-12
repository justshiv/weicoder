package com.weicoder.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.weicoder.common.U;
import com.weicoder.common.U.R;
import com.weicoder.common.W;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.StringUtil;

/**
 * 读取配置类
 * 
 * @author WD
 */
public class ConfigProperties implements Config {
	// Properties配置
	private Properties ps;

	/**
	 * 构造参数
	 * 
	 * @param fileName 文件名 可以已,分割
	 */
	public ConfigProperties(String fileName) {
		// 声明Properties
		ps = new Properties();
		// 循环加载文件
//		for (String name : StringUtil.split(fileName, StringConstants.COMMA)) {
		try (InputStream in = R.loadResource(fileName + ".properties")) {
			// 有配置文件加载
			if (in != null) {
				ps.load(in);
//				System.getProperties().putAll(ps);
			}
		} catch (IOException e) {
		}
//		}
	}

	/**
	 * 构造参数
	 * 
	 * @param ps
	 */
	public ConfigProperties(Properties ps) {
		this.ps = ps;
	}

	/**
	 * 获取属性配置
	 * 
	 * @return
	 */
	public Properties getProperties() {
		return ps;
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public List<String> getList(String key, List<String> defaultValue) {
		return Lists.newList(getStringArray(key, U.E.isEmpty(defaultValue) ? ArrayConstants.STRING_EMPTY : Lists.toArray(defaultValue)));
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	public String[] getStringArray(String key) {
		return getStringArray(key, ArrayConstants.STRING_EMPTY);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public String[] getStringArray(String key, String[] defaultValue) {
		// 获得字符串
		String s = getString(key);
		// 如果为空返回默认值 不为空以,拆分
		if (U.E.isEmpty(s))
			return defaultValue;
		else
			return s.split(StringConstants.COMMA);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	public String getString(String key) {
		return getString(key, StringConstants.EMPTY);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public String getString(String key, String defaultValue) {
		return StringUtil.trim(ps.getProperty(key, defaultValue));
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		return W.C.toBoolean(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	public int getInt(String key) {
		return getInt(key, 0);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public int getInt(String key, int defaultValue) {
		return W.C.toInt(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key 属性key
	 * @return value
	 */
	public byte getByte(String key) {
		return getByte(key, Byte.parseByte("0"));
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public byte getByte(String key, byte defaultValue) {
		return W.C.toByte(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public long getLong(String key, long defaultValue) {
		return W.C.toLong(getString(key), defaultValue);
	}

	/**
	 * 获得属性value
	 * 
	 * @param key          属性key
	 * @param defaultValue 默认值
	 * @return value
	 */
	public short getShort(String key, short defaultValue) {
		return W.C.toShort(getString(key), defaultValue);
	}

	/**
	 * 检查键是否存在
	 * 
	 * @param key 键
	 * @return 是否存在值
	 */
	public boolean exists(String key) {
		return ps.containsKey(key);
	}

	@Override
	public String toString() {
		return "Config [ps=" + ps + "]";
	}
}