package shop.jikim.bank.config;

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

	@Test
	public void authentication_test() throws Exception {
	    // given
	    // when
		ResultActions resultActions = mvc.perform(get("/api/s/hello"));
		String responseBody = resultActions.andReturn().getResponse().getContentAsString();
		int status = resultActions.andReturn().getResponse().getStatus();
		System.out.println("responseBody = " + responseBody);
		System.out.println("status = " + status);

		// then

	}


	@Test
	public void authorization_test() throws Exception {
	    // given

	    // when

	    // then
	}
}