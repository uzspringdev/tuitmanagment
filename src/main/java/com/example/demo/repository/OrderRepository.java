package com.example.demo.repository;

import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from university.public.orders o where o.name ilike concat('%',:keyword,'%') order by o.name", nativeQuery = true)
    List<Order> findAllByKeyword(@Param(value = "keyword") String keyword);
}
