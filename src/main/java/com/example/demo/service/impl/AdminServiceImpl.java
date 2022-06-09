package com.example.demo.service.impl;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.AdminService;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Admin> createAdmin(Admin admin) {
        try {
            if (!checkPassportLength(admin.getPassword())) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            if (existsByUsername(admin.getUsername())) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            Admin newAdmin = adminRepository.save(new Admin(admin.getUsername(), passwordEncoder.encode(admin.getPassword()), admin.getRoles()));
            return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Admin>> getAllAdmins() {
        try {
            List<Admin> adminList = adminRepository.findAll();
            return new ResponseEntity<>(adminList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Admin> getAdminById(Long id) {
        try {
            Optional<Admin> optionalAdmin = adminRepository.findById(id);
            if (optionalAdmin.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Admin admin = optionalAdmin.get();
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Admin> updateAdmin(Long id, Admin admin) {
        try {
            Optional<Admin> optionalAdmin = adminRepository.findById(id);
            if (optionalAdmin.isPresent()) {
                Admin editedAdmin = optionalAdmin.get();
                editedAdmin.setUsername(admin.getUsername());
                editedAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
                editedAdmin.setRoles(admin.getRoles());
                return new ResponseEntity<>(adminRepository.save(editedAdmin), HttpStatus.OK);
            } else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAdmin(Long id) {
        try {
            if (adminRepository.existsById(id)) {
                adminRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminRepository.getAdminByUsername(username);
    }

    @Override
    public Boolean checkPassportLength(String passport) {
        return passport.length() >= 8;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Override
    public Page<Admin> findPaginated(Pageable pageable, String keyword) {
        final List<Admin> admins;
        if (keyword.equals("")) {
            admins = adminRepository.findAll(Sort.by("username"));
        } else {
            admins = adminRepository.findAllByKeyword(keyword);
        }
        Page<Admin> adminPage;
        try {
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = pageSize * currentPage;

            List<Admin> list;
            if (startItem > admins.size()) {
                list = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, admins.size());
                list = admins.subList(startItem, toIndex);
            }
            adminPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), list.size());
        } catch (Exception e) {
            adminPage = null;
        }

        return adminPage;
    }

}
