package shop.jikim.bank.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static shop.jikim.bank.dto.user.UserRequestDto.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.jikim.bank.config.dummy.DummyObject;
import shop.jikim.bank.domain.user.UserRepository;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest extends DummyObject {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper om;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		userRepository.save(newUser("user", "1234"));
	}

	@Test
	public void join_success_test() throws Exception {
		// given
		JoinRequestDto joinRequestDto = new JoinRequestDto();
		joinRequestDto.setUsername("love");
		joinRequestDto.setPassword("1234");
		joinRequestDto.setEmail("love@naver.com");
		joinRequestDto.setFullname("러브");

		String requestBody = om.writeValueAsString(joinRequestDto);
		System.out.println("requestBody = " + requestBody);

		// when
		ResultActions resultActions = mvc.perform(
			post("/api/join")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
		);
		String responseBody = resultActions.andReturn().getResponse().getContentAsString();
		System.out.println("responseBody = " + responseBody);

		// then
		resultActions.andExpect(status().isCreated());
	}

	@Test
	public void join_fail_test() throws Exception {
		// given
		JoinRequestDto joinRequestDto = new JoinRequestDto();
		joinRequestDto.setUsername("user");
		joinRequestDto.setPassword("1234");
		joinRequestDto.setEmail("love@naver.com");
		joinRequestDto.setFullname("러브");

		String requestBody = om.writeValueAsString(joinRequestDto);

		// when
		ResultActions resultActions = mvc.perform(
			post("/api/join")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
		);

		// then
		resultActions.andExpect(status().isBadRequest());
	}
}