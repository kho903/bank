package shop.jikim.bank.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.jikim.bank.config.auth.LoginUser;
import shop.jikim.bank.dto.user.UserRequestDto.LoginRequestDto;
import shop.jikim.bank.dto.user.UserResponseDto.LoginResponseDto;
import shop.jikim.bank.util.CustomResponseUtil;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		setFilterProcessesUrl("/api/login");
		this.authenticationManager = authenticationManager;
	}

	// POST: /api/login
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.debug("디버그 : attemptAuthentication 호출됨");
		try {
			ObjectMapper om = new ObjectMapper();
			LoginRequestDto loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);

			// 강제 로그인
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequestDto.getUsername(), loginRequestDto.getPassword());

			// UserDetailsService의 loadUserByUsername 호출
			// JWT 쓴다 하더라도, 컨트롤러 진입을 하면 시큐리티의 권한, 인증 체크의 도움을 받을 수 있게 세션을 만든다.
			// 이 세션의 유효기간은 request 하고, response 하면 끝!
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			return authentication;
		} catch (Exception e) {
			// authenticationEntryPoint 에 걸림.
			throw new InternalAuthenticationServiceException(e.getMessage());
		}
	}

	// return authentication 이 잘 작동하면 해당 메서드 successfulAuthentication() 호출됨.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		logger.debug("디버그 : successfulAuthentication 호출됨");
		LoginUser loginUser = (LoginUser)authResult.getPrincipal();
		String jwtToken = JwtProcess.create(loginUser);
		response.addHeader(JwtVO.HEADER, jwtToken);

		LoginResponseDto loginResponseDto = new LoginResponseDto(loginUser.getUser());
		CustomResponseUtil.success(response, loginResponseDto);
	}
}
