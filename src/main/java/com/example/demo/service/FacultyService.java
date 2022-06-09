package com.example.demo.service;

import com.example.demo.entity.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FacultyService {
    //CREATE
    ResponseEntity<Faculty> createFaculty(Faculty faculty);

    //READ
    ResponseEntity<List<Faculty>> getAllFaculties();

    //READ BY ID
    ResponseEntity<Faculty> getFacultyById(Long id);

    //UPDATE
    ResponseEntity<Faculty> updateFaculty(Long id, String name);

    //DELETE
    ResponseEntity<HttpStatus> deleteFaculty(Long id);

    //READ PAGEABLE
    Page<Faculty> findPaginated(Pageable pageable, String keyword);

}
