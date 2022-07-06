package com.example.demo.repository;

import com.example.demo.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Boolean existsByUsername(String username);

    @Query(value = "SELECT a FROM Admin a JOIN FETCH a.roles where a.username=:username")
    Admin getAdminByUsername(String username);

    @Query(value = "SELECT a FROM Admin a JOIN FETCH a.roles where a.username=:username")
    Optional<Admin> getOptionalAdminByUsername(String username);

    @Query(value = "SELECT * FROM Admin  a WHERE a.username ILIKE  concat('%', :keyword, '%') order by a.username", nativeQuery = true)
    List<Admin> findAllByKeyword(@Param(value = "keyword") String keyword);

}
