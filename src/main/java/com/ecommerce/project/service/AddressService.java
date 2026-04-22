package com.ecommerce.project.service;
import com.ecommerce.project.dto.request.AddressRequestDTO;
import com.ecommerce.project.dto.response.AddressResponseDTO;
import com.ecommerce.project.entity.Address;
import com.ecommerce.project.entity.User;
import com.ecommerce.project.mapper.AddressMapper;
import com.ecommerce.project.repository.AddressRepository;
import com.ecommerce.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressResponseDTO addAddress(Long userId, AddressRequestDTO requestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = AddressMapper.toEntity(requestDTO,user);
        address.setUser(user);

        Address saved = addressRepository.save(address);

        return AddressMapper.toDto(saved);
    }

    public List<AddressResponseDTO> getUserAddresses(Long userId) {

        List<Address> addresses = addressRepository.findByUserId(userId);

        return addresses.stream()
                .map(AddressMapper::toDto)
                .toList();
    }

    public AddressResponseDTO getAddressById(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        return AddressMapper.toDto(address);
    }

    public void deleteAddress(Long addressId) {

        if (!addressRepository.existsById(addressId)) {
            throw new RuntimeException("Address not found");
        }

        addressRepository.deleteById(addressId);
    }
    public AddressResponseDTO updateAddress(AddressRequestDTO addressRequestDTO,Long addressId){
        Address address = addressRepository.findById(addressId).orElseThrow(()->new RuntimeException("address not found"));
        address.setStreet(addressRequestDTO.getStreet());
        address.setCity(addressRequestDTO.getCity());
        address.setState(addressRequestDTO.getState());
        address.setPincode(addressRequestDTO.getPincode());
        address.setCountry(addressRequestDTO.getCountry());
        address.setContactNumber(addressRequestDTO.getContactNumber());
        Address savedAddress = addressRepository.save(address);
        return AddressMapper.toDto(savedAddress);
    }
}
