package shop.jikim.bank.domain.account;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	// TODO: refactoring (현재, 계좌 소유자 확인시에 쿼리가 두 번 나감. -> join fetch)
	Optional<Account> findByNumber(Long number);

	// jpa query method
	// select * from account where user_id = :id;
	List<Account> findByUser_Id(Long id);
}
