package com.weicoder.ssh.socket;
  
/**
 * Socket接口
 * @author WD 
 * @version 1.0 
 */
public interface Socket extends AutoCloseable {
	/**
	 * 添加要处理的Handler
	 * @param handler
	 */
	void addHandler(Handler<?> handler);

	/**
	 * 服务器名
	 */
	String name();

	/**
	 * 设置连接管理处理器
	 * @param connected 连接处理器
	 */
	void connected(Connected connected);

	/**
	 * 添加关闭处理器
	 * @param closed 关闭处理器
	 */
	void closed(Closed closed);
}
