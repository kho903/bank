package shop.jikim.bank.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.jikim.bank.config.dummy.DummyObject;
import shop.jikim.bank.domain.account.Account;
import shop.jikim.bank.domain.account.AccountRepository;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserRepository;
import shop.jikim.bank.dto.account.AccountRequestDto.AccountSaveRequestDto;
import shop.jikim.bank.dto.account.AccountResponseDto;
import shop.jikim.bank.dto.account.AccountResponseDto.AccountSaveResponseDto;
import shop.jikim.bank.service.AccountService.AccountListResponseDto;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest extends DummyObject {

	@InjectMocks // 모든 Mock 들이 InjectMocks 로 주입됨
	private AccountService accountService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private AccountRepository accountRepository;

	@Spy // 진짜 객체를 InjectMocks 에 주입함.
	private ObjectMapper om;

	@Test
	public void saveAccount_test() throws Exception {
	    // given
		Long userId = 1L;

		AccountSaveRequestDto accountSaveRequestDto = new AccountSaveRequestDto();
		accountSaveRequestDto.setNumber(1111L);
		accountSaveRequestDto.setPassword(1234L);

		// stub 1
		User user = newMockUser(userId, "user", "username");
		when(userRepository.findById(any())).thenReturn(Optional.of(user));

		// stub 2
		when(accountRepository.findByNumber(any())).thenReturn(Optional.empty());

		// stub 3
		Account userAccount = newMockAccount(1L, 1111L, 1000L, user);
		when(accountRepository.save(any())).thenReturn(userAccount);

	    // when
		AccountSaveResponseDto accountSaveResponseDto = accountService.saveAccount(accountSaveRequestDto, userId);
		String responseBody = om.writeValueAsString(accountSaveResponseDto);
		System.out.println("responseBody = " + responseBody);

	    // then
		assertThat(accountSaveRequestDto.getNumber()).isEqualTo(1111L);
	}

	@Test
	public void findAccountByUser_test() throws Exception {
	    // given
		Long userId = 1L;
		User user = newMockUser(userId, "user", "username");
		when(userRepository.findById(any())).thenReturn(Optional.of(user));

		Account account = newMockAccount(1111L, 1234L, 1000L, user);
		Account account2 = newMockAccount(2222L, 2345L, 1000L, user);
		List<Account> list = Arrays.asList(account, account2);
		when(accountRepository.findByUser_Id(any())).thenReturn(list);

		// when
		AccountListResponseDto accountListResponseDto = accountService.findAccountByUser(userId);

		String responseBody = om.writeValueAsString(accountListResponseDto);
		System.out.println("responseBody = " + responseBody);

		// then
		assertThat(accountListResponseDto.getFullname()).isEqualTo("username");
		assertThat(accountListResponseDto.getAccounts().size()).isEqualTo(2);
	}
}