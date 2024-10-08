package dev.max.springAuthentication.controllers;

import dev.max.springAuthentication.dtos.LoginUserDto;
import dev.max.springAuthentication.dtos.RegisterUserDto;
import dev.max.springAuthentication.entities.User;
import dev.max.springAuthentication.response.LoginResponse;
import dev.max.springAuthentication.response.RegisterResponse;
import dev.max.springAuthentication.services.AuthenticationService;
import dev.max.springAuthentication.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Max
 * @since 10/3/2024
 */

@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authService.register(registerUserDto);

        String jwtToken = jwtService.generateToken(registeredUser);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUser(registeredUser);
        registerResponse.setToken(jwtToken);
        registerResponse.setExpiresIn(jwtService.getJwtExpiration());

        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(authenticatedUser);
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getJwtExpiration());

        return ResponseEntity.ok(loginResponse);
    }

}
