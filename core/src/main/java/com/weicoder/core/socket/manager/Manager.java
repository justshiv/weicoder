package com.weicoder.core.socket.manager;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.concurrent.ExecutorUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.core.params.SocketParams;
import com.weicoder.core.socket.Session;
import com.weicoder.core.socket.empty.SessionEmpty;

/**
 * Session管理类
 * 
 * @author WD
 * @version 1.0
 */
public final class Manager {
	// 保存注册的Session
	private Map<String, Map<Long, Session>> registers;
	// 保存Session对应所在列表
	private Map<Integer, String> keys;
	// 保存Session对应注册ID
	private Map<Integer, Long> ids;

	/**
	 * 构造方法
	 */
	public Manager() {
		// 初始化
		registers = Maps.newConcurrentMap();
		keys = Maps.newConcurrentMap();
		ids = Maps.newConcurrentMap();
		// 注册列表Map
		for (String register : SocketParams.REGISTERS) {
			registers.put(register, new ConcurrentHashMap<Long, Session>());
		}
	}

	/**
	 * 注册到列表
	 * 
	 * @param key     注册键
	 * @param session Socket Session
	 * @return true 注册成功 false 注册失败
	 */
	public boolean register(String key, Session session) {
		return register(key, session.id(), session);
	}

	/**
	 * 注册到列表
	 * 
	 * @param key     注册键
	 * @param id      注册ID
	 * @param session Socket Session
	 * @return true 注册成功 false 注册失败
	 */
	public boolean register(String key, long id, Session session) {
		// 获得注册列表
		Map<Long, Session> register = registers.get(key);
		// 列表为null
		if (register == null) {
			return false;
		}
		// 添加到列表
		register.put(id, session);
		// session id
		int sid = session.id();
		// 记录索引
		keys.put(sid, key);
		// 记录ID
		ids.put(sid, id);
		// 返回成功
		return true;
	}

	/**
	 * 从列表删除Session
	 * 
	 * @param key 注册键
	 * @param id  注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public Session remove(String key, long id) {
		// 获得注册列表
		Map<Long, Session> register = registers.get(key);
		// 列表为null
		if (register == null) {
			return SessionEmpty.EMPTY;
		}
		// 删除列表
		return register.remove(id);
	}

	/**
	 * 从列表删除Session 根据ID删除 循环所有服务器列表删除
	 * 
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public Session remove(int id) {
		// 如果存在
		if (keys.containsKey(id) && ids.containsKey(id)) {
			// 删除Session
			return remove(keys.get(id), ids.get(id));
		} else {
			return SessionEmpty.EMPTY;
		}
	}

	/**
	 * 从列表删除Session 根据Session删除 循环所有服务器列表删除
	 * 
	 * @param Session session
	 * @return true 删除成功 false 删除成功
	 */
	public Session remove(Session session) {
		return remove(session.id());
	}

	/**
	 * 根据注册ID获得Session
	 * 
	 * @param key 注册键
	 * @param id  注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public Session get(String key, long id) {
		// 获得Session
		Session session = registers.get(key).get(id);
		// 如果Session为空 返回空实现
		return session == null ? SessionEmpty.EMPTY : session;
	}

	/**
	 * 根据SessionID获得Session
	 * 
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public Session get(int id) {
		return get(keys.get(id), ids.get(id));
	}

	/**
	 * 验证Session是否注册
	 * 
	 * @param session Session
	 * @return true 以注册 false 未注册
	 */
	public boolean exists(Session session) {
		return ids.containsKey(session.id());
	}

	/**
	 * 根据键获得注册Session列表
	 * 
	 * @param key 注册键
	 * @return Session列表
	 */
	public List<Session> sessions(String key) {
		return Lists.newList(registers.get(key).values());
	}

	/**
	 * 获得全部注册Session列表
	 * 
	 * @return Session列表
	 */
	public List<Session> sessions() {
		// 声明Session列表
		List<Session> sessions = Lists.newList();
		// 循环获得全部Session
		for (String key : keys()) {
			// 添加到列表
			sessions.addAll(sessions(key));
		}
		// 返回列表
		return sessions;
	}

	/**
	 * 获得所有Key
	 * 
	 * @return key列表
	 */
	public Set<String> keys() {
		return registers.keySet();
	}

	/**
	 * 获得所有注册Session数量
	 * 
	 * @return 数量
	 */
	public int size() {
		// 声明总数
		int sum = 0;
		// 循环计算数量
		for (String key : keys()) {
			// 添加到列表
			sum += size(key);
		}
		// 返回总数
		return sum;
	}

	/**
	 * 根据Key获得注册Session数量
	 * 
	 * @param key 注册键
	 * @return 数量
	 */
	public int size(String key) {
		return Maps.size(registers.get(key));
	}

	/**
	 * 广播数据 发送给管理器下所有的session
	 * 
	 * @param id      指令
	 * @param message 消息
	 */
	public void broad(short id, Object message) {
		// 声明Sesson列表
		List<Session> sessions = Lists.newList();
		// 获得相应的Session
		for (Map<Long, Session> map : registers.values()) {
			sessions.addAll(map.values());
		}
		// 广播
		broad(sessions, id, message);
	}

	/**
	 * 广播数据 发送给管理器指定KEY下所有的session
	 * 
	 * @param key     注册KEY
	 * @param id      指令
	 * @param message 消息
	 */
	public void broad(String key, short id, Object message) {
		broad(sessions(key), id, message);
	}

	/**
	 * 广播数据 发送给管理器指定KEY下所有的session
	 * 
	 * @param key     注册KEY
	 * @param ids     注册的ID
	 * @param id      指令
	 * @param message 消息
	 */
	public void broad(String key, List<Long> ids, short id, Object message) {
		// 声明Sesson列表
		List<Session> sessions = Lists.newList();
		// 日志
		long curr = System.currentTimeMillis();
		Logs.info("manager broad start key=" + key + ";ids=" + ids.size() + ";id=" + id + ";time=" + DateUtil.getTheDate());
		// 获得Session Map
		Map<Long, Session> map = registers.get(key);
		// 获得相应的Session
		for (Long sid : ids) {
			sessions.add(map.get(sid));
		}
		// for (Map.Entry<Integer, Session> e : registers.get(key).entrySet()) {
		// // ID存在
		// if (ids.contains(e.getKey())) {
		// sessions.add(e.getValue());
		// }
		// }
		// 日志
		Logs.info("manager broad end key=" + key + ";ids=" + ids.size() + ";id=" + id + ";time=" + (System.currentTimeMillis() - curr));
		// 广播
		broad(sessions, id, message);
	}

	/**
	 * 广播
	 * 
	 * @param sessions
	 * @param id
	 * @param message
	 */
	private void broad(List<Session> sessions, short id, Object message) {
		// 列表为空
		if (EmptyUtil.isEmpty(sessions)) {
			return;
		}
		// 获得列表长度
		int size = sessions.size();
		// 如果线程池乘2倍
		int broad = SocketParams.BROAD;
		// 包装数据
		final byte[] data = sessions.get(0).send(id, message);
		// 日志
		Logs.info("manager broad num=" + size + ";broad=" + broad + ";id=" + id + ";data=" + data.length + ";time=" + DateUtil.getTheDate());
		// 如果要广播的Session小于 分组广播 直接广播数据
		if (broad == 0 || size <= broad) {
			broad(Lists.subList(sessions, 1, size), data);
		} else {
			// 循环分组广播
			for (int i = 1; i < size;) {
				// 获得执行Session列表
				final List<Session> list = Lists.subList(sessions, i, i += broad);
				// 线程执行
				ExecutorUtil.execute(() -> broad(list, data));
			}
		}

	}

	/**
	 * 广播
	 * 
	 * @param sessions
	 * @param data
	 */
	private void broad(List<Session> sessions, byte[] data) {
		// 日志
		long curr = System.currentTimeMillis();
		Logs.debug("manager pool broad start size=" + sessions.size() + ";time=" + DateUtil.getTheDate());
		// 广播消息
		for (Session session : sessions) {
			if (session != null) {
				session.write(data);
			}
		}
		Logs.debug("manager pool broad end size=" + sessions.size() + ";time=" + (System.currentTimeMillis() - curr));
	}
}
