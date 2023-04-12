package shop.jikim.bank.dto.account;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.jikim.bank.domain.account.Account;
import shop.jikim.bank.domain.user.User;

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


	@Getter
	@Setter
	public static class AccountListResponseDto {
		private String fullname;
		private List<AccountDto> accounts = new ArrayList<>();

		public AccountListResponseDto(User user, List<Account> accounts) {
			this.fullname = user.getFullname();
			this.accounts = accounts.stream()
				.map(AccountDto::new)
				.collect(Collectors.toList());
		}

		@Getter
		@Setter
		public class AccountDto {
			private Long id;
			private Long number;
			private Long balance;

			public AccountDto(Account account) {
				this.id = account.getId();
				this.number = account.getNumber();
				this.balance = account.getBalance();
			}
		}
	}
}
