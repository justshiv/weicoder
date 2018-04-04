package com.weicoder.common.lang;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;

/**
 * Map的帮助类,获得Map的一些操作
 * @author WD
 */
public final class Maps {
	/**
	 * 获得map中的元素 如果有一者为空 返回 null 如果key对应的值为空 用Class实例化个新值放在map例
	 * @param map 取数据的map
	 * @param key 对应的key
	 * @param c 对应值的类
	 * @param <K> key
	 * @param <V> val
	 * @return 值
	 */
	public static <K, V> V get(Map<K, V> map, K key, Class<V> c) {
		// 如果map和key val 为空
		if (EmptyUtil.isEmpty(map) || key == null || c == null) {
			return null;
		}
		// 获得值
		V val = map.get(key);
		// 如果值为空
		if (val == null) {
			// 实例化并保存
			map.put(key, val = BeanUtil.newInstance(c));
		}
		// 返回值
		return val;
	}

	/**
	 * 根据map获得相关的list值，如果值为空生成新list
	 * @param map 列表
	 * @param key 键
	 * @param c 类
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return List
	 */
	public static <K, V> List<V> getList(Map<K, List<V>> map, K key, Class<V> c) {
		// 如果map和key val 为空
		if (map == null || key == null || c == null) {
			return null;
		}
		// 获得值
		List<V> val = map.get(key);
		// 如果值为空
		if (val == null) {
			// 实例化并保存
			map.put(key, val = Lists.newList());
		}
		// 返回值
		return val;
	}

	/**
	 * 判断是否Map
	 * @param obj 对象
	 * @return 是否Map
	 */
	public static boolean isMap(Object obj) {
		return !EmptyUtil.isEmpty(obj) && obj instanceof Map<?, ?>;
	}

	/**
	 * 获得Map实例
	 * @param key 键
	 * @param value 值
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	public static <K, V> Map<K, V> newMap(K key, V value) {
		// 获得Map
		Map<K, V> map = newMap();
		// 设置键值
		map.put(key, value);
		// 返回Map
		return map;
	}

	/**
	 * 获得Map实例
	 * @param keys 键数组
	 * @param values 值数组
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	public static <K, V> Map<K, V> newMap(K[] keys, V[] values) {
		return newMap(Lists.newList(keys), Lists.newList(values));
	}

	/**
	 * 获得Map实例
	 * @param keys 键数组
	 * @param values 值数组
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	public static <K, V> Map<K, V> newMap(List<K> keys, List<V> values) {
		// 判断key和value为空或则键值数量不同 返回空Map
		if (EmptyUtil.isEmpty(keys) || EmptyUtil.isEmpty(values) || keys.size() != values.size()) {
			return emptyMap();
		}
		// 获得Map
		Map<K, V> map = newMap();
		// 循环填充map
		for (int i = 0; i < keys.size(); i++) {
			// 设置键值
			map.put(keys.get(i), values.get(i));
		}
		// 返回Map
		return map;
	}

	/**
	 * 获得Map实例 默认初始化大小为10
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	public static <K, V> Map<K, V> newMap() {
		return new HashMap<K, V>();
	}

	/**
	 * 获得Map实例
	 * @param size 初始化大小
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	public static <K, V> Map<K, V> newMap(int size) {
		return new HashMap<K, V>(size < 1 ? 1 : size);
	}

	/**
	 * 获得Map实例
	 * @param map 初始化的Map
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	public static <K, V> Map<K, V> newMap(Map<K, V> map) {
		return map == null ? new HashMap<K, V>() : new HashMap<K, V>(map);
	}

	/**
	 * 获得Map实例
	 * @param maps 初始化的Map
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	@SafeVarargs
	public static <K, V> Map<K, V> newMap(Map<K, V>... maps) {
		// 获得一个map
		Map<K, V> map = newMap();
		// 循环maps
		for (int i = 0; i < maps.length; i++) {
			// 添加到map
			map.putAll(maps[i]);
		}
		// 返回map
		return map;
	}

	/**
	 * 获得同步的Map实例 实现类是ConcurrentHashMap 默认初始化大小为10
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
		return new ConcurrentHashMap<K, V>();
	}

	/**
	 * 获得同步的Map实例 实现类是ConcurrentHashMap
	 * @param size 初始化大小
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	public static <K, V> ConcurrentMap<K, V> newConcurrentMap(int size) {
		return new ConcurrentHashMap<K, V>(size);
	}

	/**
	 * 获得同步的Map实例 实现类是ConcurrentHashMap
	 * @param map 初始化的Map
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return Map
	 */
	public static <K, V> ConcurrentMap<K, V> newConcurrentMap(Map<K, V> map) {
		return new ConcurrentHashMap<K, V>(map);
	}

	/**
	 * 获得一个不可变的空Map
	 * @param <K> 泛型
	 * @param <V> 泛型
	 * @return 一个不可变的空Map
	 */
	public static <K, V> Map<K, V> emptyMap() {
		return Collections.emptyMap();
	}

	/**
	 * 获得列表数量
	 * @param map 数据列表
	 * @return 数量
	 */
	public static int size(Map<?, ?> map) {
		return EmptyUtil.isEmpty(map) ? 0 : map.size();
	}

	private Maps() {}
}
