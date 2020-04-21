package com.weicoder.core.email;

import com.weicoder.core.email.factory.EmailFactory;

/**
 * Email 处理引擎
 * @author WD 
 * @version 1.0 
 */
public final class EmailEngine {
	// Email发送器
	private final static Email EMAIL = EmailFactory.getEmail();

	/**
	 * 发送简单文本邮件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public static void send(String to, String subject, String msg) {
		EMAIL.send(to, subject, msg);
	}

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public static void send(String to, String subject, String msg, String attach) {
		EMAIL.send(to, subject, msg, attach);
	}

	/**
	 * 发送Email 支持HTML格式
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 */
	public static void send(String to, String subject, String msg, boolean flag) {
		EMAIL.send(to, subject, msg, flag);
	}

	/**
	 * 发送Email 支持HTML格式 带附件
	 * @param to 发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param flag 是否支持HTML true支持,false不支持
	 * @param attach 附件
	 */
	public static void send(String to, String subject, String msg, String attach, boolean flag) {
		EMAIL.send(to, subject, msg, attach, flag);
	}

	/**
	 * 发送简单文本邮件
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 */
	public static void send(String[] to, String subject, String msg) {
		EMAIL.send(to, subject, msg);
	}

	/**
	 * 发送简单文本邮件 带附件
	 * @param to 数组发送到
	 * @param subject 标题
	 * @param msg 内容
	 * @param attach 附件
	 */
	public static void send(String[] to, String subject, String msg, String attach) {
		EMAIL.send(to, subject, msg, attach);
	}

	private EmailEngine() {}
}
