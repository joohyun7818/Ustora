package com.example.joohyun.service;

import com.example.joohyun.dto.AddressDTO;
import com.example.joohyun.entity.Address;
import com.example.joohyun.entity.User;
import com.example.joohyun.repository.AddressRepository;
import com.example.joohyun.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public void saveAddress(Address address) {
        if (address.isDefault()) {
            List<Address> userAddresses = addressRepository.findByUser(address.getUser());
            userAddresses.forEach(addr -> addr.setDefault(false));
        }
        addressRepository.save(address);
    }

    public void setDefaultAddress(Long addressId, String userEmail) {
        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // 주소가 해당 사용자의 것인지 확인
        if (!address.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Address does not belong to the user");
        }

        // 기존 기본 주소 해제
        List<Address> userAddresses = addressRepository.findByUser(user);
        userAddresses.forEach(addr -> {
            if (addr.isDefault()) {
                addr.setDefault(false);
                addressRepository.save(addr);
            }
        });

        // 새로운 기본 주소 설정
        address.setDefault(true);
        addressRepository.save(address);
    }

    public boolean deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        
        // 기본 배송지인 경우 삭제 불가
        if (address.isDefault()) {
            return false;
        }
        
        addressRepository.deleteById(addressId);
        return true;
    }

    public AddressDTO getDefaultAddress(String userEmail) {
        User user;
        Address address = null;
        Optional<User> oUser = userRepository.findById(userEmail);
        if (oUser.isPresent()) {
            user = oUser.get();
            address = addressRepository.findByUserAndIsDefaultTrue(user);
            if (address == null) {
                return null;
            }
        }
        return AddressDTO.fromEntity(address);
    }
} 