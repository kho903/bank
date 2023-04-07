package shop.jikim.bank.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.jikim.bank.dto.ResponseDto;
import shop.jikim.bank.dto.user.UserRequestDto.JoinRequestDto;
import shop.jikim.bank.dto.user.UserResponseDto.JoinResponseDto;
import shop.jikim.bank.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody JoinRequestDto joinRequestDto) {
		JoinResponseDto joinResponseDto = userService.join(joinRequestDto);
		return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinResponseDto), HttpStatus.CREATED);
	}
}
