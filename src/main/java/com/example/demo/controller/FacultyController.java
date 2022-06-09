package com.example.demo.controller;

import com.example.demo.entity.Faculty;
import com.example.demo.service.FacultyExcelService;
import com.example.demo.service.FacultyService;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping(value = "/faculty")
public class FacultyController {

    private final FacultyService facultyService;
    private final FacultyExcelService facultyExcelService;

    public FacultyController(FacultyService facultyService, FacultyExcelService facultyExcelService) {
        this.facultyService = facultyService;
        this.facultyExcelService = facultyExcelService;
    }

    //CREATE
    @PostMapping
    public String createFaculty(@RequestParam(name = "name") String name) {
        Faculty faculty = new Faculty();
        faculty.setName(name);
        facultyService.createFaculty(faculty);
        return "redirect:/faculty/list-faculty";
    }


    //READ BY PAGINATION
    @GetMapping(value = "/list-faculty")
    public String getAllFaculties(Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  @RequestParam("keyword") Optional<String> keyword) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String searchKeyword = keyword.orElse("");

        Page<Faculty> facultyPage = facultyService.findPaginated(PageRequest.of(currentPage - 1, pageSize), searchKeyword);


        model.addAttribute("facultyPage", facultyPage);
        model.addAttribute("keyword", searchKeyword);
        int totalPages = facultyPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "/faculty/list-faculty";
    }

    //ADD FACULTY PAGE
    @GetMapping(value = "/add-faculty")
    public String addFacultyPage() {
        return "/faculty/add-faculty";
    }

    //READ BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    //UPDATE
    @PostMapping(value = "/update/{id}")
    public String updateFaculty(@PathVariable Long id, @RequestParam String facultyName, Model model) {
        facultyService.updateFaculty(id, facultyName);
        return "redirect:/faculty/list-faculty";
    }

    //EDIT FACULTY
    @GetMapping(value = "/update/{id}")
    public String updateFacultyPage(@PathVariable Long id, Model model) {
        Faculty faculty = facultyService.getFacultyById(id).getBody();
        model.addAttribute("faculty", faculty);
        return "/faculty/update-faculty";
    }

    //DELETE
    @GetMapping(value = "/delete/{id}")
    public String deleteFaculty(@PathVariable Long id, Model model, @RequestParam("keyword") Optional<String> keyword) {
        facultyService.deleteFaculty(id);
        String searchKeyword = keyword.orElse("");
        if (searchKeyword.equals("")) {
            return "redirect:/faculty/list-faculty";
        }
        return "redirect:/faculty/list-faculty?keyword=" + searchKeyword;
    }

    //GET ALL FACULTIES DATA AS EXCEL
    @GetMapping(value = "/download/excel")
    public ResponseEntity<Resource> getFile(@RequestParam("keyword") Optional<String> keyword) {
        String searchKeyword = keyword.orElse("");
        String fileName = "faculties.xls";
        InputStreamResource file = new InputStreamResource(facultyExcelService.load(searchKeyword));
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

}
