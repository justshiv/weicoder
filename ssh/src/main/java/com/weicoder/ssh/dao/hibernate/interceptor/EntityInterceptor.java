package com.weicoder.ssh.dao.hibernate.interceptor;

import org.hibernate.EmptyInterceptor;
import com.weicoder.ssh.entity.EntityShards;
import com.weicoder.ssh.entity.EntityTime;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.DateUtil;

/**
 * hibernate 实体拦截器
 * @author WD 
 * @version 1.0  
 */
public class EntityInterceptor extends EmptyInterceptor {
	private static final long serialVersionUID = 2314652107885146870L;

	@Override
	public String onPrepareStatement(String sql) {
		return super.onPrepareStatement(sql);
	}

	@Override
	public String getEntityName(Object entity) {
		// 是否切片实体
		if (entity instanceof EntityShards) {
			// 获得实体名
			String name = entity.getClass().getSimpleName();
			// 按时间切片
			if (entity instanceof EntityTime) {
				// 获得时间
				int time = Conversion.toInt(((EntityTime) entity).getTime());
				// 时间大于0
				if (time > 0) {
					// 按月份切片
					return name + StringConstants.UNDERLINE + DateUtil.toString(time, DateConstants.FORMAT_YYYYMM);
				}
			}
		}
		// 返回实体名
		return super.getEntityName(entity);
	}
}
