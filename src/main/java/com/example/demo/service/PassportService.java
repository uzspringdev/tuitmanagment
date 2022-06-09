package com.example.demo.service;

import com.example.demo.entity.Passport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PassportService {
    //CRUD
    ResponseEntity<Passport> createPassport(Passport passport);

    //READ
    ResponseEntity<List<Passport>> getAllPassports();

    //READ BY ID
    ResponseEntity<Passport> getPassportById(Long id);

    //UPDATE
    ResponseEntity<Passport> updatePassport(Long id, Passport passport);

    //DELETE
    ResponseEntity<HttpStatus> deletePassport(Long id);
}
