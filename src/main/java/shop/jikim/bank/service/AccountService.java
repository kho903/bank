package shop.jikim.bank.service;

import java.util.Optional;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import shop.jikim.bank.domain.account.Account;
import shop.jikim.bank.domain.account.AccountRepository;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserRepository;
import shop.jikim.bank.handler.exception.CustomApiException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;
	private final UserRepository userRepository;

	@Transactional
	public AccountSaveResponseDto saveAccount(AccountSaveRequestDto accountSaveRequestDto, Long userId) {
		// User 가 DB에 있는지 검증
		User userPs = userRepository.findById(userId)
			.orElseThrow(() -> new CustomApiException("유저를 찾을 수 없습니다."));

		// 해당 계좌가 DB에 있는지 중복여부 체크
		Optional<Account> optionalAccount = accountRepository.findByNumber(accountSaveRequestDto.getNumber());
		if (optionalAccount.isPresent()) {
			throw new CustomApiException("해당 계좌가 이미 존재합니다.");
		}

		// 계좌 등록
		Account accountPS = accountRepository.save(accountSaveRequestDto.toEntity(userPs));

		// DTO 응답
		return new AccountSaveResponseDto(accountPS);
	}

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
