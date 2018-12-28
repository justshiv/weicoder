package com.weicoder.memcache;

import java.util.List;
import java.util.Map;

/**
 * MemCached的客户端调用接口
 * @author WD
 */
public interface Memcache {
	/**
	 * 压缩值 当值能压缩时才压缩
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	boolean compress(String key, Object value);

	/**
	 * 根据键获得压缩值 如果是压缩的返回解压缩的byte[] 否是返回Object
	 * @param key 键
	 * @return 值
	 */
	byte[] extract(String key);

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	List<byte[]> extract(String... keys);

	/**
	 * 追加键值
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	boolean append(String key, Object value);

	/**
	 * 设置键值 无论存储空间是否存在相同键，都保存
	 * @param key 键
	 * @param value 值
	 * @return 是否成功
	 */
	boolean set(String key, Object value);

	/**
	 * 根据键获得值
	 * @param key 键
	 * @return 值
	 */
	Object get(String key);

	/**
	 * 获得多个键的数组
	 * @param keys 键
	 * @return 值
	 */
	Object[] get(String... keys);

	/**
	 * 删除键值
	 * @param key 键
	 */
	void remove(String... key);

	/**
	 * 验证键是否存在
	 * @param key 键
	 * @return true 存在 false 不存在
	 */
	boolean exists(String key);

	/**
	 * 获得多个键的Map
	 * @param keys 键
	 * @return 值
	 */
	Map<String, Object> newMap(String... keys);
}