package com.example.demo.repository;

import com.example.demo.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {
    @Query(value = "SELECT * FROM Speciality s WHERE s.name ILIKE  concat('%', :keyword, '%') order by s.name", nativeQuery = true)
    List<Speciality> findAllByKeyword(@Param(value = "keyword") String keyword);
}
