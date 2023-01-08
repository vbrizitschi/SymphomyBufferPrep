package md.felicia.symphomybufferprep.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil {
    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);
    @Value("${jwt.secret}")
    private String secret ;

    public String generateToken(Authentication authentication) {
        LdapUserDetailsImpl userPrincipal = (LdapUserDetailsImpl) authentication.getPrincipal();
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(120).toInstant());

        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", userPrincipal.getUsername())
                .withClaim("authorities", userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withIssuedAt(new Date())
                .withIssuer("Felicia IT Group")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));

    }

    public String validateAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Felicia IT Group")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }
}
