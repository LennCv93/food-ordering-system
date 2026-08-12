package com.foodorder.user.application.port;

import com.foodorder.user.domain.model.User;

public interface TokenProviderPort {

    String generateToken(User user);
}
