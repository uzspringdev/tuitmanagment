package com.example.demo.utility;

import com.example.demo.entity.CurrentAddress;
import org.springframework.stereotype.Component;

@Component
public class DormitoryAddressWriter {
    public  CurrentAddress writer(String dormitory){
        switch (dormitory){
            case "dormitoryOne" : {
                return new CurrentAddress("Toshkent","Yunusobod", "Amir Temur shox ko'chasi 117-uy",4,"Universitetdoshlari","Talabalar yotoqxonasi");
            }
            case "dormitoryTwo" : {
                return new CurrentAddress("Toshkent","Yunusobod", "Amir Temur shox ko'chasi 118-uy",4,"Universitetdoshlari","Talabalar yotoqxonasi");
            }
            case "dormitoryThree" : {
                return new CurrentAddress("Toshkent","Yunusobod", "Amir Temur shox ko'chasi 119-uy",4,"Universitetdoshlari","Talabalar yotoqxonasi");
            }
            default: return null;
        }
    }
}
