package com.weicoder.frame.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import com.weicoder.common.lang.Maps; 
import com.weicoder.dao.bean.Pagination;
import com.weicoder.dao.context.DaoContext;
import com.weicoder.dao.service.SuperService;

/**
 * 基于Hibernate的查询器
 * @author WD
 * @version 1.0 2012-06-24
 */
public final class QueryService {

	/**
	 * 根据ID 获得实体
	 * @param entity 要查询的实体
	 * @param pk 主键
	 * @return 实体
	 */
	public Object get(String entity, Serializable pk) {
		return SuperService.DAO.get(DaoContext.getClass(entity), pk);
	}

	/**
	 * 获得持久化对象
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public Object get(String entity, String property, Object value) {
		return SuperService.DAO.get(DaoContext.getClass(entity), property, value);
	}

	/**
	 * 获得持久化对象
	 * @param entity 要查询的实体
	 * @param data 属性列与值
	 * @return 要获得的持久化对象，如果不存在返回null
	 */
	public Object get(String entity, Map<String, Object> data) {
		return SuperService.DAO.get(DaoContext.getClass(entity), data);
	}

	// /**
	// * 使用索引查询
	// * @param entity 实体类
	// * @param property 属性名
	// * @param value 属性值
	// * @param firstResult 重第几条开始查询
	// * @param maxResults 一共查回多少条
	// * @return 数据列表
	// */
	// public List<? extends Object> search(String entity, String property, Object value) {
	// return search(entity, property, value, -1, -1);
	// }

	// /**
	// * 使用索引查询
	// * @param entity 实体类
	// * @param property 属性名
	// * @param value 属性值
	// * @param maxResults 一共查回多少条
	// * @return 数据列表
	// */
	// public List<? extends Object> search(String entity, String property, Object value,
	// int maxResults) {
	// return search(entity, property, value, -1, maxResults);
	// }

	// /**
	// * 使用索引查询
	// * @param entity 实体类
	// * @param property 属性名
	// * @param value 属性值
	// * @param firstResult 重第几条开始查询
	// * @param maxResults 一共查回多少条
	// * @return 数据列表
	// */
	// public List<? extends Object> search(String entity, String property, Object value,
	// int firstResult, int maxResults) {
	// return SuperService.DAO.search(DaoContext.getClass(entity), property, value, firstResult,
	// maxResults);
	// }

	/**
	 * 查询全部
	 * @param entity 要查询的实体
	 * @return 全部实体
	 */
	public List<? extends Object> list(String entity) {
		return list(entity, -1);
	}

	/**
	 * 查询指定条数
	 * @param entity 要查询的实体
	 * @param num 要查询的条数
	 * @return 全部实体
	 */
	public List<? extends Object> list(String entity, int num) {
		return list(entity, -1, num);
	}

	/**
	 * 查询指定条数
	 * @param entity 要查询的实体
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 全部实体
	 */
	public List<? extends Object> list(String entity, int firstResult, int maxResults) {
		return SuperService.DAO.list(DaoContext.getClass(entity), firstResult, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, String property, List<Object> values) {
		return list(entity, property, values, -1);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, String property, List<Object> values,
			int maxResults) {
		return list(entity, property, values, -1, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, String property, List<Object> values,
			int firstResult, int maxResults) {
		return SuperService.DAO.in(DaoContext.getClass(entity), property, values, -1, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param op 操作符号
	 * @param map 对应的属性和值
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, List<String> keys, List<Object> values) {
		return list(entity, keys, values, -1, -1);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param op 操作符号
	 * @param map 对应的属性和值
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, List<String> keys, List<Object> values,
			int maxResults) {
		return list(entity, keys, values, -1, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param parames 参数map
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, Map<String, List<Object>> parames) {
		return list(entity, parames, -1);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param parames 参数map
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, Map<String, List<Object>> parames,
			int maxResults) {
		return list(entity, parames, -1, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param parames 参数map
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, Map<String, List<Object>> parames,
			int firstResult, int maxResults) {
		return SuperService.DAO.in(DaoContext.getClass(entity), parames, firstResult, maxResults);
	}

	/**
	 * 查询属性名含有列表的实体列表
	 * @param entity 要查询的实体
	 * @param keys 键列表
	 * @param values 值列表
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, List<String> keys, List<Object> values,
			int firstResult, int maxResults) {
		return SuperService.DAO.eq(DaoContext.getClass(entity), Maps.newMap(keys, values),
				firstResult, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, String property, Object value) {
		return list(entity, property, value, -1);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, String property, Object value,
			int maxResults) {
		return list(entity, property, value, -1, maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @param firstResult 重第几条开始查询
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<? extends Object> list(String entity, String property, Object value,
			int firstResult, int maxResults) {
		return SuperService.DAO.eq(DaoContext.getClass(entity), property, value, firstResult,
				maxResults);
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public List<Entity> list(String entity, String property, List<Object> values,
			Pagination pager) {
		return SuperService.DAO.in(DaoContext.getClass(entity), property, values,
				pager.getFirstResult(), pager.getMaxResults());
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param values 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public List<Entity> list(String entity, String property, List<Object> values,
			Map<String, Object> orders, Pagination pager) {
		return SuperService.DAO.in(DaoContext.getClass(entity), property, values, orders,
				pager.getFirstResult(), pager.getMaxResults());
	}

	/**
	 * 查询属性名等值的实体列表
	 * @param entity 要查询的实体
	 * @param property 属性名
	 * @param value 属性值
	 * @param pager 分页Bean
	 * @return 数据列表
	 */
	public List<Entity> list(String entity, String property, Object value, Pagination pager) {
		return SuperService.DAO.eq(DaoContext.getClass(entity), property, value,
				pager.getFirstResult(), pager.getMaxResults());
	}

	/**
	 * 根据实体条件查询数量
	 * @param entity 实体
	 * @return 数量
	 */
	public int count(String entity) {
		return SuperService.DAO.count(DaoContext.getClass(entity));
	}

	/**
	 * 根据实体条件查询数量
	 * @param entity 实体
	 * @param property 属性名
	 * @param value 属性值
	 * @return 数量
	 */
	public int count(String entity, String property, Object value) {
		return SuperService.DAO.count(DaoContext.getClass(entity), property, value);
	}

	// /**
	// * 获得指定属性下的所有实体 包含指定属性
	// * @param entity 类名称
	// * @param property 属性名
	// * @param values 属性值
	// * @return 下级所有分类列表
	// */
	// public List<? extends Object> next(String entity, String property, Object value) {
	// return SuperService.DAO.next(DaoContext.getClass(entity), property, value);
	// }
	//
	// /**
	// * 获得指定属性上的所有实体 包含指定属性
	// * @param entity 类名称
	// * @param property 属性名
	// * @param pk 主键
	// * @return 上级所有分类列表
	// */
	// public List<Entity> prev(String entity, String property, Serializable pk) {
	// return SuperService.DAO.prev(DaoContext.getClass(entity), property, pk);
	// }

	/**
	 * 查询属性名等值的实体列表
	 * @param entityClass 实体类
	 * @param orders 排序参数
	 * @param maxResults 一共查回多少条
	 * @return 数据列表
	 */
	public List<Entity> order(String entity, Map<String, Object> orders, int firstResult,
			int maxResults) {
		return SuperService.DAO.order(DaoContext.getClass(entity), orders, firstResult, maxResults);
	}
}
