package com.ssafy.enjoytrips.service;

import java.security.NoSuchAlgorithmException;
import com.ssafy.enjoytrips.model.dao.AuthDao;
import com.ssafy.enjoytrips.model.dao.UserDao;
import com.ssafy.enjoytrips.model.dto.Token;
import com.ssafy.enjoytrips.model.dto.User;
import com.ssafy.utils.PasswordHashingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthDao authDao;
    private final UserDao userDao;
    private final PasswordHashingUtil passwordHashingUtil;

	public User login(User user) {
		Token userGet = authDao.login(user);
        User users = userDao.select(userGet.getId());
		if (users != null && passwordHashingUtil.get_SHA_256_SecurePassword(user.getPw(), users.getSalt())
				.equals(users.getPw())) {
			return users;
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
		return authDao.regist(user);
	}

    @Override
    public Token refreshTokens(String accessToken, String refreshToken, String id) {
        return authDao.refreshTokens(accessToken, refreshToken, id);
    }

    @Override
    public int isDuplicateId(String id) {
        return authDao.isDuplicateId(id);
    }
}
