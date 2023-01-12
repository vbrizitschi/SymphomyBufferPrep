package md.felicia.symphomybufferprep.controller;

import md.felicia.symphomybufferprep.DTO.AuthenticationDTO;
import md.felicia.symphomybufferprep.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, Object>authUser(@RequestBody AuthenticationDTO authenticationDTO){

        Authentication authentication;

        if(authenticationDTO.getLogin().isEmpty()||authenticationDTO.getPassword().isEmpty()){
            return Map.of("Username or Password should not be empty", HttpStatus.BAD_REQUEST);
        }

       // try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword()));
       // }catch (BadCredentialsException e){
         //   return Map.of("message", e.getMessage());
       // }

         String token = jwtUtil.generateToken(authentication);

        return  Map.of("jwtToken", token);
    }

}
