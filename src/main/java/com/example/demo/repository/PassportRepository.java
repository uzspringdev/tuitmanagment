package com.example.demo.repository;

import com.example.demo.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    Boolean existsByPassportNumber(String passportNumber);
    Boolean existsByPin(String pin);
}
