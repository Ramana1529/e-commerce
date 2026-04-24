package com.ecommerce.project.mapper;

import com.ecommerce.project.dto.request.AddressRequestDTO;
import com.ecommerce.project.dto.response.AddressResponseDTO;
import com.ecommerce.project.entity.Address;
import com.ecommerce.project.entity.User;

public class AddressMapper {

    public static Address toEntity(AddressRequestDTO dto, User user){
        return Address.builder()
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .pincode(dto.getPincode())
                .country(dto.getCountry())
                .contactNumber(dto.getContactNumber())
                .user(user)
                .build();
    }

    public static AddressResponseDTO toDto(Address address){
        if (address == null) return null;

        String fullAddress = String.join(", ",
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPincode(),
                address.getCountry()
        );

        return AddressResponseDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .pincode(address.getPincode())
                .contactNumber(address.getContactNumber())
                .fullAddress(fullAddress)
                .build();
    }
}