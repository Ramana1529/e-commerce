package com.ecommerce.project.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDTO {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private String contactNumber;
}
