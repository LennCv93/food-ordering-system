package com.foodorder.user.application.usecase;

import com.foodorder.user.domain.model.PageResult;
import com.foodorder.user.domain.model.User;
import com.foodorder.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListUsersUseCase {

    private final UserRepository userRepository;

    public PageResult<User> execute(int page, int size) {
        return userRepository.findAll(page, size);
    }
}
