package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)

    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String patronymic;

    @Column(nullable = false)
    private String citizenship;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String passportNumber;

    @Column(nullable = false, unique = true)
    private String pin;

    public Passport(String firstName, String lastName, String patronymic, String citizenship, String nationality, String gender, LocalDate dateOfBirth, String passportNumber, String pin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.citizenship = citizenship;
        this.nationality = nationality;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.passportNumber = passportNumber;
        this.pin = pin;
    }
}
