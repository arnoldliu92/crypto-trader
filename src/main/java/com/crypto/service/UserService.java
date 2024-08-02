package com.crypto.service;

import com.crypto.data.UserRepository;
import com.crypto.entity.User;
import com.crypto.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository
                .findIdByEmail(email)
                .orElseThrow(() -> { throw new UserNotFoundException(email); });
    }
}