package com.example.demo.service;

import com.example.demo.entity.PermanentAddress;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PermanentAddressService {
    //CREATE
    ResponseEntity<PermanentAddress> createPermanentAddress(PermanentAddress permanentAddress);

    //READ
    ResponseEntity<List<PermanentAddress>> getAllPermanentAddresses();

    //READ BY ID
    ResponseEntity<PermanentAddress> getPermanentAddressById(Long id);

    //UPDATE
    ResponseEntity<PermanentAddress> updatePermanentAddress(Long id, PermanentAddress permanentAddress);

    //DELETE
    ResponseEntity<HttpStatus> deletePermanentAddress(Long id);
}
