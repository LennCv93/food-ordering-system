package com.foodorder.user.infrastructure.web.mapper;

import com.foodorder.user.domain.model.PageResult;
import com.foodorder.user.domain.model.User;
import com.foodorder.user.infrastructure.web.dto.PageResponse;
import com.foodorder.user.infrastructure.web.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }

    public PageResponse<UserResponse> toPageResponse(PageResult<User> pageResult) {
        return new PageResponse<>(
                pageResult.content().stream().map(this::toResponse).toList(),
                pageResult.page(),
                pageResult.size(),
                pageResult.totalElements(),
                pageResult.totalPages()
        );
    }
}
