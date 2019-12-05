package com.weicoder.ssh.entity.base;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import com.weicoder.ssh.entity.Entity;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.json.JsonEngine;

/**
 * Entity接口基础实现
 * @author WD 
 * @version 1.0 
 */
@MappedSuperclass
public abstract class BaseEntity implements Entity {
	/**
	 * 判断是否为空
	 */
	public boolean isEmpty() {
		return EmptyUtil.isEmpty(getKey());
	}

	/**
	 * 重写toString 使用json输出属性
	 */
	public String toString() {
		return JsonEngine.toJson(this);
	}

	/**
	 * hashCode方法
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
		return result;
	}

	/**
	 * 判断对象是否相同
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Entity other = (Entity) obj;
		if (getKey() == null) {
			if (other.getKey() != null) return false;
		} else if (!getKey().equals(other.getKey())) return false;
		return true;
	}

	@Override
	public int compareTo(Entity o) {
		// 获得主键
		Serializable key = o.getKey();
		// 判断类型
		if (key instanceof Integer) {
			return Integer.compare(Conversion.toInt(getKey()), Conversion.toInt(key));
		} else {
			return Conversion.toString(getKey()).compareTo(Conversion.toString(key));
		}
	}
}
