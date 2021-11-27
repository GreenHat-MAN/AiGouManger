package com.bigdata.store.service;

import java.sql.SQLException;

import com.bigdata.store.domain.User;

public interface UserService {
	void userRegist(User user) throws SQLException;
	public User login(String username, String password) throws Exception;
	public User active(String code) throws Exception;
}