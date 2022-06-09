package com.example.demo.repository;

import com.example.demo.entity.PermanentAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermanentAddressRepository extends JpaRepository<PermanentAddress, Long> {
}
