package com.example.demo.controller;

import com.example.demo.entity.PermanentAddress;
import com.example.demo.service.PermanentAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/permanentAddress")
public class PermanentAddressController {
    private final PermanentAddressService permanentAddressService;

    public PermanentAddressController(PermanentAddressService permanentAddressService) {
        this.permanentAddressService = permanentAddressService;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<PermanentAddress> createPermanentAddress(@RequestBody PermanentAddress permanentAddress) {
        return permanentAddressService.createPermanentAddress(permanentAddress);
    }

    //READ
    @GetMapping
    public ResponseEntity<List<PermanentAddress>> getAllPermanentAddresses() {
        return permanentAddressService.getAllPermanentAddresses();
    }

    //READ BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<PermanentAddress> getPermanentAddressById(@PathVariable Long id) {
        return permanentAddressService.getPermanentAddressById(id);
    }

    //UPDATE
    @PutMapping(value = "/{id}")
    public ResponseEntity<PermanentAddress> updatePermanentAddress(@PathVariable Long id, @RequestBody PermanentAddress permanentAddress) {
        return permanentAddressService.updatePermanentAddress(id, permanentAddress);
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deletePermanentAddress(@PathVariable Long id) {
        return permanentAddressService.deletePermanentAddress(id);
    }
}
