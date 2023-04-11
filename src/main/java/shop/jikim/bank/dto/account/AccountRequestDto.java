package shop.jikim.bank.dto.account;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import shop.jikim.bank.domain.account.Account;
import shop.jikim.bank.domain.user.User;

public class AccountRequestDto {

	@Getter
	@Setter
	public static class AccountSaveRequestDto {
		@NotNull
		@Digits(integer = 4, fraction = 4)
		private Long number;
		@NotNull
		@Digits(integer = 4, fraction = 4)
		private Long password;

		public Account toEntity(User user) {
			return Account.builder()
				.number(number)
				.password(password)
				.balance(1000L)
				.user(user)
				.build();
		}
	}
}
