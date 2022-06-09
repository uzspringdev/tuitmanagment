package com.example.demo.service;

import java.io.ByteArrayInputStream;

public interface OrderExcelService {
    ByteArrayInputStream load(String keyword);
}
