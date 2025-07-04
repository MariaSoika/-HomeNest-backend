package org.homeNest.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.homeNest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
//@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;

    //@Operation(summary = "Регистрация пользователя")

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/admin/signup")
    public JwtAuthenticationResponse adminSignUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.adminSignUp(request);
    }

    //@Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/admin/sign-in")
    public JwtAuthenticationResponse adminSignIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signInAsAdmin(request);
    }
}
