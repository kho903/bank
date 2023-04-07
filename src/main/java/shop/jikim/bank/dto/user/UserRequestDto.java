package shop.jikim.bank.dto.user;

import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserEnum;

public class UserRequestDto {


	@Getter
	@Setter
	public static class JoinRequestDto {
		// 유효성 검사
		@NotEmpty
		private String username;

		@NotEmpty
		private String password;

		@NotEmpty
		private String email;

		@NotEmpty
		private String fullname;

		public User toEntity(BCryptPasswordEncoder passwordEncoder) {
			return User.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.email(email)
				.fullname(fullname)
				.role(UserEnum.CUSTOMER)
				.build();
		}
	}
}
