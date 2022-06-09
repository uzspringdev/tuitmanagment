package com.example.demo.service.impl;

import com.example.demo.entity.Faculty;
import com.example.demo.helper.FacultyExcelHelper;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.service.FacultyExcelService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

@Service
public class FacultyExcelServiceImpl implements FacultyExcelService {

    private final FacultyRepository facultyRepository;

    public FacultyExcelServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public ByteArrayInputStream load(String keyword) {

        final List<Faculty> faculties;
        if (Objects.equals(keyword, "")) {
            faculties = facultyRepository.findAll(Sort.by("name"));
        } else {
            faculties = facultyRepository.findAllByKeyword(keyword);
        }

        return FacultyExcelHelper.facultiesToExcel(faculties);
    }
}
