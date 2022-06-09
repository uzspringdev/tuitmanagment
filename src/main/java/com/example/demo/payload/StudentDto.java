package com.example.demo.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private String photoUrl;

    private String paymentForm;

    private String groupName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private LocalDate admissionDate;

    private String socialCategory;

    private String studentCategory;

    private String previousEducation;

    private Long permanentAddressId;

    private Long currentAddressId;

    private Long contactId;

    private Long passportId;

    private Long facultyId;

    private Long specialityId;

    private Long groupId;

    private Long orderId;

}
