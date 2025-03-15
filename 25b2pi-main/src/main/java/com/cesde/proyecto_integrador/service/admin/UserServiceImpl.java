package com.cesde.proyecto_integrador.service.admin;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cesde.proyecto_integrador.dto.admin.UserRegisterDTO;
import com.cesde.proyecto_integrador.dto.admin.UserResponseDTO;
import com.cesde.proyecto_integrador.dto.admin.UserUpdateDTO;
import com.cesde.proyecto_integrador.exception.AuthenticationException;
import com.cesde.proyecto_integrador.exception.ResourceNotFoundException;
import com.cesde.proyecto_integrador.model.User;
import com.cesde.proyecto_integrador.repository.UserRepository;

import jakarta.validation.ConstraintViolation;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private jakarta.validation.Validator validator;

    @Override
    public UserResponseDTO register(UserRegisterDTO registerUser) {

        Set<ConstraintViolation<UserRegisterDTO>> violations = validator.validate(registerUser);
        if (!violations.isEmpty()) {
            throw new jakarta.validation.ConstraintViolationException(violations);
        }

        if (!registerUser.getPassword().equals(registerUser.getPassword_confirmation())) {
            throw new AuthenticationException("Password and confirmation do not match");
        }

        if (userRepository.findByEmail(registerUser.getEmail()) != null) {
            throw new AuthenticationException("Email already registered");
        }

        User newUser = new User();
        newUser.setEmail(registerUser.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        newUser.setRole(registerUser.getRole());
        User savedUser = userRepository.save(newUser);
        UserResponseDTO response = new UserResponseDTO();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        return response;
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = findById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        User user = findById(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public User update(Long id, UserUpdateDTO userUpdateDTO) {
        User existingUser = findById(id);
        existingUser.setEmail(userUpdateDTO.getEmail());
        existingUser.setRole(userUpdateDTO.getRole());
        return userRepository.save(existingUser);
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByRole(User.Role role) {
        return userRepository.findByRole(role);
    }

}