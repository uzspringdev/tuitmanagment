package com.example.demo.service;

import com.example.demo.entity.CurrentAddress;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CurrentAddressService {
    //CREATE
    ResponseEntity<CurrentAddress> createCurrentAddress(CurrentAddress currentAddress);

    //READ
    ResponseEntity<List<CurrentAddress>> getAllCurrentAddresses();

    //READ BY ID
    ResponseEntity<CurrentAddress> getCurrentAddressById(Long id);

    //UPDATE
    ResponseEntity<CurrentAddress> updateCurrentAddress(Long id, CurrentAddress currentAddress);

    //DELETE
    ResponseEntity<HttpStatus> deleteCurrentAddress(Long id);
}
