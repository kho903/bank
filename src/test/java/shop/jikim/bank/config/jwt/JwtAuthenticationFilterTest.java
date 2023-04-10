package shop.jikim.bank.config.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static shop.jikim.bank.dto.user.UserRequestDto.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class JwtAuthenticationFilterTest extends DummyObject {

	@Autowired
	private ObjectMapper om;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() throws Exception {
		userRepository.save(newUser("user", "username"));
	}

	@Test
	public void successfulAuthentication() throws Exception {
	    // given
		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setUsername("user");
		loginRequestDto.setPassword("1234");
		String requestBody = om.writeValueAsString(loginRequestDto);
		System.out.println("requestBody = " + requestBody);

	    // when
		ResultActions resultActions = mvc.perform(
			post("/api/login")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
		);
		String responseBody = resultActions.andReturn().getResponse().getContentAsString();
		String header = resultActions.andReturn().getResponse().getHeader(JwtVO.HEADER);
		System.out.println("responseBody = " + responseBody);
		System.out.println("header = " + header);

		// then
		resultActions.andExpect(status().isOk());
		assertNotNull(header);
		assertTrue(header.startsWith(JwtVO.TOKEN_PREFIX));
		resultActions.andExpect(jsonPath("$.data.username").value("user"));
	}

	@Test
	public void unsuccessfulAuthentication() throws Exception {
	    // given
		LoginRequestDto loginRequestDto = new LoginRequestDto();
		loginRequestDto.setUsername("");
		loginRequestDto.setPassword("1234");
		String requestBody = om.writeValueAsString(loginRequestDto);
		System.out.println("requestBody = " + requestBody);

		// when
		ResultActions resultActions = mvc.perform(
			post("/api/login")
				.content(requestBody)
				.contentType(MediaType.APPLICATION_JSON)
		);
		String responseBody = resultActions.andReturn().getResponse().getContentAsString();
		String header = resultActions.andReturn().getResponse().getHeader(JwtVO.HEADER);
		System.out.println("responseBody = " + responseBody);
		System.out.println("header = " + header);

		// then
		resultActions.andExpect(status().isUnauthorized());
	}
}