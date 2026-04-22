package com.ecommerce.project.dto.response;


import com.ecommerce.project.entity.RoleOfUser;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String contactNumber;
    private RoleOfUser role;
    private LocalDateTime createdAt;

}