package com.cesde.proyecto_integrador.controller.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cesde.proyecto_integrador.auth.JwtUtil;
import com.cesde.proyecto_integrador.dto.auth.UserLoginDTO;
import com.cesde.proyecto_integrador.model.User;
import com.cesde.proyecto_integrador.service.auth.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO loginUser) {
        User user = authService.login(loginUser);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);        
        response.put("username", user.getEmail());
        response.put("role", user.getRole().toString());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return ResponseEntity.ok()
                .headers(headers)
                .body(response);
    } 
}