package com.example.demo.repository;

import com.example.demo.entity.Student;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    //GET ALL STUDENTS BY CRITERIA
    @Query(value = "SELECT * FROM Student st " +
            "INNER JOIN Passport p on p.id = st.passport_id " +
            "INNER JOIN Faculty f on st.faculty_id = f.id " +
            "INNER JOIN Speciality s on s.id = st.speciality_id " +
            "INNER JOIN Orders o on o.id = st.order_id " +
            "WHERE ((:searchKeyword ='' or p.last_name ILIKE concat('%', :searchKeyword, '%')) or " +
            "(:searchKeyword ='' or p.first_name ILIKE concat('%', :searchKeyword, '%')) or " +
            "(:searchKeyword ='' or p.patronymic ILIKE concat('%', :searchKeyword, '%'))) and " +
            "(:facultyKeyword ='' or f.name = :facultyKeyword) and" +
            "(:specialityKeyword ='' or s.name = :specialityKeyword) and" +
            "(:orderKeyword ='' or o.name = :orderKeyword) and" +
            "(:paymentFormKeyword ='' or st.payment_form=:paymentFormKeyword) and" +
            "(:groupNameKeyword ='' or st.group_name=:groupNameKeyword) " +
            "order by p.last_name, p.first_name, p.patronymic", nativeQuery = true)
    List<Student> findAllByKeywords(
            @Param(value = "searchKeyword") String searchKeyword,
            @Param(value = "facultyKeyword") String facultyKeyword,
            @Param(value = "specialityKeyword") String specialityKeyword,
            @Param(value = "paymentFormKeyword") String paymentFormKeyword,
            @Param(value = "orderKeyword") String orderKeyword,
            @Param(value = "groupNameKeyword") String groupNameKeyword
    );
    //GET ALL
    Long countAllByPassportGender(String gender);

    //GET STUDENT COUNT BY FACULTY
    @Query(value = "select count(*)  from faculty as f inner join student s on f.id = s.faculty_id group by f.name", nativeQuery = true)
    List<Long> getStudentCountByFaculty();

    @Query(value = "SELECT * FROM Student s INNER JOIN Passport p on p.id = s.passport_id WHERE p.last_name ILIKE concat('%', :keyword, '%') or p.first_name ILIKE concat('%', :keyword, '%') or p.patronymic ILIKE concat('%', :keyword, '%') order by p.last_name, p.first_name, p.patronymic", nativeQuery = true)
    List<Student> findAllByKeyword(@Param(value = "keyword") String keyword);

    @Query(value = "SELECT * FROM Student s INNER JOIN Passport p on p.id = s.passport_id order by p.last_name, p.first_name, p.patronymic", nativeQuery = true)
    @NonNull
    List<Student> findAll();

    //GET STUDENTS BY FIRSTNAME
    List<Student> getAllByPassportFirstName(String firstname);

    //GET STUDENTS BY LASTNAME
    List<Student> getAllByPassportLastName(String lastname);

    //GET STUDENTS BY FULL NAME
    List<Student> getAllByPassportFirstNameAndPassportLastName(String firstname, String lastname);

    //GET STUDENTS BY GENDER
    List<Student> getAllByPassportGender(String gender);

    //GET STUDENTS BY FACULTY
    List<Student> getAllByFacultyName(String faculty);

    //GET STUDENTS BY SPECIALITY
    List<Student> getAllBySpecialityName(String speciality);

    //GET STUDENTS BY PERMANENT COUNTRY
    List<Student> getAllByPermanentAddressCountry(String country);

    //GET STUDENTS BY PERMANENT REGION
    List<Student> getAllByPermanentAddressRegion(String region);

    //GET STUDENTS BY PERMANENT DISTRICT
    List<Student> getAllByPermanentAddressDistrict(String district);

    //GET STUDENTS BY CURRENT REGION
    List<Student> getAllByCurrentAddressRegion(String region);

    //GET STUDENTS BY CURRENT DISTRICT
    List<Student> getAllByCurrentAddressDistrict(String district);

    //GET STUDENT BY CURRENT ADDRESS RESIDENCE STATUS
    List<Student> getAllByCurrentAddressStatusResidence(String statusResidence);

}
