package shop.jikim.bank.web;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.jikim.bank.config.auth.LoginUser;
import shop.jikim.bank.dto.ResponseDto;
import shop.jikim.bank.dto.account.AccountRequestDto.AccountSaveRequestDto;
import shop.jikim.bank.dto.account.AccountResponseDto;
import shop.jikim.bank.handler.exception.CustomForbiddenException;
import shop.jikim.bank.service.AccountService;
import shop.jikim.bank.service.AccountService.AccountListResponseDto;

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

	// 인증이 필요하고, account 테이블에 1번 row를 주세요!
	// user로 로그인을 했는데, user 의 id가 2번이면 ? => 검증 필요. => 검증하는 것이 너무 장황해짐.
	// 권한 처리 때문에 선호 X
	// 인증이 필요하고, account 테이블에 login 한 유저의 계좌만 주세요.
	@GetMapping("/s/account/login-user")
	public ResponseEntity<?> findUserAccount(@AuthenticationPrincipal LoginUser loginUser) {
		AccountListResponseDto accountListResponseDto = accountService.findAccountByUser(loginUser.getUser().getId());
		return new ResponseEntity<>(new ResponseDto<>(1, "계좌목록보기_유저별 성공", accountListResponseDto), HttpStatus.OK);
	}
}
