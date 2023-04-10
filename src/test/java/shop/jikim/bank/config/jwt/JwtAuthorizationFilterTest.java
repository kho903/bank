package shop.jikim.bank.config.jwt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import shop.jikim.bank.config.auth.LoginUser;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserEnum;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JwtAuthorizationFilterTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void authorization_test() throws Exception {
		// given
		User user = User.builder()
			.id(1L)
			.role(UserEnum.CUSTOMER)
			.build();
		LoginUser loginUser = new LoginUser(user);
		String jwtToken = JwtProcess.create(loginUser);
		System.out.println("jwtToken = " + jwtToken);

		// when
		ResultActions resultActions = mvc.perform(
			get("/api/user/hello/test").header(JwtVO.HEADER, jwtToken));

		// then
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	public void authorization_fail_test() throws Exception {
		// given
		// when
		ResultActions resultActions = mvc.perform(
			get("/api/s/hello/test"));

		// then
		resultActions.andExpect(status().isUnauthorized()); // 401
	}

	@Test
	public void authorization_admin_test() throws Exception {
		// given
		User user = User.builder()
			.id(1L)
			.role(UserEnum.ADMIN)
			.build();
		LoginUser loginUser = new LoginUser(user);
		String jwtToken = JwtProcess.create(loginUser);
		System.out.println("jwtToken = " + jwtToken);

		// when
		ResultActions resultActions = mvc.perform(
			get("/api/admin/hello/test").header(JwtVO.HEADER, jwtToken));

		// then
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	public void authorization_admin_forbidden_test() throws Exception {
		// given
		User user = User.builder()
			.id(1L)
			.role(UserEnum.CUSTOMER)
			.build();
		LoginUser loginUser = new LoginUser(user);
		String jwtToken = JwtProcess.create(loginUser);
		System.out.println("jwtToken = " + jwtToken);

		// when
		ResultActions resultActions = mvc.perform(
			get("/api/admin/hello/test").header(JwtVO.HEADER, jwtToken));

		// then
		resultActions.andExpect(status().isForbidden());
	}

}