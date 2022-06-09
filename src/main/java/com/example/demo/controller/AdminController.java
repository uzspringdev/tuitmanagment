package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Role;
import com.example.demo.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //CREATE
    @PostMapping
    public String createAdmin(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "adminRole") Optional<String> adr,
            @RequestParam(value = "userRole") Optional<String> usr
    ) {
        Set<Role> roles = new HashSet<>();
        Role adminRole = new Role();
        Role userRole = new Role();
        if (adr.isPresent()) {
            adminRole.setName(adr.get());
            roles.add(adminRole);

        }

        if (usr.isPresent()) {
            userRole.setName(usr.get());
            roles.add(userRole);
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setRoles(roles);

        adminService.createAdmin(admin);
        return "redirect:/admin/list-admin";
    }

    //CREATE ADMIN PAGE
    @GetMapping(value = "/add-admin")
    public String createAdminPage() {

        return "/admin/add-admin";
    }


    //READ
    @GetMapping(value = "/list-admin")
    public String getAllAdmins(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               @RequestParam("keyword") Optional<String> keyword) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String searchKeyword = keyword.orElse("");
        Page<Admin> adminPage = adminService.findPaginated(PageRequest.of(currentPage - 1, pageSize), searchKeyword);
        model.addAttribute("adminPage", adminPage);
        model.addAttribute("keyword", searchKeyword);
        List<Integer> pageNumbers;
        int totalPages = adminPage.getTotalPages();
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        return "/admin/list-admin";
    }


    //READ BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    //UPDATE
    @PostMapping(value = "/update/{id}")
    public String updateAdmin(@PathVariable Long id, @RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password,
                              @RequestParam(value = "adminRole") Optional<String> adr,
                              @RequestParam(value = "userRole") Optional<String> usr) {
        Set<Role> roles = new HashSet<>();
        Role adminRole = new Role();
        Role userRole = new Role();
        if (adr.isPresent()) {
            adminRole.setName(adr.get());
            roles.add(adminRole);

        }

        if (usr.isPresent()) {
            userRole.setName(usr.get());
            roles.add(userRole);
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        admin.setRoles(roles);
        adminService.updateAdmin(id, admin);
        return "redirect:/admin/list-admin";
    }

    //UPDATE ADMIN PAGE
    @GetMapping(value = "/update/{id}")
    public String updateAdminPage(@PathVariable Long id, Model model) {
        Admin admin = adminService.getAdminById(id).getBody();
        model.addAttribute("admin", admin);
        return "/admin/update-admin";
    }

    //DELETE
    @GetMapping(value = "/delete/{id}")
    public String deleteAdmin(@PathVariable Long id, @RequestParam Optional<String> keyword) {
        String searchKeyword = keyword.orElse("");
        adminService.deleteAdmin(id);
        if (searchKeyword.equals("")) {
            return "redirect:/admin/list-admin";
        } else return "redirect:/admin/list-admin?keyword=" + searchKeyword;

    }
}
