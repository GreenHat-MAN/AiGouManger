package com.bigdata.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.bigdata.store.dao.UserDao;
import com.bigdata.store.domain.User;
import com.bigdata.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void userRegist(User user) throws SQLException {
		String sql = "INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
		qr.update(sql, params);

	}

	@Override
	public User getByUsernameAndPwd(String username, String password) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from user where username = ? and password = ? limit 1";
        return qr.query(sql, new BeanHandler<>(User.class), username,password);
	}

	@Override
	public User getByCode(String code) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from user where code = ? limit 1";
        return qr.query(sql, new BeanHandler<>(User.class), code);
	}

	@Override
	public void update(User user) throws Exception {
		//更新状态
		 QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		 String sql="update user set password = ?,sex = ?,state = ?,code = ? where uid = ?";
	     qr.update(sql, user.getPassword(),user.getSex(),user.getState(),user.getCode(),user.getUid());
	}
}