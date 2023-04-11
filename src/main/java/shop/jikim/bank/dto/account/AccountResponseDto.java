package shop.jikim.bank.dto.account;

import lombok.Getter;
import lombok.Setter;
import shop.jikim.bank.domain.account.Account;

public class AccountResponseDto {

	@Getter
	@Setter
	public static class AccountSaveResponseDto {
		private Long id;
		private Long number;
		private Long balance;

		public AccountSaveResponseDto(Account account) {
			this.id = account.getId();
			this.number = account.getNumber();
			this.balance = account.getBalance();
		}
	}
}
