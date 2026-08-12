package com.foodorder.user.infrastructure.web.controller;

import com.foodorder.user.application.dto.LoginCommand;
import com.foodorder.user.application.dto.LoginResult;
import com.foodorder.user.application.dto.RegisterUserCommand;
import com.foodorder.user.application.usecase.LoginUseCase;
import com.foodorder.user.application.usecase.RegisterUserUseCase;
import com.foodorder.user.domain.model.User;
import com.foodorder.user.infrastructure.web.dto.LoginRequest;
import com.foodorder.user.infrastructure.web.dto.LoginResponse;
import com.foodorder.user.infrastructure.web.dto.RegisterUserRequest;
import com.foodorder.user.infrastructure.web.dto.UserResponse;
import com.foodorder.user.infrastructure.web.mapper.UserWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final UserWebMapper userWebMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        User user = registerUserUseCase.execute(new RegisterUserCommand(
                request.firstName(), request.lastName(), request.email(), request.password()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userWebMapper.toResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResult result = loginUseCase.execute(new LoginCommand(request.email(), request.password()));
        return ResponseEntity.ok(LoginResponse.of(result.token(), result.expiresInSeconds()));
    }
}
