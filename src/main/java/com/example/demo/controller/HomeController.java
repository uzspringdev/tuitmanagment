package com.example.demo.controller;

import com.example.demo.entity.Faculty;
import com.example.demo.entity.Student;
import com.example.demo.model.EmailDetails;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping()
public class HomeController {
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    private final SpecialityService specialityService;
    private final OrderService orderService;
    private final StudentService studentService;
    private final FacultyService facultyService;
    private final EmailService emailService;

    public HomeController(StudentRepository studentRepository, FacultyRepository facultyRepository, SpecialityService specialityService, OrderService orderService, StudentService studentService, FacultyService facultyService, EmailService emailService) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.specialityService = specialityService;
        this.orderService = orderService;
        this.studentService = studentService;
        this.facultyService = facultyService;
        this.emailService = emailService;
    }

    @GetMapping(value = "/")
    public String homePage(Model model) {
        Long maleCount = studentRepository.countAllByPassportGender("Male");
        Long femaleCount = studentRepository.countAllByPassportGender("Female");
        List<String> facultyNameList = new ArrayList<>();
        List<Long> studentCountList = new ArrayList<>();
        for (Faculty faculty : facultyRepository.findAll()) {
            facultyNameList.add(faculty.getName());
        }
        for (int i = 0; i < facultyNameList.size(); i++) {
            if (studentRepository.getStudentCountByFaculty().size() > i)
                studentCountList.add(studentRepository.getStudentCountByFaculty().get(i));
            else studentCountList.add(0L);
        }

        model.addAttribute("maleCount", maleCount);
        model.addAttribute("femaleCount", femaleCount);
        model.addAttribute("facultyNameList", facultyNameList);
        model.addAttribute("studentCountList", studentCountList);
        return "home";
    }

    @GetMapping(value = "/contact")
    public String contactPage(Model model,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "faculty", defaultValue = "") String faculty,
                              @RequestParam(value = "speciality", defaultValue = "") String speciality,
                              @RequestParam(value = "paymentForm", defaultValue = "") String paymentForm,
                              @RequestParam(value = "orderName", defaultValue = "") String order,
                              @RequestParam(value = "admissionDate", defaultValue = "") String admissionDate,
                              @RequestParam(value = "groupName", defaultValue = "") String groupName,
                              @RequestParam(value = "message", defaultValue = "") String message) {
        final List<Student> students;


        HashSet<String> groupList = new HashSet<>();
        for (Student student : Objects.requireNonNull(studentService.getAllStudents().getBody())) {
            groupList.add(student.getGroupName());
        }
        model.addAttribute("faculties", facultyService.getAllFaculties().getBody());
        model.addAttribute("specialities", specialityService.getAllSpecialities().getBody());
        model.addAttribute("orders", orderService.getAllOrders().getBody());
        model.addAttribute("keyword", keyword);
        model.addAttribute("faculty", faculty);
        model.addAttribute("speciality", speciality);
        model.addAttribute("paymentForm", paymentForm);
        model.addAttribute("orderName", order);
        model.addAttribute("admissionDate", admissionDate);
        model.addAttribute("groupName", groupName);
        model.addAttribute("groupList", groupList);
        return "/contact/contact";
    }

    @PostMapping(value = "/contact")
    public String sendMail(Model model,
                           @RequestParam(value = "keyword", defaultValue = "") String keyword,
                           @RequestParam(value = "faculty", defaultValue = "") String faculty,
                           @RequestParam(value = "speciality", defaultValue = "") String speciality,
                           @RequestParam(value = "paymentForm", defaultValue = "") String paymentForm,
                           @RequestParam(value = "orderName", defaultValue = "") String order,
                           @RequestParam(value = "admissionDate", defaultValue = "") String admissionDate,
                           @RequestParam(value = "groupName", defaultValue = "") String groupName,
                           @RequestParam(value = "message", defaultValue = "") String message,
                           RedirectAttributes redirectAttributes) {
        final List<Student> students;

        students = studentRepository.findAllByKeywords(
                keyword,
                faculty,
                speciality,
                paymentForm,
                order,
                groupName
        );
        if (students.isEmpty()) redirectAttributes.addFlashAttribute("error", "Student is not found");


        for (Student student : students) {
            emailService.sendSimpleEmail(new EmailDetails(
                    student.getContact().getEmail(),
                    message,
                    "",
                    null

            ));
        }

        return "redirect:/contact";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "/login";
    }
}
