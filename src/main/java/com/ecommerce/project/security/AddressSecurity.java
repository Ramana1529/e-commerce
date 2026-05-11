package com.ecommerce.project.security;

import com.ecommerce.project.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("addressSecurity")
@RequiredArgsConstructor
public class AddressSecurity {
    private final AddressRepository addressRepository;

    public boolean isOwner(Long addressId, Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails user)) {
            return false;
        }
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) return true;

        return addressRepository.findById(addressId)
                .map(address -> address.getUser().getId().equals(user.getUserId()))
                .orElse(false);
    }
}
