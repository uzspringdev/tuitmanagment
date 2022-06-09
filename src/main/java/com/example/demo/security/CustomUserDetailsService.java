package com.example.demo.security;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Admin> optionalAdmin = adminRepository.getOptionalAdminByUsername(username);
        return optionalAdmin.map(CustomUserDetails::new).orElseGet(() -> new CustomUserDetails(new Admin()));
    }
}
