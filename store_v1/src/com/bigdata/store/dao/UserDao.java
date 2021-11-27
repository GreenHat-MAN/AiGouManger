package com.bigdata.store.dao;

import java.sql.SQLException;

import com.bigdata.store.domain.User;

public interface UserDao {

	void userRegist(User user) throws SQLException;
	
	public User getByUsernameAndPwd(String username, String password) throws Exception;
	User getByCode(String code) throws Exception;
	 void update(User user) throws Exception;
}