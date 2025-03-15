package com.cesde.proyecto_integrador.service.auth;

import com.cesde.proyecto_integrador.dto.auth.UserLoginDTO;
import com.cesde.proyecto_integrador.model.User;

public interface AuthService {   
    User login(UserLoginDTO loginUser);   
}
