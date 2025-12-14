package sa.tamyuz.market.general.security.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24-hours default
    private long jwtExpiration;

    public String generateToken(String email) {
        return JWT
                .create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .withIssuer("tamyuz.market")
                .sign(Algorithm.HMAC256(secret));
    }

    public String extractUsername(String token) {
        return verifyToken(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return verifyToken(token).getExpiresAt().before(new Date());
    }

    private DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return JWT
                .require(Algorithm.HMAC256(secret))
                .withIssuer("tamyuz.market")
                .build()
                .verify(token);
    }
}

