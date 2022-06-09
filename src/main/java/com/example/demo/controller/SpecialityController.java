package com.example.demo.controller;

import com.example.demo.entity.Speciality;
import com.example.demo.service.SpecialityExcelService;
import com.example.demo.service.SpecialityService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/speciality")
public class SpecialityController {
    private final SpecialityService specialityService;
    private final SpecialityExcelService specialityExcelService;

    public SpecialityController(SpecialityService specialityService, SpecialityExcelService specialityExcelService) {
        this.specialityService = specialityService;
        this.specialityExcelService = specialityExcelService;
    }

    //CREATE
    @PostMapping
    public String createFaculty(@RequestParam(name = "code") Long code, @RequestParam(name = "name") String name, @RequestParam(name = "typeOfEducation") String typeOfEducation) {
        Speciality speciality = new Speciality();
        speciality.setCode(code);
        speciality.setName(name);
        speciality.setTypeOfEducation(typeOfEducation);
        specialityService.createSpeciality(speciality);
        return "redirect:/speciality/list-speciality";
    }

    //CREATE FACULTY PAGE
    @GetMapping(value = "/add-speciality")
    public String addSpecialityPage() {
        return "/speciality/add-speciality";
    }

    //READ
    @GetMapping(value = "/list-speciality")
    public String getAllSpecialities(Model model,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     @RequestParam("keyword") Optional<String> keyword) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String searchKeyword = keyword.orElse("");

        Page<Speciality> specialityPage = specialityService.findPaginated(PageRequest.of(currentPage - 1, pageSize), searchKeyword);
        model.addAttribute("specialityPage", specialityPage);
        model.addAttribute("keyword", searchKeyword);
        int totalPages = specialityPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "/speciality/list-speciality";
    }


    //READ BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Speciality> getSpecialityById(@PathVariable Long id) {
        return specialityService.getSpecialityById(id);
    }


    //UPDATE PAGE
    @PostMapping(value = "/update/{id}")
    public String updateSpeciality(@PathVariable Long id, @RequestParam Long specialityCode, @RequestParam String specialityName, @RequestParam String typeOfEducation) {
        specialityService.updateSpeciality(id, new Speciality(specialityCode, specialityName, typeOfEducation));
        return "redirect:/speciality/list-speciality";
    }

    //UPDATE
    @GetMapping(value = "/update/{id}")
    public String updateSpecialityPage(@PathVariable Long id, Model model) {

        Speciality speciality = specialityService.getSpecialityById(id).getBody();
        model.addAttribute("speciality", speciality);
        return "/speciality/update-speciality";
    }

    //DELETE
    @GetMapping(value = "/delete/{id}")
    public String deleteSpeciality(@PathVariable Long id, Model model, @RequestParam("keyword") Optional<String> keyword) {
        specialityService.deleteSpeciality(id);

        String searchKeyword = keyword.orElse("");
        if (searchKeyword.equals("")) {
            return "redirect:/speciality/list-speciality";
        }
        return "redirect:/speciality/list-speciality?keyword=" + searchKeyword;
    }

    //GET ALL DATA AS EXCEL
    @GetMapping(value = "/download/excel")
    public ResponseEntity<Resource> getFile(@RequestParam Optional<String> keyword) {
        String fileName = "specialities.xls";
        String searchKeyword = keyword.orElse("");
        InputStreamResource file = new InputStreamResource(specialityExcelService.load(searchKeyword));

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }

}
