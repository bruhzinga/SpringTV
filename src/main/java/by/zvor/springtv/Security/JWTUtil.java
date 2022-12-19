package by.zvor.springtv.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;


@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(final String login) {
        final Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(120).toInstant());

        return JWT.create().withSubject("User details").withClaim("login", login).withIssuedAt(new Date()).withIssuer("Demo").withExpiresAt(expirationDate).sign(Algorithm.HMAC256(this.secret));
    }

    public String validateTokenAndRetrieveClaim(final String token) {

        final JWTVerifier verifier = JWT.require(Algorithm.HMAC256(this.secret)).withSubject("User details").withIssuer("Demo").build();

        final DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("login").asString();
    }
}
