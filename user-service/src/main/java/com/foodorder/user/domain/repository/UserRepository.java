package com.foodorder.user.domain.repository;

import com.foodorder.user.domain.model.PageResult;
import com.foodorder.user.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    PageResult<User> findAll(int page, int size);
}
