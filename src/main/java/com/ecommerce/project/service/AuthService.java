package com.ecommerce.project.service;

import com.ecommerce.project.dto.request.LoginRequestDTO;
import com.ecommerce.project.dto.request.RegisterRequestDTO;
import com.ecommerce.project.dto.response.AuthResponseDTO;
import com.ecommerce.project.dto.response.UserResponseDTO;
import com.ecommerce.project.entity.Role;
import com.ecommerce.project.entity.RoleName;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.exception.BadRequestException;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.mapper.UserMapper;
import com.ecommerce.project.repository.RoleRepository;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.security.CustomUserDetails;
import com.ecommerce.project.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    public UserResponseDTO register(RegisterRequestDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new BadRequestException("Phone number already exists");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role role = roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.setRoles(Set.of(role));

        User savedUser = userRepository.save(user);

        return userMapper.toDTO(savedUser);
    }

    public AuthResponseDTO login(LoginRequestDTO request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails);

        return AuthResponseDTO.builder()
                .userId(userDetails.getUserId())
                .token(token)
                .type("Bearer")
                .email(userDetails.getUsername())
                .roles(
                userDetails.getAuthorities()
                        .stream()
                        .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                        .collect(Collectors.toSet())
        )
                .expiresIn(3600L)
                .build();
    }
}
