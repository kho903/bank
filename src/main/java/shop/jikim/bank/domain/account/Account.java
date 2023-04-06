package shop.jikim.bank.domain.account;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.jikim.bank.domain.user.User;

@Entity
@Getter
@Table(name = "account_tb")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, length = 20)
	private Long number; // 계좌 번호

	@Column(nullable = false, length = 4)
	private Long password; // 계좌 비밀 번호

	@Column(nullable = false)
	private Long balance; // 잔액 (기본값 1000원)

	// 항상 ORM에서 fk 의 주인은 Many Entity 쪽.
	@ManyToOne(fetch = FetchType.LAZY) // account.getUser().필드호출() == LAZY 발동
	private User user; // user_id

	@CreatedDate // INSERT
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate // INSERT, UPDATE
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Builder
	public Account(Long id, Long number, Long password, Long balance, User user, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this.id = id;
		this.number = number;
		this.password = password;
		this.balance = balance;
		this.user = user;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
