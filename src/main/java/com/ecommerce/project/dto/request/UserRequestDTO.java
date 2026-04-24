package com.ecommerce.project.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email
    private String email;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 6)
    private String password;

    @NotBlank(message = "Contact number cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$")
    private String contactNumber;
}