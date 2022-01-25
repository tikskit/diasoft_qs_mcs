package ru.vluzhnykh.quickstart.security.services;

import org.springframework.stereotype.Service;
import ru.vluzhnykh.quickstart.security.domain.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Optional<User> getUser(String username) {
        if (username.equals("username")) {
            return Optional.of(new User("username", "password", "ADMIN"));
        } else {
            return Optional.empty();
        }
    }
}
