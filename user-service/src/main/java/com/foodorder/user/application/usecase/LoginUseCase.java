package com.foodorder.user.application.usecase;

import com.foodorder.user.application.dto.LoginCommand;
import com.foodorder.user.application.dto.LoginResult;
import com.foodorder.user.application.port.PasswordHasherPort;
import com.foodorder.user.application.port.TokenProviderPort;
import com.foodorder.user.domain.exception.InvalidCredentialsException;
import com.foodorder.user.domain.model.User;
import com.foodorder.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private static final long TOKEN_TTL_SECONDS = 3600L;

    private final UserRepository userRepository;
    private final PasswordHasherPort passwordHasherPort;
    private final TokenProviderPort tokenProviderPort;

    public LoginResult execute(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordHasherPort.matches(command.rawPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        String token = tokenProviderPort.generateToken(user);
        return new LoginResult(token, TOKEN_TTL_SECONDS);
    }
}
