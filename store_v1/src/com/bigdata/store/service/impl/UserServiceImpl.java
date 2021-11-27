package com.bigdata.store.service.impl;

import java.sql.SQLException;

import com.bigdata.store.constant.Constant;
import com.bigdata.store.dao.UserDao;
import com.bigdata.store.dao.impl.UserDaoImpl;
import com.bigdata.store.domain.User;
import com.bigdata.store.service.UserService;

public class UserServiceImpl implements UserService {
	UserDao userDao = new UserDaoImpl();

	@Override
	public void userRegist(User user) throws SQLException {
		// 实现注册功能
		userDao.userRegist(user);

	}

	@Override
	public User login(String username, String password) throws Exception {
		  return userDao.getByUsernameAndPwd(username,password);
	}

	@Override
	public User active(String code) throws Exception {
		 User user = userDao.getByCode(code);
         //1.通过激活码没有找到 用户
         if(user == null){
             return null;
         }
         //2.若获取到了 修改用户
         user.setState(Constant.USER_IS_ACTIVE);
         user.setCode(null);
        userDao.update(user);
         return user;
	}
}