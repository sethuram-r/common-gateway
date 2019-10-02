package smartshare.commongateway.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import smartshare.commongateway.model.CustomUserDetail;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

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
        this.claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return this.claims;
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        System.out.println("Claims-------->" + claims);

        System.out.println(this.claims.get("sessionId"));
        System.out.println(this.claims.get("userName"));
        return claimsResolver.apply(claims);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //retrieve username from jwt token
    public String getUsernameFromToken(String jwtToken) {
        System.out.println("claims----------->" + this.claims);
//        return getAllClaimsFromToken(jwtToken).get("userName").toString();
        if (null == this.claims) {
            getAllClaimsFromToken(jwtToken);
            return this.claims.get("userName").toString();
        }
        return this.claims.get("userName").toString();


    }

    private String getSessionIdFromToken(String jwtToken) {
        if (null == this.claims.get("sessionId")) {
            getAllClaimsFromToken(jwtToken);
            return this.claims.get("sessionId").toString();
        }
        return this.claims.get("sessionId").toString();
    }


    public Boolean validateToken(String token, CustomUserDetail userDetails) {
        System.out.println("userDetails--------" + userDetails);
        final String username = getUsernameFromToken(token);
        final String sessionId = getSessionIdFromToken(token);
        System.out.println(sessionId.equals(userDetails.getSessionId()));
        System.out.println(username.equals(userDetails.getUsername()));
        System.out.println(!isTokenExpired(token));
        return (sessionId.equals(userDetails.getSessionId()) && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}