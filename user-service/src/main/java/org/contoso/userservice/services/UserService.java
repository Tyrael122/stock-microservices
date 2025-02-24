package org.contoso.userservice.services;

import lombok.RequiredArgsConstructor;
import org.contoso.userservice.models.User;
import org.contoso.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public BigDecimal getBalance(UUID userId) {
        return userRepository.findById(userId)
                .map(User::getBalance)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateBalance(UUID userId, BigDecimal newBalance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setBalance(newBalance);
        userRepository.save(user);
    }
}
