package com.ecommerce.project.controller;
import com.ecommerce.project.dto.request.AddressRequestDTO;
import com.ecommerce.project.dto.response.AddressResponseDTO;
import com.ecommerce.project.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public AddressResponseDTO createAddress(
            @Valid @RequestBody AddressRequestDTO requestDTO
    ) {
        return addressService.createAddress(requestDTO);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<AddressResponseDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public List<AddressResponseDTO> getUserAddresses() {
        return addressService.getUserAddresses();
    }

    @GetMapping("/{addressId}")
    @PreAuthorize("@addressSecurity.isOwner(#addressId, authentication)")
    public AddressResponseDTO getAddressById(@PathVariable Long addressId) {
        return addressService.getAddressById(addressId);
    }

    @DeleteMapping("/{addressId}")
    @PreAuthorize("@addressSecurity.isOwner(#addressId, authentication)")
    public String deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return "Address deleted successfully";
    }
    @PutMapping("/{addressId}")
    @PreAuthorize("@addressSecurity.isOwner(#addressId, authentication)")
    public AddressResponseDTO updateAddress(@RequestBody AddressRequestDTO addressRequestDTO,@PathVariable Long addressId){
        return addressService.updateAddress(addressRequestDTO,addressId);
    }
}
