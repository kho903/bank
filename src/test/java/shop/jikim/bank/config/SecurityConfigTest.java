package shop.jikim.bank.config;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc // Mock (가짜) 환경에 MockMvc 가 등록됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SecurityConfigTest {

	// 가짜 횐경에 등록된 MockMvc를 DI
	@Autowired
	private MockMvc mvc;

	// 서버는 일관성있게 에러가 리턴되어야 함.
	// 내가 모르는 에러가 프론트한테 날라가지 않게, 직접 다 제어하자.
	@Test
	public void authentication_test() throws Exception {
	    // given
	    // when
		ResultActions resultActions = mvc.perform(get("/api/s/hello"));
		String responseBody = resultActions.andReturn().getResponse().getContentAsString();
		int httpStatusCode = resultActions.andReturn().getResponse().getStatus();
		System.out.println("responseBody = " + responseBody);
		System.out.println("httpStatusCode = " + httpStatusCode);

		// then
		assertThat(httpStatusCode).isEqualTo(401);
	}


	@Test
	public void authorization_test() throws Exception {
	    // given

	    // when
		ResultActions resultActions = mvc.perform(get("/api/admin/hello"));
		String responseBody = resultActions.andReturn().getResponse().getContentAsString();
		int httpStatusCode = resultActions.andReturn().getResponse().getStatus();
		System.out.println("responseBody = " + responseBody);
		System.out.println("httpStatusCode = " + httpStatusCode);

	    // then
		assertThat(httpStatusCode).isEqualTo(401);
	}
}