package com.weicoder.ssh.starter;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.weicoder.ssh.params.QuartzParams;
import com.weicoder.ssh.quartz.Job;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.log.Logs; 

/**
 * quartz启动器
 * @author WD 
 * @version 1.0  
 */
@Component
final class QuartzStarter {
	//ApplicationContext
	@Resource
	private ApplicationContext context;

	/**
	 * 初始化
	 */
	@PostConstruct
	protected void init() {
		// 定时任务
		if (QuartzParams.SPRING) {
			// 声明定时对象
			List<Trigger> triggers = Lists.newList();
			// 循环设置
			for (Job job : context.getBeansOfType(Job.class).values()) {
				// 设置任务
				for (Map.Entry<String, String> e : job.getTriggers().entrySet()) {
					// 声明方法执行bean
					MethodInvokingJobDetailFactoryBean method = new MethodInvokingJobDetailFactoryBean();
					// 设置任务对象
					method.setTargetObject(job);
					// 设置不可以并发
					method.setConcurrent(false);
					// // 设置执行方法
					method.setTargetMethod(e.getKey());
					// 设置group
					method.setGroup(job.getClass().getSimpleName());
					// 设置beanName
					method.setBeanName(e.getKey());
					try {
						// 执行初始化
						method.afterPropertiesSet();
					} catch (Exception ex) {}
					// 执行执行时间
					for (String trigger : e.getValue().split(StringConstants.COMMA)) {
						// 执行时间对象
						CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
						// 设置group
						cron.setGroup(method.getTargetObject().getClass().getSimpleName());
						// 设置beanName
						cron.setBeanName(method.getTargetMethod());
						// 设置任务对象
						cron.setJobDetail(method.getObject());
						// 设置时间
						cron.setCronExpression(trigger);
						// 执行初始化
						try {
							cron.afterPropertiesSet();
						} catch (ParseException ex) {
							Logs.error(ex);
						}
						// 添加到定时列表中
						triggers.add(cron.getObject());
					}
				}
			}
			// 定时任务不为空
			if (!EmptyUtil.isEmpty(triggers)) {
				// 声明执行定时方法工厂
				SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
				// 设置执行时间
				scheduler.setTriggers(Lists.toArray(triggers));
				// scheduler.init();
				try {
					// 执行初始化
					scheduler.afterPropertiesSet();
				} catch (Exception e) {}
				// 执行
				scheduler.start();
			}
		}
	}
}
