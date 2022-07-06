package com.example.demo.service;

import java.io.ByteArrayInputStream;

public interface StudentExcelService {
    ByteArrayInputStream load( String searchKeyword, String facultyKeyword, String specialityKeyword, String paymentFormKeyword, String orderKeyword, String admissionDateKeyword, String groupNameKeyword);
}
