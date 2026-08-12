package com.foodorder.user.infrastructure.persistence.adapter;

import com.foodorder.user.domain.model.PageResult;
import com.foodorder.user.domain.model.Role;
import com.foodorder.user.domain.model.User;
import com.foodorder.user.domain.repository.UserRepository;
import com.foodorder.user.infrastructure.persistence.entity.UserEntity;
import com.foodorder.user.infrastructure.persistence.repository.SpringDataUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = springDataUserRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return springDataUserRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataUserRepository.existsByEmail(email);
    }

    @Override
    public PageResult<User> findAll(int page, int size) {
        Page<UserEntity> result = springDataUserRepository.findAll(PageRequest.of(page, size));
        List<User> content = result.getContent().stream().map(this::toDomain).toList();
        return new PageResult<>(content, page, size, result.getTotalElements());
    }

    private UserEntity toEntity(User user) {
        return new UserEntity(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPasswordHash(), user.getRole().name(), user.getCreatedAt(), user.getUpdatedAt());
    }

    private User toDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(),
                entity.getPasswordHash(), Role.valueOf(entity.getRole()), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
