package com.ecommerce.project.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class AuthResponseDTO {
    private String token;
    private String type;
    private Long userId;
    private String email;
    private Set<String> roles;
    private Long expiresIn;
}
