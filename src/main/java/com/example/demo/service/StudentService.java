package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.payload.StudentDataTransferObject;
import com.example.demo.payload.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {
    //CREATE
    ResponseEntity<Student> createStudent(StudentDto student);

    //CREATE
    ResponseEntity<Student> createStudent(StudentDataTransferObject student);

    //READ
    ResponseEntity<List<Student>> getAllStudents();

    //READ PAGINATED
    Page<Student> findPaginated(Pageable pageable, String searchKeyword, String facultyKeyword, String specialityKeyword, String paymentFormKeyword, String orderKeyword, String admissionDateKeyword, String groupNameKeyword);

    //READ BY ID
    ResponseEntity<Student> getStudentById(Long id);

    //READ BY FIRSTNAME
     ResponseEntity<List<Student>> getStudentsByFirstname(String firstname);

    //READ BY FIRSTNAME
     ResponseEntity<List<Student>> getStudentsByLastname(String lastname);

    //READ BY FULL NAME
     ResponseEntity<List<Student>> getStudentsByFullName(String firstname, String lastname);

    //READ BY GENDER
     ResponseEntity<List<Student>> getStudentsByGender(String gender);

    //READ BY FACULTY
     ResponseEntity<List<Student>> getStudentsByFaculty(String faculty);

    //READ BY SPECIALITY
     ResponseEntity<List<Student>> getStudentsBySpeciality(String speciality);

    //READ BY PERMANENT COUNTRY
    ResponseEntity<List<Student>> getStudentsByPermanentCountry(String country);

    //READ BY PERMANENT REGION
    ResponseEntity<List<Student>> getStudentsByPermanentRegion(String region);

    //READ BY PERMANENT DISTRICT
    ResponseEntity<List<Student>> getStudentsByPermanentDistrict(String district);

    //READ BY CURRENT REGION
    ResponseEntity<List<Student>> getStudentsByCurrentRegion(String region);

    //READ BY CURRENT DISTRICT
    ResponseEntity<List<Student>> getStudentsByCurrentDistrict(String district);

    //READ BY CURRENT ADDRESS RESIDENCE STATUS
    ResponseEntity<List<Student>> getStudentsByCurrentAddressResidenceStatus(String statusResidence);

    //UPDATE
    ResponseEntity<Student> updateStudent(Long id, StudentDataTransferObject studentDto);

    //DELETE
    ResponseEntity<HttpStatus> deleteStudent(Long id);
}
