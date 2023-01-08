package md.felicia.symphomybufferprep.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {
    private RequestMatcher requestMatcher;
    public JwtAuthenticationFilter(String path) {
        this.requestMatcher = new AntPathRequestMatcher(path);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (!requiresAuthentication((HttpServletRequest) servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String authHeader = ((HttpServletRequest) servletRequest).getHeader("Authorization");

        String jwt = authHeader.substring(7);



     //   Authentication authentication = ((HttpServletRequest) servletRequest).getHeader()
    }
    private boolean requiresAuthentication(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }
}
