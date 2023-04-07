package shop.jikim.bank.dto.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserEnum;

public class UserRequestDto {

	@Getter
	@Setter
	public static class LoginRequestDto {
		private String username;
		private String password;
	}

	@Getter
	@Setter
	public static class JoinRequestDto {
		// 유효성 검사
		// 영문, 숫자는 되고, 길이 최소 2~20자 이내
		@NotEmpty
		@Pattern(regexp = "[a-zA-Z0-9]{2,20}", message = "영문/숫자 2~20자 이내로 작성해주세요.")
		private String username;

		// 길이 4~20
		@NotEmpty
		@Size(min = 4, max = 20)
		private String password;

		// 이메일 형식
		@NotEmpty
		@Pattern(regexp = "[a-zA-Z0-9]{2,10}@[a-zA-Z]{2,6}\\.[a-zA-Z]{2,3}", message = "이메일 형식으로 작성해주세요.")
		private String email;

		// 영어, 한글, 1 ~ 20
		@NotEmpty
		@Pattern(regexp = "[a-zA-Z가-힣]{1,20}", message = "한글/영문 1~20자 이내로 작성해주세요.")
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
