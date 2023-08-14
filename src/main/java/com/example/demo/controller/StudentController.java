package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.payload.StudentDataTransferObject;
import com.example.demo.payload.StudentDto;
import com.example.demo.service.*;
import com.example.demo.utility.DormitoryAddressWriter;
import com.example.demo.utility.ExistenceChecker;
import com.example.demo.utility.UploadUtility;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
    private final StudentService studentService;
    private final FacultyService facultyService;
    private final SpecialityService specialityService;
    private final OrderService orderService;
    private final UploadUtility uploadUtility;
    private final StudentExcelService studentExcelService;
    private final DormitoryAddressWriter dormitoryAddressWriter;

    public StudentController(StudentService studentService, FacultyService facultyService, SpecialityService specialityService, OrderService orderService, UploadUtility uploadUtility, StudentExcelService studentExcelService, DormitoryAddressWriter dormitoryAddressWriter) {
        this.studentService = studentService;
        this.facultyService = facultyService;
        this.specialityService = specialityService;
        this.orderService = orderService;
        this.uploadUtility = uploadUtility;
        this.studentExcelService = studentExcelService;
        this.dormitoryAddressWriter = dormitoryAddressWriter;
    }

    //CREATE
    @PostMapping
    ResponseEntity<Student> createStudent(@RequestBody StudentDto studentDto) {
        return studentService.createStudent(studentDto);
    }

    //CREATE PAGE
    @GetMapping(value = "/add-student")
    String createStudentPage(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties().getBody());
        model.addAttribute("specialities", specialityService.getAllSpecialities().getBody());
        model.addAttribute("orders", orderService.getAllOrders().getBody());

        return "/student/add-student";
    }

    //CREATE STUDENT
    @PostMapping(value = "/add-student")
    String createStudent(
            @RequestParam(value = "studentPhoto") MultipartFile photo,
            @RequestParam(value = "paymentForm") String paymentForm,
            @RequestParam(value = "admissionDate") String admissionDate,
            @RequestParam(value = "socialCategory") String socialCategory,
            @RequestParam(value = "studentCategory") String studentCategory,
            @RequestParam(value = "previousEducation") String previousEducation,
            @RequestParam(value = "groupName") String groupName,
            /*permanent address data*/
            @RequestParam(value = "permanentCountry") String permanentCountry,
            @RequestParam(value = "permanentRegion") String permanentRegion,
            @RequestParam(value = "permanentDistrict") String permanentDistrict,
            @RequestParam(value = "permanentAddress") String permanentAddress,
            /*current address data*/
            @RequestParam(value = "dormitory", defaultValue = "") String dormitory,
            @RequestParam(value = "currentRegion", defaultValue = "") String currentRegion,
            @RequestParam(value = "currentDistrict", defaultValue = "") String currentDistrict,
            @RequestParam(value = "currentAddress", defaultValue = "") String currentAddress,
            @RequestParam(value = "countRoommates", defaultValue = "") Integer countRoommates,
            @RequestParam(value = "categoryRoommates", defaultValue = "") String categoryRoommates,
            @RequestParam(value = "statusResidence", defaultValue = "") String statusResidence,
            /*contact data*/
            @RequestParam(value = "email") String email,
            @RequestParam(value = "studentPhone") String studentPhone,
            @RequestParam(value = "parentPhone") String parentPhone,
            /*passport data*/
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "patronymic") String patronymic,
            @RequestParam(value = "citizenship") String citizenship,
            @RequestParam(value = "nationality") String nationality,
            @RequestParam(value = "gender") String gender,
            @RequestParam(value = "dateOfBirth") String dateOfBirth,
            @RequestParam(value = "passportNumber") String passportNumber,
            @RequestParam(value = "pin") String pin,
            /*faculty id*/
            @RequestParam("facultyId") Long facultyId,
            /*speciality id*/
            @RequestParam("specialityId") Long specialityId,
            /*order id*/
            @RequestParam("orderId") Long orderId,
            RedirectAttributes redirectAttributes
    ) {
        CurrentAddress cAddress;
        if (ExistenceChecker.isExists(redirectAttributes, email, passportNumber, pin, studentPhone)) {
            return "redirect:/student/add-student";
        }
        System.out.println(dormitory);
        if (dormitory.equals("other")) {
            cAddress = new CurrentAddress(currentRegion, currentDistrict, currentAddress, countRoommates, categoryRoommates, statusResidence);
        } else {
            cAddress = dormitoryAddressWriter.writer(dormitory);
        }
        LocalDate date = LocalDate.parse(dateOfBirth);
        LocalDate dateOfAdmission = LocalDate.parse(admissionDate);
        PermanentAddress pAddress = new PermanentAddress(permanentCountry, permanentRegion, permanentDistrict, permanentAddress);
        Contact contact = new Contact(email, studentPhone, parentPhone);
        Passport passport = new Passport(firstName, lastName, patronymic, citizenship, nationality, gender, date, passportNumber, pin);
        String photoUrl = uploadUtility.uploadPhoto(photo, "");
        StudentDataTransferObject studentDto = new StudentDataTransferObject(photoUrl, paymentForm, groupName, dateOfAdmission, socialCategory, studentCategory, previousEducation, pAddress, cAddress, contact, passport, facultyId, specialityId, orderId);
        studentService.createStudent(studentDto);

        return "redirect:/student/list-student";
    }

    //GET ALL
    @GetMapping(value = "/list-student")
    String getAllStudents(Model model,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword,
                          @RequestParam(value = "faculty", defaultValue = "") String faculty,
                          @RequestParam(value = "speciality", defaultValue = "") String speciality,
                          @RequestParam(value = "paymentForm", defaultValue = "") String paymentForm,
                          @RequestParam(value = "orderName", defaultValue = "") String order,
                          @RequestParam(value = "admissionDate", defaultValue = "") String admissionDate,
                          @RequestParam(value = "groupName", defaultValue = "") String groupName) {

        int currentPage = page;
        int pageSize = size;
        Page<Student> studentPage = studentService.findPaginated(
                PageRequest.of(currentPage - 1, pageSize),
                keyword,
                faculty,
                speciality,
                paymentForm,
                order,
                admissionDate,
                groupName);
        HashSet<String> groupList = new HashSet<>();
        HashSet<String> course = new HashSet<>();
        for (Student student : Objects.requireNonNull(studentService.getAllStudents().getBody())) {
            groupList.add(student.getGroupName());

        }
        model.addAttribute("faculties", facultyService.getAllFaculties().getBody());
        model.addAttribute("specialities", specialityService.getAllSpecialities().getBody());
        model.addAttribute("orders", orderService.getAllOrders().getBody());
        model.addAttribute("studentPage", studentPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("faculty", faculty);
        model.addAttribute("speciality", speciality);
        model.addAttribute("paymentForm", paymentForm);
        model.addAttribute("orderName", order);
        model.addAttribute("admissionDate", admissionDate);
        model.addAttribute("groupName", groupName);
        model.addAttribute("groupList", groupList);
        int totalPages = studentPage.getTotalPages();
        List<Integer> pageNumbers;
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "/student/list-student";
    }

    //GET BY ID
    @GetMapping(value = "/{id}")
    ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    //UPDATE PAGE
    @GetMapping(value = "/update/{id}")
    String updateStudentPage(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id).getBody();

        model.addAttribute("student", student);
        assert student != null;
        model.addAttribute("photoUrl", student.getPhotoUrl());
        model.addAttribute("faculties", facultyService.getAllFaculties().getBody());
        model.addAttribute("specialities", specialityService.getAllSpecialities().getBody());
        model.addAttribute("orders", orderService.getAllOrders().getBody());

        return "/student/update-student";
    }

    //UPDATE PAGE
    @GetMapping(value = "/resume/{id}")
    String resumeStudentPage(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response) {
        Student student = studentService.getStudentById(id).getBody();
        assert student != null;

        model.addAttribute("student", student);
        model.addAttribute("photoUrl", student.getPhotoUrl());
        model.addAttribute("faculties", facultyService.getAllFaculties().getBody());
        model.addAttribute("specialities", specialityService.getAllSpecialities().getBody());
        model.addAttribute("orders", orderService.getAllOrders().getBody());


        return "/student/resume";
    }

    //UPDATE
    @PostMapping(value = "/update/{id}")
    String updateStudent(
            @PathVariable Long id,
            @RequestParam("studentPhoto") MultipartFile photo,
            @RequestParam("paymentForm") String paymentForm,
            @RequestParam("admissionDate") String admissionDate,
            @RequestParam("socialCategory") String socialCategory,
            @RequestParam("studentCategory") String studentCategory,
            @RequestParam("previousEducation") String previousEducation,
            @RequestParam("groupName") String groupName,
            /*permanent address data*/
            @RequestParam("permanentCountry") String permanentCountry,
            @RequestParam("permanentRegion") String permanentRegion,
            @RequestParam("permanentDistrict") String permanentDistrict,
            @RequestParam("permanentAddress") String permanentAddress,
            /*current address data*/
            @RequestParam("currentRegion") String currentRegion,
            @RequestParam("currentDistrict") String currentDistrict,
            @RequestParam("currentAddress") String currentAddress,
            @RequestParam("countRoommates") Integer countRoommates,
            @RequestParam("categoryRoommates") String categoryRoommates,
            @RequestParam("statusResidence") String statusResidence,
            /*contact data*/
            @RequestParam("email") String email,
            @RequestParam("studentPhone") String studentPhone,
            @RequestParam("parentPhone") String parentPhone,
            /*passport data*/
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("patronymic") String patronymic,
            @RequestParam("citizenship") String citizenship,
            @RequestParam("nationality") String nationality,
            @RequestParam("gender") String gender,
            @RequestParam("dateOfBirth") String dateOfBirth,
            @RequestParam("passportNumber") String passportNumber,
            @RequestParam("pin") String pin,
            /*faculty id*/
            @RequestParam("faultyId") Long facultyId,
            /*speciality id*/
            @RequestParam("specialityId") Long specialityId,
            /*order id*/
            @RequestParam("orderId") Long orderId,
            RedirectAttributes redirectAttributes
    ) {
        /*if (ExistenceChecker.isExists(redirectAttributes, email, passportNumber, pin, studentPhone)) {
            return "redirect:/student/add-student";
        }*/
        System.out.println("post updated student data");
        LocalDate date = LocalDate.parse(dateOfBirth);
        String url = Objects.requireNonNull(studentService.getStudentById(id).getBody()).getPhotoUrl();

        LocalDate dateOfAdmission = LocalDate.parse(admissionDate);
        PermanentAddress pAddress = new PermanentAddress(permanentCountry, permanentRegion, permanentDistrict, permanentAddress);
        CurrentAddress cAddress = new CurrentAddress(currentRegion, currentDistrict, currentAddress, countRoommates, categoryRoommates, statusResidence);
        Contact contact = new Contact(email, studentPhone, parentPhone);
        Passport passport = new Passport(firstName, lastName, patronymic, citizenship, nationality, gender, date, passportNumber, pin);
        String photoUrl = uploadUtility.uploadPhoto(photo, url);
        StudentDataTransferObject studentDto = new StudentDataTransferObject(photoUrl, paymentForm, groupName, dateOfAdmission, socialCategory, studentCategory, previousEducation, pAddress, cAddress, contact, passport, facultyId, specialityId, orderId);
        studentService.updateStudent(id, studentDto);
        return "redirect:/student/list-student";
    }

    //DELETE
    @GetMapping(value = "/delete/{id}")
    String deleteStudent(@PathVariable Long id, @RequestParam("keyword") Optional<String> keyword) {
        String searchKeyword = keyword.orElse("");
        studentService.deleteStudent(id);
        if (searchKeyword.equals("")) {
            return "redirect:/student/list-student";
        } else return "redirect:/student/list-student?keyword=" + keyword;
    }

    //GET STUDENT BY FIRSTNAME
    @GetMapping(value = "/firstname/{firstname}")
    ResponseEntity<List<Student>> getStudentsByFirstname(@PathVariable String firstname) {
        return studentService.getStudentsByFirstname(firstname);
    }

    //GET STUDENT BY LASTNAME
    @GetMapping(value = "/firstname/{lastname}")
    ResponseEntity<List<Student>> getStudentsByLastname(@PathVariable String lastname) {
        return studentService.getStudentsByLastname(lastname);
    }

    //GET ALL STUDENTS DATA AS EXCEL
    @GetMapping(value = "/download/excel")
    public ResponseEntity<Resource> getFile(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "faculty", defaultValue = "") String faculty,
            @RequestParam(value = "speciality", defaultValue = "") String speciality,
            @RequestParam(value = "paymentForm", defaultValue = "") String paymentForm,
            @RequestParam(value = "orderName", defaultValue = "") String order,
            @RequestParam(value = "admissionDate", defaultValue = "") String admissionDate,
            @RequestParam(value = "groupName", defaultValue = "") String groupName) {
        String fileName = "students.xls";
        InputStreamResource file = new InputStreamResource(studentExcelService.load(keyword,
                faculty,
                speciality,
                paymentForm,
                order,
                admissionDate,
                groupName));
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
