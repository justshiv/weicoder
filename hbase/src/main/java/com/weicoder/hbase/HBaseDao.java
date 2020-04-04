package com.weicoder.hbase;

import java.util.List;
import java.util.Map;

/**
 * HBase数据操作接口
 * @author WD 
 * @version 1.0 
 */
public interface HBaseDao {
	/**
	 * 添加数据
	 * @param row 行数
	 * @param key 键
	 * @param value 值
	 */
	void add(String row, String key, String value);

	/**
	 * 读取所有
	 * @return 数据列表
	 */
	List<Map<String, String>> queryAll();
}
