package com.example.demo.repository;

import com.example.demo.entity.CurrentAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentAddressRepository extends JpaRepository<CurrentAddress, Long> {
}
