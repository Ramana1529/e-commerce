package com.ecommerce.project.dto.request;

import com.ecommerce.project.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequestDTO {
    @NotNull(message = "Address is required")
    private Long addressId;
}
