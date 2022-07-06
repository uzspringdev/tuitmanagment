package com.example.demo.repository;

import com.example.demo.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query(value = "SELECT * FROM Faculty f WHERE f.name ILIKE  concat('%', :keyword, '%') order by f.name", nativeQuery = true)
    List<Faculty> findAllByKeyword(@Param("keyword") String keyword);

}
