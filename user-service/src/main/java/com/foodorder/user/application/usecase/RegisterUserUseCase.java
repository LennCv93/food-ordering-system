package com.foodorder.user.application.usecase;

import com.foodorder.user.application.dto.RegisterUserCommand;
import com.foodorder.user.application.port.PasswordHasherPort;
import com.foodorder.user.domain.exception.EmailAlreadyRegisteredException;
import com.foodorder.user.domain.model.User;
import com.foodorder.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHasherPort passwordHasherPort;

    @Transactional
    public User execute(RegisterUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyRegisteredException(command.email());
        }
        String passwordHash = passwordHasherPort.hash(command.rawPassword());
        User user = User.createNew(command.firstName(), command.lastName(), command.email(), passwordHash);
        return userRepository.save(user);
    }
}
