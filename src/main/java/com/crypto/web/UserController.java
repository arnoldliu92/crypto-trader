package com.crypto.web;

import com.crypto.entity.User;
import com.crypto.exception.UserNotFoundException;
import com.crypto.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * Retrieves the wallet balance for a specific user.
     *
     * @param   email the email of the user
     * @return  User object
     */
    @GetMapping
    public ResponseEntity<User> getUserByEmail(@RequestHeader String email) {
        logger.info("Getting user with email {}...", email);
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException exception) {
            logger.error("User with email {} NOT found...", email);
            throw exception;
        }
    }
}