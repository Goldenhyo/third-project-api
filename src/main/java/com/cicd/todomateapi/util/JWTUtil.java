package com.cicd.todomateapi.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.key}")
    private String jwtKey;

    public String generateToken(Map<String, Object> claims, int min) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        log.info("---------- secretKey:{}", secretKey);
        String jwt = Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(secretKey)
                .compact();
        log.info("---------- jwt:{}", jwt);
        return jwt;
    }

    public Map<String, Object> validateToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
        Map<String, Object> claim = null;
        try {
            claim = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 비밀키 세팅
                    .build()
                    .parseClaimsJws(token) // 파싱 및 검증 -> 실패하면 에러 발생 -> catch 써서 잡기
                    .getBody();// claim 리턴
        } catch (MalformedJwtException e) { // 잘못된 형식의 토큰 예외
            throw new CustomJWTException("Malformed");
        } catch (ExpiredJwtException e) { // 만료된 토큰 예외
            throw new CustomJWTException("Expired");
        } catch (InvalidClaimException e) { // 유효하지 않은 Claim 토큰 예외
            throw new CustomJWTException("Invalid");
        } catch (JwtException e) { // 그 외 Jwt 관련 예외
            log.info("JwtException:{}", e.getMessage());
            throw new CustomJWTException("JWTError");
        }  catch (Exception e) { //  그 외 나머지 예외
            throw new CustomJWTException("Error");
        }
        return claim;
    }

//    // 특정 사용자에 대한 토큰 생성
//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails.getUsername());
//    }
//
//    // 토큰 생성
//    private String createToken(Map<String, Object> claims, String subject) {
//        SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
//        log.info("---------- secretKey:{}", secretKey);
//        String jwt = Jwts.builder()
//                .setHeader(Map.of("typ", "JWT"))
//                .setClaims(claims)
//                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
//                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(10).toInstant()))
//                .signWith(secretKey)
//                .compact();
//        log.info("---------- jwt:{}", jwt);
//        return jwt;
//    }
//
//    // 토큰에서 username 추출
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    // 토큰 만료 여부 확인
//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    // 토큰에서 claim 추출
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    // 토큰에서 모든 claim 추출
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    }
//
//    // 토큰 유효성 검사
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
}
