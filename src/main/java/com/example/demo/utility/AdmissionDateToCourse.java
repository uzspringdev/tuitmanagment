package com.example.demo.utility;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AdmissionDateToCourse {

    private static String convertToCourse(LocalDate admissionDate ,LocalDate currentDate){
        //CURRENT DATE
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonth().getValue();
        int currentDayOfMonth = currentDate.getDayOfMonth();
        int currentDayOfYear = currentDate.getDayOfYear();

        //ADMISSION DATE
        int admissionYear = admissionDate.getYear();
        int admissionMonth = admissionDate.getMonth().getValue();
        int admissionDayOfMonth = admissionDate.getDayOfMonth();
        int admissionDayOfYear = admissionDate.getDayOfYear();

        System.out.println(admissionDayOfYear);
        if ((currentYear-admissionYear==0&&currentDayOfYear>admissionDayOfYear)||(currentYear-admissionYear==1&&currentDayOfYear<admissionDayOfYear)){
            return "1-kurs";
        }else if ((currentYear-admissionYear==1&&currentDayOfYear>admissionDayOfYear)||(currentYear-admissionYear==2&&currentDayOfYear<admissionDayOfYear)){
            return "2-kurs";
        }else if ((currentYear-admissionYear==2&&currentDayOfYear>admissionDayOfYear)||(currentYear-admissionYear==3&&currentDayOfYear<admissionDayOfYear)){
            return "3-kurs";
        }else if ((currentYear-admissionYear==3&&currentDayOfYear>admissionDayOfYear)||(currentYear-admissionYear==4&&currentDayOfYear<admissionDayOfYear)){
            return "4-kurs";
        }
        else return "other";
    }
}
