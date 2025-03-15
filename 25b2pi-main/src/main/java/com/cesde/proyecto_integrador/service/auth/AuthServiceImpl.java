package com.cesde.proyecto_integrador.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cesde.proyecto_integrador.dto.auth.UserLoginDTO;
import com.cesde.proyecto_integrador.exception.AuthenticationException;
import com.cesde.proyecto_integrador.model.User;
import com.cesde.proyecto_integrador.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User login(UserLoginDTO loginUser) {
        User user = userRepository.findByEmail(loginUser.getEmail());
        if (user == null) {
            throw new AuthenticationException("Email not found");
        }
        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid password");
        }
        return user;
    }

}
