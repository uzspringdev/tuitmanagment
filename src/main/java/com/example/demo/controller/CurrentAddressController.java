package com.example.demo.controller;

import com.example.demo.entity.CurrentAddress;
import com.example.demo.service.CurrentAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/currentAddress")
public class CurrentAddressController {

    private final CurrentAddressService currentAddressService;

    public CurrentAddressController(CurrentAddressService currentAddressService) {
        this.currentAddressService = currentAddressService;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<CurrentAddress> createCurrentAddress(@RequestBody CurrentAddress currentAddress) {
        return currentAddressService.createCurrentAddress(currentAddress);
    }

    //READ
    @GetMapping
    public ResponseEntity<List<CurrentAddress>> getAllCurrentAddresses() {
        return currentAddressService.getAllCurrentAddresses();
    }

    //READ BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<CurrentAddress> getCurrentAddressById(@PathVariable Long id) {
        return currentAddressService.getCurrentAddressById(id);
    }

    //UPDATE
    @PutMapping(value = "/{id}")
    public ResponseEntity<CurrentAddress> updateCurrentAddress(@PathVariable Long id, @RequestBody CurrentAddress currentAddress) {
        return currentAddressService.updateCurrentAddress(id, currentAddress);
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteCurrentAddress(@PathVariable Long id) {
        return currentAddressService.deleteCurrentAddress(id);
    }

}
