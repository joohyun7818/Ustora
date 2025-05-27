package com.example.joohyun.repository;

import com.example.joohyun.entity.Address;
import com.example.joohyun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
    Address findByUserAndIsDefaultTrue(User user);

    
} 