package shop.jikim.bank.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shop.jikim.bank.domain.user.User;

public class UserResponseDto {

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
