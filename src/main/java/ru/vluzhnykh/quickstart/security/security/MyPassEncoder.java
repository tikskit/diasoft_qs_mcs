package ru.vluzhnykh.quickstart.security.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPassEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        // Не будем сейчас возиться с зашифрованным паролем, храним его в открытом виде
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
