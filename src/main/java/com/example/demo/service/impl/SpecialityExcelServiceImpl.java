package com.example.demo.service.impl;

import com.example.demo.entity.Speciality;
import com.example.demo.helper.SpecialityExcelHelper;
import com.example.demo.repository.SpecialityRepository;
import com.example.demo.service.SpecialityExcelService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

@Service
public class SpecialityExcelServiceImpl implements SpecialityExcelService {

    private final SpecialityRepository specialityRepository;

    public SpecialityExcelServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public ByteArrayInputStream load(String keyword) {

        final List<Speciality> speciality;
        if (Objects.equals(keyword, "")) {
            speciality = specialityRepository.findAll(Sort.by("name"));
        } else {
            speciality = specialityRepository.findAllByKeyword(keyword);
        }

        return SpecialityExcelHelper.specialitiesToExcel(speciality);
    }
}
