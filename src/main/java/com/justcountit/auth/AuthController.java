package com.justcountit.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.justcountit.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final String jwtSigningSecret;
    private final AuthenticationManager authenticationManager;

    public AuthController(@Value("${jwt.signing-secret}") String jwtSigningSecret,
                          AuthenticationManager authenticationManager) {
        this.jwtSigningSecret = jwtSigningSecret;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestBody loginRequestBody) {
        try {
            var email = loginRequestBody.email();
            var password = loginRequestBody.password();
            var authToken = new UsernamePasswordAuthenticationToken(email, password);
            var authentication = authenticationManager.authenticate(authToken);

            var user = (User) authentication.getPrincipal();
            var jwt = JWT.create()
                    .withClaim(JwtTokenFilter.USERNAME_CLAIM, user.getUsername())
                    .sign(Algorithm.HMAC256(jwtSigningSecret));

            return ResponseEntity.ok()
                                 .header(HttpHeaders.AUTHORIZATION, jwt)
                                 .build();
        } catch (BadCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
