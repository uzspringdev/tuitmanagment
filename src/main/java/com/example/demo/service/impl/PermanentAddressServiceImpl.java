package com.example.demo.service.impl;

import com.example.demo.entity.PermanentAddress;
import com.example.demo.repository.PermanentAddressRepository;
import com.example.demo.service.PermanentAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermanentAddressServiceImpl implements PermanentAddressService {

    private final PermanentAddressRepository permanentAddressRepository;

    public PermanentAddressServiceImpl(PermanentAddressRepository permanentAddressRepository) {
        this.permanentAddressRepository = permanentAddressRepository;
    }

    @Override
    public ResponseEntity<PermanentAddress> createPermanentAddress(PermanentAddress permanentAddress) {
        try {
            PermanentAddress newAddress = permanentAddressRepository.save(
                    new PermanentAddress(
                            permanentAddress.getCountry(),
                            permanentAddress.getRegion(),
                            permanentAddress.getDistrict(),
                            permanentAddress.getAddress()));
            return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<PermanentAddress>> getAllPermanentAddresses() {
        try {
            List<PermanentAddress> addressList = permanentAddressRepository.findAll();
            return new ResponseEntity<>(addressList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PermanentAddress> getPermanentAddressById(Long id) {
        try {
            Optional<PermanentAddress> optionalPermanentAddress = permanentAddressRepository.findById(id);
            if (optionalPermanentAddress.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            PermanentAddress permanentAddress = optionalPermanentAddress.get();
            return new ResponseEntity<>(permanentAddress, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PermanentAddress> updatePermanentAddress(Long id, PermanentAddress permanentAddress) {
        try {
            Optional<PermanentAddress> optionalPermanentAddress = permanentAddressRepository.findById(id);
            if (optionalPermanentAddress.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            PermanentAddress editedPermanentAddress = optionalPermanentAddress.get();
            editedPermanentAddress.setCountry(permanentAddress.getCountry());
            editedPermanentAddress.setRegion(permanentAddress.getRegion());
            editedPermanentAddress.setDistrict(permanentAddress.getDistrict());
            editedPermanentAddress.setAddress(permanentAddress.getAddress());
            PermanentAddress updatedPermanentAddress = permanentAddressRepository.save(editedPermanentAddress);
            return new ResponseEntity<>(updatedPermanentAddress, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deletePermanentAddress(Long id) {
        try {
            if (permanentAddressRepository.existsById(id)) {
                permanentAddressRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
