package com.ssafy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class PasswordHashingUtil {

	public PasswordHashingUtil() {
	}

	/**
	 * 회원가입 또는 비밀번호 수정 시 임의의 솔트를 생성하고, 해싱된 비밀번호와 솔트를 반환하는 메소드
	 * 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String[] getHashedPassword(String password) throws NoSuchAlgorithmException {
		String salt = getSalt(); // 임의 솔트 생성
		return new String[] { get_SHA_256_SecurePassword(password, salt), salt };
	}

	/**
	 * 입력 비밀번호를 해싱하여 반환하는 메소드
	 * 
	 * @param passwordToHash
	 * @param salt
	 * @return 해싱된 비밀번호
	 */
	public String get_SHA_256_SecurePassword(String passwordToHash, String salt) {
		String generatedPassword = null;
		byte[] decodedBytes = Base64.getDecoder().decode(salt);
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // SHA-256 암호화 모듈 호출
			md.update(decodedBytes); // 암호화 모듈에 솔트 추가
			// --- 비밀번호 해싱 ---
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	/**
	 * 임의의 솔트를 생성하는 메소드
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstanceStrong();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt); // byte 배열의 솔트를 Base64 방식으로 인코딩하여 문자열로 변환
	}
}
