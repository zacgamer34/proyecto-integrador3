package com.cesde.proyecto_integrador.service.admin;

import java.util.List;

import com.cesde.proyecto_integrador.dto.admin.UserRegisterDTO;
import com.cesde.proyecto_integrador.dto.admin.UserResponseDTO;
import com.cesde.proyecto_integrador.dto.admin.UserUpdateDTO;
import com.cesde.proyecto_integrador.model.User;

public interface UserService {

    // Operaciones de autenticación
    public UserResponseDTO register(UserRegisterDTO user);

    public void changePassword(Long userId, String oldPassword, String newPassword);

    public void resetPassword(Long userId, String newPassword);

    // Operaciones CRUD
    public List<User> findAll();

    public User findById(Long id);

    public User update(Long id, UserUpdateDTO userUpdateDTO);

    public void delete(Long userId);

    // Operaciones adicionales     
    public User findByEmail(String email);   

    public List<User> findByRole(User.Role role);
}
