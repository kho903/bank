package shop.jikim.bank.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserEnum;
import shop.jikim.bank.domain.user.UserRepository;
import shop.jikim.bank.handler.exception.CustomApiException;

@Service
@RequiredArgsConstructor
public class UserService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	// 서비스는 DTO를 요청받고, DTO를 응답한다.
	@Transactional
	public JoinResponseDto join(JoinRequestDto joinRequestDto) {
		// 1. 동일 유저네임 존재 검사
		Optional<User> optionalUser = userRepository.findByUsername(joinRequestDto.getUsername());
		if (optionalUser.isPresent()) {
			// 유저네임 중복되었다는 뜻
			throw new CustomApiException("동일한 username이 존재합니다.");
		}

		// 2. 패스워드 인코딩 + 회원가입
		User userPs = userRepository.save(joinRequestDto.toEntity(passwordEncoder));

		// 3. dto 응답
		return new JoinResponseDto(userPs);
	}

	@Getter
	@Setter
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

	@Getter
	@Setter
	public static class JoinRequestDto {
		// 유효성 검사
		private String username;
		private String password;
		private String email;
		private String fullname;

		public User toEntity(BCryptPasswordEncoder passwordEncoder) {
			return User.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.email(email)
				.fullname(fullname)
				.role(UserEnum.CUSTOMER)
				.build();
		}
	}

}
