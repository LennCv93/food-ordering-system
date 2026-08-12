package com.foodorder.user.application.usecase;

import com.foodorder.user.domain.exception.UserNotFoundException;
import com.foodorder.user.domain.model.User;
import com.foodorder.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    public User execute(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
