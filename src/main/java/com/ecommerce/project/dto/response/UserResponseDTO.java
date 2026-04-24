package com.ecommerce.project.dto.response;


import com.ecommerce.project.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String contactNumber;
    private Role role;
    private LocalDateTime createdAt;

}