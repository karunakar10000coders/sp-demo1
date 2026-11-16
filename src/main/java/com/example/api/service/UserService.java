package com.example.api.service;

import com.example.api.exception.ResourceNotFoundException;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(Boolean active) {
        if (active != null) {
            return userRepository.findAll().stream()
                    .filter(u -> u.isActive() == active)
                    .collect(Collectors.toList());
        }
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        });
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User existingUser = getUserById(id);
        
        // If email is changing, verify unique email
        if (!existingUser.getEmail().equalsIgnoreCase(userDetails.getEmail())) {
            userRepository.findByEmail(userDetails.getEmail()).ifPresent(u -> {
                throw new IllegalArgumentException("User with email " + userDetails.getEmail() + " already exists");
            });
        }

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setRole(userDetails.getRole());
        existingUser.setActive(userDetails.isActive());
        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
