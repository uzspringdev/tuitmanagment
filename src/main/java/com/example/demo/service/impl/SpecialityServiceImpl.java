package com.example.demo.service.impl;

import com.example.demo.entity.Speciality;
import com.example.demo.repository.SpecialityRepository;
import com.example.demo.service.SpecialityService;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SpecialityServiceImpl implements SpecialityService {
    private final SpecialityRepository specialityRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public ResponseEntity<Speciality> createSpeciality(Speciality speciality) {
        try {
            Speciality newSpeciality = specialityRepository.save(
                    new Speciality(
                            speciality.getCode(),
                            speciality.getName(),
                            speciality.getTypeOfEducation()));
            return new ResponseEntity<>(newSpeciality, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Speciality>> getAllSpecialities() {
        try {
            List<Speciality> specialityList = specialityRepository.findAll();
            return new ResponseEntity<>(specialityList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Speciality> getSpecialityById(Long id) {
        try {
            Optional<Speciality> optionalSpeciality = specialityRepository.findById(id);
            if (!optionalSpeciality.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Speciality speciality = optionalSpeciality.get();
            return new ResponseEntity<>(speciality, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Speciality> updateSpeciality(Long id, Speciality speciality) {
        try {
            Optional<Speciality> optionalSpeciality = specialityRepository.findById(id);
            if (!optionalSpeciality.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Speciality editedSpeciality = optionalSpeciality.get();
            editedSpeciality.setCode(speciality.getCode());
            editedSpeciality.setName(speciality.getName());
            editedSpeciality.setTypeOfEducation(speciality.getTypeOfEducation());
            Speciality newSpeciality = specialityRepository.save(editedSpeciality);
            return new ResponseEntity<>(newSpeciality, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteSpeciality(Long id) {
        try {
            if (specialityRepository.existsById(id)) {
                specialityRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Speciality> findPaginated(Pageable pageable, String keyword) {
        final List<Speciality> specialities;
        if (Objects.equals(keyword, "")) {
            specialities = specialityRepository.findAll(Sort.by("name"));
        } else {
            specialities = specialityRepository.findAllByKeyword(keyword);
        }

        Page<Speciality> specialityPage;
        try {
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            List<Speciality> list;
            if (specialities.size() < startItem) {
                list = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, specialities.size());
                list = specialities.subList(startItem, toIndex);
            }
            specialityPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), specialities.size());
        } catch (Exception e) {
            specialityPage = null;
        }
        return specialityPage;
    }
}
