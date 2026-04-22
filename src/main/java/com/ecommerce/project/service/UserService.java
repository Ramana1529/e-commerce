package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.RegisterRequestDTO;
import com.ecommerce.project.dto.request.UserRequestDTO;
import com.ecommerce.project.dto.response.UserResponseDTO;
import com.ecommerce.project.entity.RoleOfUser;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.mapper.UserMapper;
import com.ecommerce.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO){
        if(userRepository.existsByEmail(registerRequestDTO.getEmail())){
            throw new RuntimeException("Email already exsist");
        }
        User user = userMapper.toEntity(registerRequestDTO);
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));

        user.setRole(RoleOfUser.USER); // optional default
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
    public UserResponseDTO getUserById(Long id){
        User user= userRepository.findById(id).orElseThrow(()->new RuntimeException("User Not Found"));
        return userMapper.toDTO(user);
    }
    public List<UserResponseDTO> getAllUsers() {

       List<User> user= userRepository.findAll();
        return user.stream()
                .map(userMapper::toDTO)
                .toList();
    }
    public String deleteUserById(Long id){
        if(!userRepository.existsById(id)){
            throw new RuntimeException("user not exsist by id");
        }
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
    public UserResponseDTO updateUser(RegisterRequestDTO registerRequestDTO,Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
        user.setName(registerRequestDTO.getName());
        if(!user.getEmail().equals(registerRequestDTO.getEmail()) &&
                userRepository.existsByEmail(registerRequestDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        user.setContactNumber(registerRequestDTO.getContactNumber());
        if(registerRequestDTO.getPassword()!=null && !registerRequestDTO.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
}
