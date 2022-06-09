package com.example.demo.service.impl;

import com.example.demo.entity.CurrentAddress;
import com.example.demo.repository.CurrentAddressRepository;
import com.example.demo.service.CurrentAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrentAddressServiceImpl implements CurrentAddressService {

    private final CurrentAddressRepository currentAddressRepository;

    public CurrentAddressServiceImpl(CurrentAddressRepository currentAddressRepository) {
        this.currentAddressRepository = currentAddressRepository;
    }


    @Override
    public ResponseEntity<CurrentAddress> createCurrentAddress(CurrentAddress currentAddress) {
        try {
            CurrentAddress newCurrentAddress = currentAddressRepository.save(
                    new CurrentAddress(
                            currentAddress.getRegion(),
                            currentAddress.getDistrict(),
                            currentAddress.getAddress(),
                            currentAddress.getCountRoommates(),
                            currentAddress.getCategoryRoommates(),
                            currentAddress.getStatusResidence())
            );

            return new ResponseEntity<>(newCurrentAddress, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<CurrentAddress>> getAllCurrentAddresses() {
        try {
            List<CurrentAddress> currentAddressList = currentAddressRepository.findAll();
            return new ResponseEntity<>(currentAddressList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CurrentAddress> getCurrentAddressById(Long id) {
        try {
            Optional<CurrentAddress> optionalCurrentAddressOptional = currentAddressRepository.findById(id);
            if (optionalCurrentAddressOptional.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            CurrentAddress currentAddress = optionalCurrentAddressOptional.get();

            return new ResponseEntity<>(currentAddress, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CurrentAddress> updateCurrentAddress(Long id, CurrentAddress currentAddress) {
        try {
            Optional<CurrentAddress> optionalCurrentAddressOptional = currentAddressRepository.findById(id);
            if (optionalCurrentAddressOptional.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            CurrentAddress editedCurrentAddress = optionalCurrentAddressOptional.get();
            editedCurrentAddress.setRegion(currentAddress.getRegion());
            editedCurrentAddress.setDistrict(currentAddress.getDistrict());
            editedCurrentAddress.setAddress(currentAddress.getAddress());
            editedCurrentAddress.setCountRoommates(currentAddress.getCountRoommates());
            editedCurrentAddress.setCategoryRoommates(currentAddress.getCategoryRoommates());
            editedCurrentAddress.setStatusResidence(currentAddress.getStatusResidence());
            CurrentAddress updatedCurrentAddress = currentAddressRepository.save(editedCurrentAddress);
            return new ResponseEntity<>(updatedCurrentAddress, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteCurrentAddress(Long id) {
        try {

            if (currentAddressRepository.existsById(id)) {
                currentAddressRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
