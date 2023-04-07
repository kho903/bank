package shop.jikim.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.util.CustomDateUtil;

public class UserResponseDto {

	@Getter
	@Setter
	public static class LoginResponseDto {
		private Long id;
		private String username;
		private String createdAt;

		public LoginResponseDto(User user) {
			this.id = user.getId();
			this.username = user.getUsername();
			this.createdAt = CustomDateUtil.toStringFormat(user.getCreatedAt());
		}
	}

	@Getter
	@Setter
	@ToString
	public static class JoinResponseDto {
		private Long id;
		private String username;
		private String fullname;

		public JoinResponseDto(User user) {
			this.id = user.getId();
			this.username = user.getUsername();
			this.fullname = user.getFullname();
		}
	}
}
