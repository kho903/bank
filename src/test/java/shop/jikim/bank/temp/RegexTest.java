package shop.jikim.bank.temp;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

// java.util.regexPattern
public class RegexTest {

	@Test
	public void 한글만_regex_test() throws Exception {
		String value = "가나다";
		boolean result = Pattern.matches("^[ㄱ-힣]+$", value);
		System.out.println("result = " + result);
	}

	@Test
	public void 한글X_regex_test() throws Exception {
		String value = "";
		boolean result = Pattern.matches("^[^ㄱ-힣]*$", value);
		System.out.println("result = " + result);
	}

	@Test
	public void 영어만_regex_test() throws Exception {
		String value = "abc";
		boolean result = Pattern.matches("^[a-zA-Z]+$", value);
		System.out.println("result = " + result);
	}

	@Test
	public void 영어X_regex_test() throws Exception {
		String value = "abc";
		boolean result = Pattern.matches("^[^a-zA-Z]*$", value);
		System.out.println("result = " + result);
	}

	@Test
	public void 영어_숫자_regex_test() throws Exception {
		String value = "abc123";
		boolean result = Pattern.matches("^[a-zA-Z0-9]+$", value);
		System.out.println("result = " + result);
	}

	@Test
	public void 영어만되고_길이최소2_최대4_regex_test() throws Exception {
		String value = "a";
		boolean result = Pattern.matches("^[a-zA-Z0-9]{2,4}$", value);
		System.out.println("result = " + result);
	}

	@Test
	public void user_username_test() throws Exception {
		String username = "user";
		boolean result = Pattern.matches("[a-zA-Z0-9]{2,20}", username);
		System.out.println("result = " + result);
	}

	@Test
	public void user_fullname_test() throws Exception {
		String username = "김지훈";
		boolean result = Pattern.matches("[a-zA-Z가-힣]{1,20}", username);
		System.out.println("result = " + result);
	}

	@Test
	public void user_email_test() throws Exception {
		String email = "gmldnr2222@naver.com";
		boolean result = Pattern.matches("[a-zA-Z0-9]{2,10}@[a-zA-Z]{2,6}\\.[a-zA-Z]{2,3}", email);
		System.out.println("result = " + result);
	}
}
