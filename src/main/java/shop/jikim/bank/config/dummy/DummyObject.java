package shop.jikim.bank.config.dummy;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserEnum;

public class DummyObject {

	protected User newUser(String username, String fullname) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encPwd = passwordEncoder.encode("1234");
		return User.builder()
			.username(username)
			.password(encPwd)
			.email(username + "@naver.com")
			.fullname(fullname)
			.role(UserEnum.CUSTOMER)
			.build();
	}

	protected User newMockUser(Long id, String username, String fullname) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encPwd = passwordEncoder.encode("1234");
		return User.builder()
			.id(id)
			.username(username)
			.password(encPwd)
			.email(username + "@naver.com")
			.fullname(fullname)
			.role(UserEnum.CUSTOMER)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}
}
