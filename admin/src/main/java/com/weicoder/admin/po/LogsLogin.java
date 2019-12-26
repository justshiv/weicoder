package com.weicoder.admin.po;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.weicoder.ssh.entity.EntityIp;
import com.weicoder.ssh.entity.EntityUserId;
import com.weicoder.ssh.entity.base.BaseEntityIdTime;

/**
 * 登录日志实体
 * 
 * @author  WD
 * @since   JDK7
 * @version 1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class LogsLogin extends BaseEntityIdTime implements EntityIp, EntityUserId {
	// 登录IP
	private String ip;
	// 用户ID
	private Long userId;
	// 状态
	private Integer state;
	// 名称
	private String name;
	// 时间
	private Integer time;
}
