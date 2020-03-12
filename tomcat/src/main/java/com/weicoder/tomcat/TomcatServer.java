package com.weicoder.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.startup.Tomcat;

import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.log.Logs;
import com.weicoder.tomcat.params.TomcatParams;
import com.weicoder.web.action.Actions;
import com.weicoder.web.servlet.BasicServlet;

/**
 * tomcat server
 * 
 * @author wudi
 */
public final class TomcatServer {
	// tomcat服务器
	private static Tomcat tomcat = null;

	/**
	 * 启动tomcat
	 */
	public static void start() {
		start(TomcatParams.PORT, TomcatParams.PATH);
	}

	/**
	 * 关闭tomcat
	 */
	public static void stop() {
		if (tomcat != null) {
			try {
				tomcat.stop();
			} catch (LifecycleException e) {
				Logs.error(e);
			}
		}
	}

	/**
	 * 启动tomcat
	 * 
	 * @param port 端口
	 * @param path 路径
	 */
	public static void start(int port, String path) {
		// 如果已经启动 返回
		if (tomcat != null) {
			Logs.warn("tomcat server port={} path={} already start");
			return;
		}
		try {
			// 声明tomcat 设置参数
			tomcat = new Tomcat();
			tomcat.setBaseDir(SystemConstants.BASE_DIR);
			tomcat.setPort(port);
			// 声明Connector 设置参数
			Connector connector = new Connector(TomcatParams.PROTOCOL);
			connector.setPort(port);
			connector.addLifecycleListener(new AprLifecycleListener());
			tomcat.setConnector(connector);

			// 添加路径与servlet
			tomcat.addWebapp(path, SystemConstants.BASE_DIR).setReloadable(false);
			tomcat.addServlet(path, "basic", new BasicServlet()).addMapping("/*");
			tomcat.enableNaming();

			// 初始化
			Actions.init();
			tomcat.init();

			// 启动
			tomcat.start();
			tomcat.getServer().await();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	private TomcatServer() {
	}
}