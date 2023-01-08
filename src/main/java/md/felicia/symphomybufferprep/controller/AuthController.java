package md.felicia.symphomybufferprep.controller;

import md.felicia.symphomybufferprep.DTO.AuthenticationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import md.felicia.symphomybufferprep.security.ApiResponse;
import md.felicia.symphomybufferprep.security.JwtAuthenticationResponse;
import md.felicia.symphomybufferprep.security.JwtTokenProvider;
import md.felicia.symphomybufferprep.security.ValidateTokenRequest;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<?>authUser(@RequestBody AuthenticationDTO authenticationDTO){
        if(authenticationDTO.getLogin().isEmpty()||authenticationDTO.getPassword().isEmpty()){
            return new ResponseEntity<>(new ApiResponse(false,"Username or Password should not be empty"), HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.getLogin(), authenticationDTO.getPassword())
        );
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?>validateToken(@Valid @RequestBody ValidateTokenRequest validateTokenRequest){
        String login = null;
        String jwt = validateTokenRequest.getToken();
        if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
            login = jwtTokenProvider.getUserNameFromJWT(jwt);
            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE,"Valid token for user "+ login));
        }else
            return new ResponseEntity<>(new ApiResponse(false,"Invalid Token"), HttpStatus.BAD_REQUEST);
    }
}
