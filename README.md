# Junit Bank App

## Jpa LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
```java
@CreatedDate // INSERT
@Column(nullable = false)
private LocalDateTime createdAt;

@LastModifiedDate // INSERT, UPDATE
@Column(nullable = false)
private LocalDateTime updatedAt;
```
