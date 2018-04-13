package com.liangweibang.o2o.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataSourceHolder {

	private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
	private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
	public static final String DB_MASTER = "master";
	public static final String DB_SLAVE = "slave";
	
	/**
	 * 获取线程的 DbType
	 * @return
	 */
	public static String getDbType() {
		String db = contextHolder.get();
		if (db == null) {
			db = DB_MASTER;
		}
		return db;
	}
	
	/**
	 * 设置线程的 DbType
	 * @param type
	 */
	public static void setDbType(String type) {
		logger.debug("所使用的数据源为：" + type);
		contextHolder.set(type);
	}
	
	/**
	 * 清理连接类型
	 */
	public static void clearDbType() {
		contextHolder.remove();
	}
}
