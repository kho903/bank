package shop.jikim.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.jikim.bank.config.jwt.JwtAuthenticationFilter;
import shop.jikim.bank.config.jwt.JwtAuthorizationFilter;
import shop.jikim.bank.domain.user.UserEnum;
import shop.jikim.bank.dto.ResponseDto;
import shop.jikim.bank.util.CustomResponseUtil;

@Configuration
public class SecurityConfig {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Bean // IoC 컨테이너에 BCryptPasswordEncoder 객체가 등록됨.
	public BCryptPasswordEncoder passwordEncoder() {
		log.debug("디버그 : BCryptPasswordEncoder 빈 등록됨");
		return new BCryptPasswordEncoder();
	}

	// JWT 필터 등록
	public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
		@Override
		public void configure(HttpSecurity builder) throws Exception {
			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
			builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
			builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
			super.configure(builder);
		}
	}

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
			.apply(new CustomSecurityFilterManager())
			.and()
			.authorizeRequests()
			.antMatchers("/api/s/**").authenticated()
			.antMatchers("/api/admin/**")
			.hasRole(UserEnum.ADMIN + "")
			.anyRequest().permitAll();

		// Exception 가로채기
		// 인증 실패
		http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
			CustomResponseUtil.fail(response, "로그인을 진행해 주세요.", HttpStatus.UNAUTHORIZED);
		});

		// 권한 실패
		http.exceptionHandling().accessDeniedHandler((request, response, e) -> {
			CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN);
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
