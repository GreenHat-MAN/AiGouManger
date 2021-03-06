package com.bigdata.store.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	
	//使用命名配置
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	/**
	    *    获取连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {	
		return dataSource.getConnection();
		
	}

	/**
	    *   获得数据源（连接池）
	 * @return
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}

	

}
