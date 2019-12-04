package com.weicoder.core.nosql.hbase.factory;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory; 
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.log.Logs;
import com.weicoder.core.factory.FactoryKey;
import com.weicoder.core.nosql.hbase.HBase;
import com.weicoder.core.nosql.hbase.HBaseDao;

/**
 * HBase实现
 * @author WD 
 * @version 1.0 
 */
final class HBaseImpl extends FactoryKey<String, HBaseDao> implements HBase {
	// 配置
	private Configuration	cfg;
	// HBase管理
	private Admin			admin;

	/**
	 * 构造方法
	 * @param host 主机
	 * @param port 端口
	 */
	public HBaseImpl(String host, int port) {
		try {
			// 创建配置
			cfg = HBaseConfiguration.create();
			// 设置主机
			cfg.set("hbase.master", host);
			cfg.set("hbase.zookeeper.quorum", host);
			// 设置端口
			cfg.set("hbase.zookeeper.property.clientPort", Conversion.toString(port));
			cfg.set("hbase.master.port", Conversion.toString(port));
			// 实例化管理
			admin = ConnectionFactory.createConnection(cfg).getAdmin();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 获得Dao
	 * @param tableName 表名
	 * @return HBaseDao
	 */
	public HBaseDao getDao(String tableName) {
		return getInstance(tableName);
	}

	/**
	 * 创建表
	 * @param tableName 表名
	 * @return HBaseDao
	 */
	public HBaseDao createTable(String tableName, String... cols) {
		try {
			// 表不存在
			TableName tn = TableName.valueOf(tableName);
			if (!admin.tableExists(tn)) {
				// 声明表对象
				HTableDescriptor tableDesc = new HTableDescriptor(tn);
				// 添加列
				for (int i = 0; i < cols.length; i++) {
					tableDesc.addFamily(new HColumnDescriptor(cols[i]));
				}
				// 创建表
				admin.createTable(tableDesc);
			}
		} catch (Exception e) {
			Logs.warn(e);
		}
		// 返回表Dao
		return getDao(tableName);
	}

	/**
	 * 删除表
	 * @param tableName 表名
	 */
	public void deleteTable(String tableName) {
		try {
			// 生成表对象
			TableName name = TableName.valueOf(tableName);
			// 表存在
			if (admin.tableExists(name)) {
				// 使表失效
				admin.disableTable(name);
				// 删除表
				admin.deleteTable(name);
			}
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	@Override
	public boolean append(String key, Object value) {
		return false;
	}

	/**
	 * 实例化一个新对象
	 */
	public HBaseDao newInstance(String tableName) {
		return new HBaseDaoImpl(cfg, tableName);
	}

	/**
	 * 实例化一个新对象
	 */
	public HBaseDao newInstance() {
		return newInstance(StringConstants.EMPTY);
	}

	@Override
	public boolean compress(String key, Object value) {
		return false;
	}

	@Override
	public byte[] extract(String key) {
		return null;
	}

	@Override
	public boolean set(String key, Object value) {
		return false;
	}

	@Override
	public Object get(String key) {
		return null;
	}

	@Override
	public void remove(String... key) {}

	@Override
	public boolean exists(String key) {
		return false;
	}

	@Override
	public void clear() {}

	@Override
	public void close() {}

	@Override
	public List<byte[]> extract(String... keys) {
		return null;
	}

	@Override
	public Object[] get(String... keys) {
		return null;
	}
}
