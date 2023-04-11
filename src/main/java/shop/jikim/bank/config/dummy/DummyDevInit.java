package shop.jikim.bank.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import shop.jikim.bank.domain.user.User;
import shop.jikim.bank.domain.user.UserRepository;

@Configuration
public class DummyDevInit extends DummyObject {

	@Bean
	@Profile("dev")
	CommandLineRunner init(UserRepository userRepository) {
		return (args -> {
			// 서버 실행시에 무조건 실행됨.
			User user = userRepository.save(newUser("user", "1234"));
		});
	}
}
