package shop.jikim.bank.config.auth;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

	private final UserRepository userRepository;

	// 시큐리티로 로그인이 될때, 시큐리티가 loadUserByUsername() 실행해서 username 체크!!
	// 없으면 오류
	// 있으면 정상적으로 시큐리티 컨텍스트 내부 세션에 로그인된 세션이 만들어진다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userPS = userRepository.findByUsername(username).orElseThrow(
			() -> new InternalAuthenticationServiceException("인증 실패")
		);
		return new LoginUser(userPS);
	}
}
