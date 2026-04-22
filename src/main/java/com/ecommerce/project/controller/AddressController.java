package com.ecommerce.project.controller;
import com.ecommerce.project.dto.request.AddressRequestDTO;
import com.ecommerce.project.dto.response.AddressResponseDTO;
import com.ecommerce.project.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/{userId}")
    public AddressResponseDTO addAddress(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequestDTO requestDTO
    ) {
        return addressService.addAddress(userId, requestDTO);
    }

    @GetMapping("/user/{userId}")
    public List<AddressResponseDTO> getUserAddresses(@PathVariable Long userId) {
        return addressService.getUserAddresses(userId);
    }

    @GetMapping("/{addressId}")
    public AddressResponseDTO getAddressById(@PathVariable Long addressId) {
        return addressService.getAddressById(addressId);
    }

    @DeleteMapping("/{addressId}")
    public String deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return "Address deleted successfully";
    }
    @PutMapping("/{addressId}")
    public AddressResponseDTO updateAddress(@RequestBody AddressRequestDTO addressRequestDTO,@PathVariable Long addressId){
        return addressService.updateAddress(addressRequestDTO,addressId);
    }
}
