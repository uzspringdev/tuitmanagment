package com.example.demo.controller;

import com.example.demo.entity.Passport;
import com.example.demo.service.PassportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/passport")
public class PassportController {

    private final PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Passport> createPassport(@RequestBody Passport passport) {
        return passportService.createPassport(passport);
    }

    //READ
    @GetMapping
    public ResponseEntity<List<Passport>> getAllPassports() {
        return passportService.getAllPassports();
    }

    //READ BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Passport> getPassportById(@PathVariable Long id) {
        return passportService.getPassportById(id);
    }

    //UPDATE
    @PutMapping(value = "/{id}")
    public ResponseEntity<Passport> updatePassport(@PathVariable Long id, @RequestBody Passport passport) {
        return passportService.updatePassport(id, passport);
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deletePassport(@PathVariable Long id) {
        return passportService.deletePassport(id);
    }
}
