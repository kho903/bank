package shop.jikim.bank.domain.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	// TODO: refactoring
	Optional<Account> findByNumber(Long number);
}
