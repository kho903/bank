package shop.jikim.bank.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.jikim.bank.config.auth.LoginUser;
import shop.jikim.bank.dto.ResponseDto;
import shop.jikim.bank.dto.account.AccountRequestDto.AccountSaveRequestDto;
import shop.jikim.bank.dto.account.AccountResponseDto;
import shop.jikim.bank.service.AccountService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;

	@PostMapping("/s/account")
	public ResponseEntity<?> saveAccount(
			@RequestBody @Valid AccountSaveRequestDto accountSaveRequestDto,
			BindingResult bindingResult,
			@AuthenticationPrincipal LoginUser loginUser) {

		AccountResponseDto.AccountSaveResponseDto accountSaveResponseDto =
			accountService.saveAccount(accountSaveRequestDto, loginUser.getUser().getId());
		return new ResponseEntity<>(
			new ResponseDto<>(1, "계좌등록 성공", accountSaveResponseDto),
			HttpStatus.CREATED
		);
	}
}
