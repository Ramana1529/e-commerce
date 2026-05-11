package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.RegisterRequestDTO;
import com.ecommerce.project.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getCurrentUser();
    String deleteUserById(Long id);
    UserResponseDTO updateUser(RegisterRequestDTO dto);
}
