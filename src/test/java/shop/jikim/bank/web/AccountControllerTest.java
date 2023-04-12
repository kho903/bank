package shop.jikim.bank.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static shop.jikim.bank.dto.account.AccountRequestDto.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.jikim.bank.config.dummy.DummyObject;
import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserRepository;

@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AccountControllerTest extends DummyObject {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper om;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		User user = userRepository.save(newUser("user", "username"));
	}

	// jwt token => 인증 필터 => 시큐리티 세션 생성
	// setupBefore=TEST_METHOD (setup 메서드 실행 전에 수행)
	// setupBefore=TEST_EXECUTION (saveAccount_test 메서드 실행 전에 수행)
	@WithUserDetails(value = "user", setupBefore = TestExecutionEvent.TEST_EXECUTION) // 디비에서 username=user 조회를 해서 세션에 담아주는 어노테이션!!
	@Test
	public void saveAccount_test() throws Exception {
	    // given
		AccountSaveRequestDto accountSaveRequestDto = new AccountSaveRequestDto();
		accountSaveRequestDto.setNumber(9999L);
		accountSaveRequestDto.setPassword(1234L);
		String requestBody = om.writeValueAsString(accountSaveRequestDto);
		System.out.println("requestBody = " + requestBody);

	    // when
		ResultActions resultActions = mvc.perform(
			post("/api/s/account")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON));
		String responseBody = resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("responseBody = " + responseBody);

		// then
		resultActions.andExpect(status().isCreated());
	}
}