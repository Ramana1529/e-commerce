package com.ecommerce.project.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponseDTO {
    private String token;
    private String type;
    private Long userId;
    private String email;
    private String role;
    private Long expiresIn;
}
