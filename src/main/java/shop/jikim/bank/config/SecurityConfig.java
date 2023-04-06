package shop.jikim.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import shop.jikim.bank.domain.user.UserEnum;

@Configuration
public class SecurityConfig {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Bean // IoC 컨테이너에 BCryptPasswordEncoder 객체가 등록됨.
	public BCryptPasswordEncoder passwordEncoder() {
		log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
		return new BCryptPasswordEncoder();
	}

	// TODO: JWT 필터 등록

	// JWT 서버를 만들 예정 ! Session 사용 안함.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		log.debug("디버그 : filterChain 빈 등록됨");
		http
			.headers().frameOptions().disable() // iframe 허용 안함.
			.and()
			.csrf().disable()
			.cors().configurationSource(configurationSource())
			.and()
			// jSessionId를 서버쪽에서 관리 X
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			// react, 앱으로 요청하기 (백엔드에서 X)
			.formLogin().disable()
			// httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다.
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers("/api/s/**").authenticated()
			.antMatchers("/api/admin/**")
			.hasRole(UserEnum.ADMIN + "")
			.anyRequest().permitAll();

		// Exception 가로채기
		http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
			// response.setContentType("application/json; charset=utf-8");
			response.setStatus(403);
			response.getWriter().println("error"); // TODO: 메시지를 포장하는 공통적인 응답 DTO
		});

		return http.build();
	}

	public CorsConfigurationSource configurationSource() {
		log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*"); // GET, POST, PUT, DELETE (js 요청 허용)
		configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (프론트 IP만 허용 이라던지 커스텀 가능)
		configuration.setAllowCredentials(true); // 클라이언트에서 쿠키 요청 허용

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
