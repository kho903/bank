package shop.jikim.bank.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_tb")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, length = 20)
	private String username;

	@Column(nullable = false, length = 60) // 패스워드 인코딩 (BCrypt)
	private String password;

	@Column(nullable = false, length = 20)
	private String email;

	@Column(nullable = false, length = 20)
	private String fullname;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserEnum role;

	@CreatedDate // INSERT
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate // INSERT, UPDATE
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Builder
	public User(String username, String password, String email, String fullname, UserEnum role, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullname = fullname;
		this.role = role;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
