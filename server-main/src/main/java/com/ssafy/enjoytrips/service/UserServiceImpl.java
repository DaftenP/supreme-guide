package com.ssafy.enjoytrips.service;

import java.security.NoSuchAlgorithmException;

import com.ssafy.enjoytrips.model.dto.Token;
import com.ssafy.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ssafy.enjoytrips.model.dao.UserDao;
import com.ssafy.enjoytrips.model.dto.User;
import com.ssafy.utils.PasswordHashingUtil;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final PasswordHashingUtil passwordHashingUtil;
	private final TokenProvider tokenProvider;

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

	public int delete(String id) {
		return userDao.delete(id);
	}

	@Override
	public User select(String id) {
		return userDao.select(id);
	}

}
