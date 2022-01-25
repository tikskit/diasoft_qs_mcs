package ru.vluzhnykh.quickstart.security.services;

import ru.vluzhnykh.quickstart.security.domain.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String username);
}
