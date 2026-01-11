package com.app.nihongo.service.jwt;

import com.app.nihongo.entity.User;
import com.app.nihongo.service.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    private Key signingKey;
    private SignatureAlgorithm signingAlgorithm;

    // Tạo JWT dựa trên tên đang nhập
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        User user = userService.findByUsername(username);

        claims.put("id", user.getUserId());

        return createToken(claims, username);
    }

    // Tạo JWT với các claim đã chọn
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // JWT hết hạn sau 24 giờ
                .signWith(getSignKey(), signingAlgorithm)
                .compact();
    }

    private Key getSignKey() {
        if (signingKey == null) {
            throw new IllegalStateException("JWT signing key has not been initialized.");
        }
        return signingKey;
    }

    // Trích xuất thông tin
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @PostConstruct
    private void init() {
        byte[] keyBytes = decodeSecret(secret);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.signingAlgorithm = SignatureAlgorithm.forSigningKey(signingKey);
    }

    private byte[] decodeSecret(String secretValue) {
        if (secretValue == null || secretValue.isBlank()) {
            throw new IllegalStateException("JWT secret must be configured and cannot be blank.");
        }

        try {
            return Decoders.BASE64.decode(secretValue.trim());
        } catch (IllegalArgumentException ignored) {
            return secretValue.getBytes(StandardCharsets.UTF_8);
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsFunction.apply(claims);
    }

    // Kiểm tra tời gian hết hạn từ JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Kiểm tra tời gian hết hạn từ JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Kiểm tra cái JWT đã hết hạn
    private Boolean isTokenExpired(String token) {
        // if(extractExpiration(token).before(new Date())){
        // System.out.println("Lỗi date ở check token");
        // }
        return extractExpiration(token).before(new Date());
    }

    // Kiểm tra tính hợp lệ
    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsername(token);
        // System.out.println(username + " username " + userDetails.getUsername() + "
        // validate token");
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Integer extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return (Integer) claims.get("id"); // Make sure the key matches the one used during token creation
    }

}
