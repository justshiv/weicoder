package com.weicoder.dao.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * JDBC实现 数据库操作接口
 * 使用DataBaseFactory获得实例,调用方法执行数据库操作
 * @author WD 
 */
public interface DataBase {
	/**
	 * 完成数据库的增删改操作，要求传入的sql语句必须为insert,update或delete 
	 * 以List包含数组表示的多条条件 可使用单条sql批量插入
	 * @param sql 传入的sql语句
	 * @param parame SQL语句的参数 List包含数组 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	int execute(String sql, List<Object> parame);

	/**
	 * 完成数据库的增删改操作，要求传入的sql语句必须为insert,update或delete
	 * @param sql 传入的sql语句
	 * @param parame SQL语句的参数 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	int execute(String sql, Object... parame);

	/**
	 * 完成数据库的增删改操作，要求传入的sql语句必须为insert,update或delete
	 * 以二维数组表示的多条条件 可使用单条sql批量插入
	 * @param sql 传入的sql语句
	 * @param parame SQL语句的参数 二维数组 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	int execute(String sql, Object[][] parame);

	/**
	 * 传入一组sql语句，完成数据库的增删改操作 要求传入的sqlArr数组中的SQL语句必须为insert,update或delete 
	 * 传入的parame为多个条件 每个一唯数组给一条sql附值
	 * @param sql 传入的sql语句数组 可执行多条语句
	 * @param parame SQL语句的参数 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	int execute(String[] sql, Object[]... parame);

	/**
	 * 完成数据库的增删改操作，要求传入的sql语句必须为insert,update或delete 
	 * 以一个List包含二维数组 可使用多条sql批量插入
	 * @param sql 传入的sql语句 可执行多条语句
	 * @param parame SQL语句的参数 List包含二维数组 按顺序赋值给sql中的?
	 * @return 更新的行数
	 */
	int execute(String[] sql, List<Object[][]> parame);

	/**
	 * 执行指定过程 用于非查询
	 * @param call 执行过程的SQL语句
	 * @param parame 参数
	 * @return 返回影响行数
	 */
	int executeCall(String call, Object... parame);

	/**
	 * 执行指定过程 用于查询
	 * @param call 执行过程的SQL语句
	 * @param parame 参数
	 * @return 返回结果集
	 */
	List<Map<String, Object>> queryCall(String call, Object... parame);

	/**
	 * 查询结果
	 * @param sql 查询语句
	 * @param parame SQL语句的参数
	 * @param c 返回的实体类Class
	 * @param <E> 泛型
	 * @return List
	 */
	<E> List<E> query(String sql, Class<E> c, Object... parame);

	/**
	 * 返回一行一列查询结果,如果SQL语句查询出多条，只返回第一条记录
	 * @param sql 查询语句
	 * @param parame SQL语句的参数
	 * @return String 列值
	 */
	Object querySnglRowSnglCol(String sql, Object... parame);

	/**
	 * 返回一行多列查询结果,如果SQL语句查询出多条，只返回第一条记录
	 * @param sql 查询语句
	 * @param parame SQL语句的参数
	 * @return Map 返回的数据集合 key列名(列名都小写) value(值)
	 */
	Map<String, Object> querySnglRowMultiCol(String sql, Object... parame);

	/**
	 * 返回多行一列查询结果
	 * @param sql 查询语句
	 * @param parame SQL语句的参数
	 * @return List 值
	 */
	List<Object> queryMultiRowSnglCol(String sql, Object... parame);

	/**
	 * 返回多行多列查询结果
	 * @param sql 查询语句
	 * @param parame SQL语句的参数
	 * @return List 返回的数据集合 Map key列名(列名都小写) value(值)
	 */
	List<Map<String, Object>> queryMultiRowMultiCol(String sql, Object... parame);

	/**
	 * 获得Connection对象
	 * @return Connection
	 */
	Connection getConnection();
}