package shop.jikim.bank.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.jikim.bank.config.dummy.DummyObject;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserEnum;
import shop.jikim.bank.domain.user.UserRepository;
import shop.jikim.bank.dto.user.UserRequestDto.JoinRequestDto;
import shop.jikim.bank.dto.user.UserResponseDto.JoinResponseDto;

// Spring 관련 Bean 들이 하나도 없는 환경!
@ExtendWith(MockitoExtension.class)
class UserServiceTest extends DummyObject {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Spy
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void 회원가입_test() throws Exception {
	    // given
		JoinRequestDto joinRequestDto = new JoinRequestDto();
		joinRequestDto.setUsername("user");
		joinRequestDto.setPassword("1234");
		joinRequestDto.setEmail("user@naver.com");
		joinRequestDto.setFullname("kimuser");

		// stub 1
		when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

		// stub 2
		User user = newMockUser(1L, "user", "fulluser");
		when(userRepository.save(any())).thenReturn(user);

	    // when
		JoinResponseDto joinResponseDto = userService.join(joinRequestDto);
		System.out.println(joinResponseDto);

		// then
		assertThat(joinResponseDto.getId()).isEqualTo(1L);
		assertThat(joinResponseDto.getUsername()).isEqualTo("user");
	}
}