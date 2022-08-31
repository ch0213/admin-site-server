package admin.adminsiteserver.authentication.util;

import admin.adminsiteserver.authentication.dto.response.JwtTokenDto;
import admin.adminsiteserver.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String TOKEN_DELIMITER = " ";
    private static final long ACCESS_TOKEN_VALID_TIME = 30 * 60 * 1000L;
    private static final long REFRESH_TOKEN_VALID_TIME  = 30 * 60 * 1000L * 2 * 24;

    @Value("${security.jwt.token.secret-key}")
    private String SECRET_KEY;

    private final UserDetailsService userDetailsService;

    public JwtTokenDto createTokens(Member member) {
        String accessToken = createToken(member, SECRET_KEY, ACCESS_TOKEN_VALID_TIME);
        String refreshToken = createToken(member, SECRET_KEY, REFRESH_TOKEN_VALID_TIME);

        return new JwtTokenDto(accessToken, refreshToken);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String createToken(Member member, String encodingKey, long validTime) {
        Claims claims = Jwts.claims().setSubject(member.getId().toString());
        claims.put("email", member.getEmail());
        claims.put("name", member.getName());
        claims.put("role", member.getRole().getDescription());

        Date now = new Date();
        Date expireDate = new Date(now.getTime() + validTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(HS256, encodingKey)
                .compact();
    }

    private String extractUserId(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null || !header.startsWith(BEARER)) {
            return null;
        }

        return header.split(TOKEN_DELIMITER)[1].trim();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
