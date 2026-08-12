package com.foodorder.user.application.usecase;

import com.foodorder.user.domain.exception.UserNotFoundException;
import com.foodorder.user.domain.model.User;
import com.foodorder.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserProfileUseCase {

    private final UserRepository userRepository;

    public User execute(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
