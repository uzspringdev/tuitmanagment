package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String photoUrl;

    @Column(nullable = false)
    private String paymentForm;

    @Column(nullable = false)
    private String groupName;

    @Column(nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate admissionDate;

    @Column(nullable = false)
    private String socialCategory;

    @Column(nullable = false)
    private String studentCategory;

    @Column(nullable = false)
    private String previousEducation;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    private PermanentAddress permanentAddress;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    private CurrentAddress currentAddress;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    private Contact contact;

    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    private Passport passport;

    @ManyToOne
    private Faculty faculty;

    @ManyToOne
    private Speciality speciality;


    @ManyToOne
    private Order order;

    public Student(String photoUrl, String paymentForm, LocalDate admissionDate, String socialCategory, String studentCategory, String previousEducation, PermanentAddress permanentAddress, CurrentAddress currentAddress, Contact contact, Passport passport, Faculty faculty, Speciality speciality, String groupName, Order order) {
        this.photoUrl = photoUrl;
        this.paymentForm = paymentForm;
        this.admissionDate = admissionDate;
        this.socialCategory = socialCategory;
        this.studentCategory = studentCategory;
        this.previousEducation = previousEducation;
        this.permanentAddress = permanentAddress;
        this.currentAddress = currentAddress;
        this.contact = contact;
        this.passport = passport;
        this.faculty = faculty;
        this.speciality = speciality;
        this.groupName = groupName;
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Student student = (Student) o;
        return id != null && Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
