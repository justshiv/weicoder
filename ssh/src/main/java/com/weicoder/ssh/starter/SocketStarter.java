package com.weicoder.ssh.starter;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.params.SocketParams;
import com.weicoder.core.socket.Closed;
import com.weicoder.core.socket.Handler;
import com.weicoder.core.socket.Socket;
import com.weicoder.core.socket.Sockets;

/**
 * Socket 相关类
 * @author WD 
 * @version 1.0 
 */
@Component
public final class SocketStarter {
	//ApplicationContext
	@Resource
	private ApplicationContext context;

	/**
	 * 初始化Mina
	 */
	@SuppressWarnings("rawtypes")
	@PostConstruct
	public void init() {
		// 获得全部Handler
		Collection<Handler> handlers = context.getBeansOfType(Handler.class).values();
		// 获得全部Closed
		Collection<Closed> closeds = context.getBeansOfType(Closed.class).values();
		// 判断任务不为空
		if (SocketParams.SPRING) {
			// 循环数组
			for (String name : SocketParams.NAMES) {
				init(name, handlers, closeds);
			}
			Sockets.start();
		}
	}

	/**
	 * 根据名称设置
	 * @param name 名
	 */
	@SuppressWarnings("rawtypes")
	private void init(String name, Collection<Handler> handlers, Collection<Closed> closeds) {
		// Socket
		Socket socket = Sockets.init(name);
		// 获得指定的包
		String pack = SocketParams.getPackage(name);
		// 设置Handler
		for (Handler<?> handler : handlers) {
			// 在指定包内
			if (EmptyUtil.isEmpty(pack) || handler.getClass().getPackage().getName().indexOf(pack) > -1) {
				socket.addHandler(handler);
			}
		}
		// 设置Closed
		String close = SocketParams.getClosed(name);
		for (Closed closed : closeds) {
			// 在指定包内
			if (closed.getClass().getName().equals(close)) {
				socket.closed(closed);
				break;
			}
		}
	}

	private SocketStarter() {}
}