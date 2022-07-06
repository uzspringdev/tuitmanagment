package com.example.demo.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CurrentAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Integer countRoommates;

    @Column(nullable = false)
    private String categoryRoommates;

    @Column(nullable = false)
    private String statusResidence;


    public CurrentAddress(String region, String district, String address, Integer countRoommates, String categoryRoommates, String statusResidence) {
        this.region = region;
        this.district = district;
        this.address = address;
        this.countRoommates = countRoommates;
        this.categoryRoommates = categoryRoommates;
        this.statusResidence = statusResidence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CurrentAddress that = (CurrentAddress) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
