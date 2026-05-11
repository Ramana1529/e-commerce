package com.ecommerce.project.service;
import com.ecommerce.project.dto.request.AddressRequestDTO;
import com.ecommerce.project.dto.response.AddressResponseDTO;
import com.ecommerce.project.entity.Address;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.exception.ResourceNotFoundException;
import com.ecommerce.project.mapper.AddressMapper;
import com.ecommerce.project.repository.AddressRepository;
import com.ecommerce.project.repository.UserRepository;
import com.ecommerce.project.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private  User getCurrentUser(){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    @Transactional
    public AddressResponseDTO createAddress(AddressRequestDTO requestDTO) {
        User user = getCurrentUser();
        Address address = AddressMapper.toEntity(requestDTO,user);

        Address saved = addressRepository.save(address);

        return AddressMapper.toDto(saved);
    }
    public List<AddressResponseDTO> getAllAddresses(){
        List<Address> address = addressRepository.findAll();
        return address.stream()
                .map(AddressMapper::toDto)
                .toList();
    }

    public List<AddressResponseDTO> getUserAddresses() {
        User user = getCurrentUser();
        return addressRepository.findByUserId(user.getId())
                .stream()
                .map(AddressMapper::toDto)
                .toList();
    }

    public AddressResponseDTO getAddressById(Long addressId) {
        User user = getCurrentUser();

        Address address = addressRepository
                .findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        return AddressMapper.toDto(address);
    }

    public void deleteAddress(Long addressId) {
        User user = getCurrentUser();

        Address address = addressRepository
                .findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        addressRepository.delete(address);
    }
    @Transactional
    public AddressResponseDTO updateAddress(AddressRequestDTO dto,Long addressId){
        User user = getCurrentUser();

        Address address = addressRepository
                .findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPincode(dto.getPincode());
        address.setCountry(dto.getCountry());
        address.setContactNumber(dto.getContactNumber());

        return AddressMapper.toDto(addressRepository.save(address));
    }
}
