package md.felicia.symphomybufferprep.config;

import md.felicia.symphomybufferprep.security.JWTFilter;
import md.felicia.symphomybufferprep.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

    @Value("${ad.ldap.server-address}")
    private String AD_LDAP_URL_SERVER;
    @Value("${ad.root-dn}")
    private String AD_ROOT_DN;
    @Value("${ad.search-filter}")
    private String AD_SEARCH_FILTER;
    @Value("${ad.domain}")
    private String AD_DOMAIN;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AuthenticationConfiguration authenticationConfiguration;

   @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, AuthenticationConfiguration authenticationConfiguration) {
       this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
       this.authenticationConfiguration = authenticationConfiguration;
   }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

       http = http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
        }).and();

        http.csrf()
                    .disable()
                .cors().and()
                .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/logs/**").access("hasAuthority('SymphonyLogsCalculation')")
                .anyRequest().authenticated()
        .and()
                .addFilter(new JWTFilter(authenticationManager()));
            http.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
        return http.build();
    }

    @Bean
    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {

        ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider =
                new ActiveDirectoryLdapAuthenticationProvider( AD_DOMAIN, AD_LDAP_URL_SERVER,AD_ROOT_DN);

        activeDirectoryLdapAuthenticationProvider.setConvertSubErrorCodesToExceptions(true);
        activeDirectoryLdapAuthenticationProvider.setUseAuthenticationRequestCredentials(true);
        activeDirectoryLdapAuthenticationProvider.setSearchFilter(AD_SEARCH_FILTER);

        return activeDirectoryLdapAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

