package com.ecommerce.project.dto.response;


import com.ecommerce.project.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String contactNumber;
    private Set<String> role;
    private LocalDateTime createdAt;

}