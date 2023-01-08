package md.felicia.symphomybufferprep.config;

import md.felicia.symphomybufferprep.security.JWTFilter;
import md.felicia.symphomybufferprep.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

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

        http    .csrf()
                    .disable()
                .cors()
                    .disable()
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
                new ActiveDirectoryLdapAuthenticationProvider( "felicia.local", "ldap://batman.felicia.local","dc=felicia,dc=local");

        activeDirectoryLdapAuthenticationProvider.setConvertSubErrorCodesToExceptions(true);
        activeDirectoryLdapAuthenticationProvider.setUseAuthenticationRequestCredentials(true);
        activeDirectoryLdapAuthenticationProvider.setSearchFilter("(&(objectClass=user)(sAMAccountName={1}))");

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

