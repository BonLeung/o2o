package com.liangweibang.o2o.dao.split;

import java.util.Locale;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}), 
	@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {
	
	private static final String REGEXP = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";
	private static Logger Logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
		String lookupKey = DynamicDataSourceHolder.DB_MASTER;
		Object[] objects = invocation.getArgs();
		MappedStatement mappedStatement = (MappedStatement) objects[0];
		if (!synchronizationActive) {
			mappedStatement = (MappedStatement) objects[0];
			// 读方法
			if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
				// selectKey 为自增 id 查询主键（SELECT LAST_INSERT_UD()）方法，使用主库
				if (mappedStatement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
					lookupKey = DynamicDataSourceHolder.DB_MASTER;
				} else {
					BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(objects[1]);
					String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
					if (sql.matches(REGEXP)) {
						lookupKey = DynamicDataSourceHolder.DB_MASTER;
					} else {
						lookupKey = DynamicDataSourceHolder.DB_SLAVE;
					}
				}
			} else {
				lookupKey = DynamicDataSourceHolder.DB_MASTER;
			}
		}
		Logger.debug("设置方法[{}] use [{}] Strategy, SqlCommanType [{}]...", mappedStatement.getId(), lookupKey, mappedStatement.getSqlCommandType().name());
		DynamicDataSourceHolder.setDbType(lookupKey);
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub
		
	}

}
