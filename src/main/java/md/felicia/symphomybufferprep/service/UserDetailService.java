package md.felicia.symphomybufferprep.service;

import md.felicia.symphomybufferprep.config.SecurityConfig;
import md.felicia.symphomybufferprep.config.UsersDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new UsersDetails(username);
    }
}
