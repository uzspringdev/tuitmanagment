package com.example.demo.service.impl;

import com.example.demo.entity.Faculty;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.service.FacultyService;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public ResponseEntity<Faculty> createFaculty(Faculty faculty) {
        try {
            Faculty newFaculty = facultyRepository.save(new Faculty(faculty.getName()));
            return new ResponseEntity<>(newFaculty, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        try {
            List<Faculty> facultyList = facultyRepository.findAll();
            return new ResponseEntity<>(facultyList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Faculty> getFacultyById(Long id) {
        try {
            Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
            if (!optionalFaculty.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Faculty faculty = optionalFaculty.get();
            return new ResponseEntity<>(faculty, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Faculty> updateFaculty(Long id, String facultyName) {
        try {
            Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
            if (!optionalFaculty.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Faculty editedFaculty = optionalFaculty.get();
            editedFaculty.setName(facultyName);
            Faculty updatedFaculty = facultyRepository.save(editedFaculty);
            return new ResponseEntity<>(updatedFaculty, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteFaculty(Long id) {

        try {
            if (facultyRepository.existsById(id)) {
                facultyRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Page<Faculty> findPaginated(Pageable pageable, String keyword) {
        final List<Faculty> faculties;
        if (Objects.equals(keyword, "")) {
            faculties = facultyRepository.findAll(Sort.by("name"));
        } else {
            faculties = facultyRepository.findAllByKeyword(keyword);
        }

        Page<Faculty> facultyPage;
        try {
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            List<Faculty> list;
            if (faculties.size() < startItem) {
                list = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, faculties.size());
                list = faculties.subList(startItem, toIndex);
            }
            facultyPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), faculties.size());
        } catch (Exception e) {
            facultyPage = null;
        }
        return facultyPage;
    }
}
