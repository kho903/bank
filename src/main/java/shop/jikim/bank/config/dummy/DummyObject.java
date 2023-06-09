package shop.jikim.bank.config.dummy;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.jikim.bank.domain.account.Account;
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

	protected Account newAccount(Long number, User user) {
		return Account.builder()
			.number(number)
			.password(1234L)
			.balance(1000L)
			.user(user)
			.build();
	}

	protected Account newMockAccount(Long id, Long number, Long balance, User user) {
		return Account.builder()
			.id(id)
			.number(number)
			.password(1234L)
			.balance(balance)
			.user(user)
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
	}
}
