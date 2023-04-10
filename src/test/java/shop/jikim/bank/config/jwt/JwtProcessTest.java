package shop.jikim.bank.config.jwt;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import shop.jikim.bank.config.auth.LoginUser;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserEnum;

class JwtProcessTest {
	@Test
	public void create_test() throws Exception {
	    // given
		User user = User.builder()
			.id(1L)
			.role(UserEnum.CUSTOMER)
			.build();
		LoginUser loginUser = new LoginUser(user);

	    // when
		String jwtToken = JwtProcess.create(loginUser);
		System.out.println("jwtToken = " + jwtToken);

		// then
		assertTrue(jwtToken.startsWith(JwtVO.TOKEN_PREFIX));
	}

	@Test
	public void verify_test() throws Exception {
	    // given
		String jwtToken = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJiYW5rIiwiZXhwIjoxNjgxNzAyMzc0LCJpZCI6MSwicm9sZSI6IkNVU1RPTUVSIn0.YPxU4KFLLxFuXN2aot9S1UD7IbRV2QrXBuyiEkht3EtqUs5KzMrw7hF-8gk13oHEEHW7OobICP_RKkZBEInXwQ";

	    // when
		LoginUser loginUser = JwtProcess.verify(jwtToken);
		System.out.println("loginUser.getUser().getId() = " + loginUser.getUser().getId());

	    // then
		assertThat(loginUser.getUser().getId()).isEqualTo(1L);
		assertThat(loginUser.getUser().getRole()).isEqualTo(UserEnum.CUSTOMER);
	}
}