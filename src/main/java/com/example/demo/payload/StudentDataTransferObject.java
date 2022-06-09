package com.example.demo.payload;

import com.example.demo.entity.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDataTransferObject {

    private String photoUrl;

    private String paymentForm;

    private String groupName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate admissionDate;

    private String socialCategory;

    private String studentCategory;

    private String previousEducation;

    private PermanentAddress permanentAddress;

    private CurrentAddress currentAddress;

    private Contact contact;

    private Passport passport;

    private Long facultyId;

    private Long specialityId;

    private Long orderId;

}
