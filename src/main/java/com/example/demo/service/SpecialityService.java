package com.example.demo.service;

import com.example.demo.entity.Speciality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpecialityService {
    //CREATE
    ResponseEntity<Speciality> createSpeciality(Speciality speciality);

    //READ
    ResponseEntity<List<Speciality>> getAllSpecialities();

    //READ BY ID
    ResponseEntity<Speciality> getSpecialityById(Long id);

    //UPDATE
    ResponseEntity<Speciality> updateSpeciality(Long id, Speciality speciality);

    //DELETE
    ResponseEntity<HttpStatus> deleteSpeciality(Long id);

    //READ PAGEABLE
    Page<Speciality> findPaginated(Pageable pageable, String keyword);

}
