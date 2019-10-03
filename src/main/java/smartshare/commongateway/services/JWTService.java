package smartshare.commongateway.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smartshare.commongateway.model.CustomUserDetail;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;


@Slf4j
@Service
public class JWTService {

    public static final String TOKEN_PREFIX = "Bearer";
    public static final String TOKEN_NAME = "Authentication";
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    @Value("${jwt.secret}")
    private String secret;
    private Claims claims;


    public String generateToken(Map userDetails) {
        return TOKEN_PREFIX + Jwts.builder().addClaims(userDetails).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    private String getStoredCustomClaimsFromToken(String jwtToken, String customClaim) {
        this.claims = getAllClaimsFromToken(jwtToken);
        return this.claims.get(customClaim).toString();
    }

    public String getUsernameFromToken(String jwtToken) {
        return getStoredCustomClaimsFromToken(jwtToken, "userName");

    }

    private String getSessionIdFromToken(String jwtToken) {
        return getStoredCustomClaimsFromToken(jwtToken, "sessionId");
    }

    public Boolean validateToken(String token, CustomUserDetail userDetails) {
        log.info("Validating Token");
        return (getSessionIdFromToken(token).equals(userDetails.getSessionId()) && getUsernameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}