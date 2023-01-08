package md.felicia.symphomybufferprep.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import md.felicia.symphomybufferprep.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class LoginAuthenticationFilter  extends BasicAuthenticationFilter {
    public LoginAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header  == null|| !header.startsWith("Bearer ")){
            chain.doFilter(request, response);
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token =  request.getHeader("Authorization");
        if (token !=null){
            String user = JWT.require(Algorithm.HMAC512("${jwt.secret}".getBytes()))
                    .build()
                    .verify(token.replace("Bearer ", ""))
                    .getSubject();

            if(user != null){
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
        }
    return null;
    }
}
