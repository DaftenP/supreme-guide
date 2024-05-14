package com.ssafy.enjoytrips.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.ssafy.enjoytrips.model.dao.UserDao;
import com.ssafy.enjoytrips.model.dto.User;
import com.ssafy.utils.PasswordHashingUtil;

@Service
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private PasswordHashingUtil passwordHashingUtil;

	public UserServiceImpl(UserDao userDao, PasswordHashingUtil passwordHashingUtil) {
		this.userDao = userDao;
		this.passwordHashingUtil = passwordHashingUtil;
	}

	public User login(User user) {
		User userGet = userDao.login(user);
		if (userGet != null && passwordHashingUtil.get_SHA_256_SecurePassword(user.getPw(), userGet.getSalt())
				.equals(userGet.getPw())) {
			return userGet;
		}
		return null;
	}

	public int regist(User user) {
		try {
			String[] res = passwordHashingUtil.getHashedPassword(user.getPw());
			user.setPw(res[0]);
			user.setSalt(res[1]);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return userDao.regist(user);
	}

	public int modify(User user) {
		try {
			String[] res = passwordHashingUtil.getHashedPassword(user.getPw());
			user.setPw(res[0]);
			user.setSalt(res[1]);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return userDao.modify(user);
	}

	public int delete(User user) {
		return userDao.delete(user);
	}

	@Override
	public User select(String id) {
		return userDao.select(id);
	}

}
