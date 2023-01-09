package md.felicia.symphomybufferprep.controller;

import md.felicia.symphomybufferprep.DTO.AuthenticationDTO;
import md.felicia.symphomybufferprep.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
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
    public ResponseEntity<?>authUser(@RequestBody AuthenticationDTO authenticationDTO){
        if(authenticationDTO.getLogin().isEmpty()||authenticationDTO.getPassword().isEmpty()){
            return new ResponseEntity<>("Username or Password should not be empty", HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword()));

         String token = jwtUtil.generateToken(authentication);

        return new ResponseEntity<>(Map.of("jwtToken", token), HttpStatus.OK);
    }

}
