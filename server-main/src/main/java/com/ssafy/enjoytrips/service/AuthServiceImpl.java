package com.ssafy.enjoytrips.service;

import java.security.NoSuchAlgorithmException;
import com.ssafy.enjoytrips.model.dao.AuthDao;
import com.ssafy.enjoytrips.model.dao.UserDao;
import com.ssafy.enjoytrips.model.dto.Token;
import com.ssafy.enjoytrips.model.dto.User;
import com.ssafy.utils.PasswordHashingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService{

    private final AuthDao authDao;
    private final UserDao userDao;
    private final PasswordHashingUtil passwordHashingUtil;

	public User login(User user) {
		log.debug("Attempting to login user: {}", user.getId());
		User userFromDb = authDao.login(user);
		System.out.println("userFromdb------" + userFromDb.getId());
		if (userFromDb != null) {
			log.debug("User found in database: {}", userFromDb.getId());
			String hashedPassword = passwordHashingUtil.get_SHA_256_SecurePassword(user.getPw(), userFromDb.getSalt());
			log.debug("Comparing passwords: {} vs {}", hashedPassword, userFromDb.getPw());
			if (hashedPassword.equals(userFromDb.getPw())) {
				return userFromDb;
			} else {
				log.debug("Password mismatch");
			}
		} else {
			log.debug("User not found in database");
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
	public void saveTokens(String accessToken, String refreshToken, String userId) throws Exception {
		Token token = new Token(accessToken, refreshToken, userId);
		authDao.saveTokens(token);
	}

	@Override
    public Token refreshTokens(String accessToken, String refreshToken, String id, long refreshTokenExpiredTime) {
		refreshTokenExpiredTime = System.currentTimeMillis() + refreshTokenExpiredTime;

		return authDao.refreshTokens(accessToken, refreshToken, id, refreshTokenExpiredTime);
    }

    @Override
    public int isDuplicateId(String id) {
        return authDao.isDuplicateId(id);
    }
}
