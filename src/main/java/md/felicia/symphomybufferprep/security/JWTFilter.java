package md.felicia.symphomybufferprep.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends BasicAuthenticationFilter {

    // to do, тут не считыавается с файла
    @Value("${jwt.secret}")
    private String secret = "QEwQsFv3C2n4wMJ2rAub4C4s8Xy3Wr2Gadgjhklksgjhkjzhfsgk5vctBQBukldjfgjsgjrl0456684dfhg684064jkljjkbhjrxpzb";
    public JWTFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {


        String header = req.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        String token = header.substring(7);
        //to do заебинить проверки
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("Felicia IT Group")
                .build();

        DecodedJWT jwt = verifier.verify(token);

        String user = jwt.getClaim("username").asString();
        List<SimpleGrantedAuthority> grantedAuthorityList = jwt.getClaim("authorities").asList(SimpleGrantedAuthority.class) ;

        if (user != null) {
            // new arraylist means authorities
            return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorityList);
        }

        return null;  //to do тут хуйня

    }
}

