package com.kimchang.rpa.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SpringBootTest
@ActiveProfiles("test")
public class JwtApplicationTest {

	private final String SIGN_KEY = "jwt_test";
	private final String SERVICE_IP = "10.10.10.10";
	private final String SERVICE_KEY = "trial_search";

	@DisplayName("JWT 토큰 생성 테스트")
	@Test
	void JWT_토큰_생성_테스트() {
		// 헤더에 jwt의 암호화 방법 정보
		Map<String, Object> jwtHeader = new HashMap<>();
		jwtHeader.put("typ", "JWT");
		
		// 네이버 OCR API KEY 예시 : dnJ0dmdMWVdVZ0tqVEFiUUtQWU5sdHVvQVVyTVJFdmM=
		jwtHeader.put("alg", "HS256");
		jwtHeader.put("regDate", System.currentTimeMillis());
		
		// make claim
		Map<String, Object> claim = new HashMap<>();
		claim.put("service_ip", SERVICE_IP);
		claim.put("service_key", SERVICE_KEY);
		
		String token = Jwts.builder()
				.setHeader(jwtHeader)
				.setClaims(claim)
				.signWith(SignatureAlgorithm.HS256, SIGN_KEY.getBytes())
				.compact();
		
		System.out.println(token);
		
		JWT_토큰_검증_테스트(token, SERVICE_IP, SERVICE_KEY);
	}

	@DisplayName("JWT 토큰 검증 테스트")
//	@Test
	void JWT_토큰_검증_테스트(String jwt, String ip, String key) {
        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SIGN_KEY.getBytes("UTF-8")) // 키 설정
                    .parseClaimsJws(jwt) // jwt의 정보를 파싱해서 시그니처 값을 검증한다.
                    .getBody();
			claimMap = claims;
			
			assertEquals(claimMap.get("service_ip"), ip);
			assertEquals(claimMap.get("service_key"), key);
		} catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
			System.out.println(e);
		} catch (Exception e) { // 나머지 에러의 경우
			System.out.println(e);
		}
    }    
}
