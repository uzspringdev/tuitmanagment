package com.example.demo.service.impl;

import com.example.demo.entity.Passport;
import com.example.demo.repository.PassportRepository;
import com.example.demo.service.PassportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;

    public PassportServiceImpl(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    @Override
    public ResponseEntity<Passport> createPassport(Passport passport) {
        try {
            Passport newPassport = passportRepository.save(
                    new Passport(
                            passport.getFirstName(),
                            passport.getLastName(),
                            passport.getPatronymic(),
                            passport.getCitizenship(),
                            passport.getNationality(),
                            passport.getGender(),
                            passport.getDateOfBirth(),
                            passport.getPassportNumber(),
                            passport.getPin()
                    )
            );
            return new ResponseEntity<>(newPassport, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Passport>> getAllPassports() {
        try {
            List<Passport> passportList = passportRepository.findAll();
            return new ResponseEntity<>(passportList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Passport> getPassportById(Long id) {
        try {
            Optional<Passport> optionalPassport = passportRepository.findById(id);
            if (!optionalPassport.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Passport passport = optionalPassport.get();
            return new ResponseEntity<>(passport, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Passport> updatePassport(Long id, Passport passport) {
        try {
            Optional<Passport> optionalPassport = passportRepository.findById(id);
            if (!optionalPassport.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Passport editedPassport = optionalPassport.get();
            //firstName, lastName, patronymic, citizenship, nationality, gender, dateOfBirth, passportNumber, pin
            editedPassport.setFirstName(passport.getFirstName());
            editedPassport.setLastName(passport.getLastName());
            editedPassport.setPatronymic(passport.getPatronymic());
            editedPassport.setCitizenship(passport.getCitizenship());
            editedPassport.setNationality(passport.getNationality());
            editedPassport.setGender(passport.getGender());
            editedPassport.setDateOfBirth(passport.getDateOfBirth());
            editedPassport.setPassportNumber(passport.getPassportNumber());
            editedPassport.setPin(passport.getPin());
            Passport updatedPassport = passportRepository.save(editedPassport);
            return new ResponseEntity<>(updatedPassport, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deletePassport(Long id) {
        try {
            if (passportRepository.existsById(id)) {
                passportRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
