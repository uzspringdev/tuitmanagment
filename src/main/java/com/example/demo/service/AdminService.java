package com.example.demo.service;


import com.example.demo.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    //CREATE
    ResponseEntity<Admin> createAdmin(Admin admin);

    //READ
    ResponseEntity<List<Admin>> getAllAdmins();

    //READ BY ID
    ResponseEntity<Admin> getAdminById(Long id);

    //UPDATE
    ResponseEntity<Admin> updateAdmin(Long id, Admin admin);

    //DELETE
    ResponseEntity<HttpStatus> deleteAdmin(Long id);

    //GET ADMIN BY USERNAME
    Admin getAdminByUsername(String username);

    //CHECK PASSPORT LENGTH
    Boolean checkPassportLength(String passport);

    //CHECK ADMIN USERNAME
    Boolean existsByUsername(String username);

    //READ PAGEABLE
    Page<Admin> findPaginated(Pageable pageable, String keyword);

}
