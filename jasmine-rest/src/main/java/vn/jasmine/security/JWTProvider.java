package vn.jasmine.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import vn.jasmine.constants.JasmineMessageConst;
import vn.jasmine.security.exception.instance.BlackListTokenException;
import vn.jasmine.security.exception.instance.ExpiredAccessTokenException;
import vn.jasmine.security.service.IJWTService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class JWTProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecretKey;

    @Value("${app.jwt.expiration.minutes}")
    private Long jwtExpirationMinutes;

    @Value("${app.jwt.jwtCookieName}")
    private String jwtCookie;

    @Value("${app.jwt.jwtRefreshCookieName}")
    private String jwtRefreshCookie;

    private final IJWTService jwtService;
    private final UserDetailsService userDetailsService;

    public String generateToken(String username) {

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusMinutes(jwtExpirationMinutes);

        return Jwts.builder()
                .header().add("typ", TOKEN_TYPE)
                .and()
                .subject(userDetails.getUsername())
                .claim("email", userDetails.getEmail())
                .claim("rol", roles)
                .issuer(TOKEN_ISSUER)
                .issuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
                .audience().and()
                .signWith(getSignInKey())
                .compact();
    }

    protected Claims extractAllClaims(String token) {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload();
    }

    protected String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    protected boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    protected void validateToken(String token) {
        if (jwtService.isBlackListToken(token)) {
            throw new BlackListTokenException(JasmineMessageConst.BLACK_LIST_TOKEN);
        } else if (isTokenExpired(token)) {
            throw new ExpiredAccessTokenException(JasmineMessageConst.ACCESS_TOKEN_EXPIRED);
        }
    }

    protected Optional<Jws<Claims>> validateTokenAndGetJws(String token) {

        validateToken(token);

        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));

            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return Optional.of(jws);
        } catch (ExpiredJwtException exception) {
            log.error("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
        } catch (UnsupportedJwtException exception) {
            log.error("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
        } catch (MalformedJwtException exception) {
            log.error("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            log.error("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
        }

        return Optional.empty();
    }

    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtCookie);
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/api/auth/refreshtoken").build();
        return cookie;
    }

    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    private SecretKey getSignInKey() {
        byte[] bytes = Base64.getDecoder()
                .decode(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    public ResponseCookie generateJwtCookie(String jwt) {
        return generateCookie(jwtCookie, jwt, "/api");
    }

    public ResponseCookie generateRefreshJwtCookie(UUID refreshToken) {
        return generateCookie(jwtRefreshCookie, refreshToken.toString(), "/api/auth/refresh-token");
    }

    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value).path(path).maxAge(24 * 60 * 60L).httpOnly(true).build();
    }

    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_ISSUER = "canis";

}
