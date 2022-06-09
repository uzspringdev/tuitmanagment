package com.example.demo.service.impl;

import com.example.demo.entity.Student;
import com.example.demo.helper.StudentExcelHelper;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentExcelService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

@Service
public class StudentExcelServiceImpl implements StudentExcelService {

    private final StudentRepository studentRepository;

    public StudentExcelServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public ByteArrayInputStream load( String searchKeyword, String facultyKeyword, String specialityKeyword, String paymentFormKeyword, String orderKeyword, String admissionDateKeyword, String groupNameKeyword) {

        final List<Student> students;


        students = studentRepository.findAllByKeywords(
                searchKeyword,
                facultyKeyword,
                specialityKeyword,
                paymentFormKeyword,
                orderKeyword,
                groupNameKeyword
        );

        return StudentExcelHelper.studentsToExcel(students);
    }
}
