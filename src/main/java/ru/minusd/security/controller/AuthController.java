package ru.minusd.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.minusd.security.domain.dto.JwtAuthenticationResponse;
import ru.minusd.security.domain.dto.SignInRequest;
import ru.minusd.security.domain.dto.SignUpRequest;
import ru.minusd.security.service.AuthenticationService;

@Controller
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    @GetMapping("/register")
    public String getRegistrationForm() {
        return "register";
    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public JwtAuthenticationResponse signUp(@ModelAttribute SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@ModelAttribute SignInRequest request) {
        return authenticationService.signIn(request);
    }
}

