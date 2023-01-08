package md.felicia.symphomybufferprep.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtTokenProvider{
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.jwtExpirationInMs}")
    private String jwtExpirationInMs;

    public String generateToken(Authentication authentication){
        LdapUserDetailsImpl userPrincipal = (LdapUserDetailsImpl) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = Date.from(ZonedDateTime.now().plusMinutes(120).toInstant());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserNameFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJwt(authToken);
            return true;
        }catch (SignatureException ex){
            logger.error("Invalid JWT signature");
        }catch (MalformedJwtException ex){
            logger.error("Invalid JWT token");
        }catch (ExpiredJwtException ex){
            logger.error("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            logger.error("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            logger.error("JWT claim string is empty");
        }
        return false;
    }
}
