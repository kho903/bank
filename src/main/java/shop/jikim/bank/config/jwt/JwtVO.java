package shop.jikim.bank.config.jwt;

/**
 * SECRET : 원래 노출되면 안 된다. (클라우드AWS - 환경변수 or 파일에 있는 것을 읽기)
 * 리프레시 토큰 구현 X
 */
public interface JwtVO {
	public static final String SECRET = "jikim"; // HS256 (대칭키)
	public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 일주일
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER = "Authorization";

}