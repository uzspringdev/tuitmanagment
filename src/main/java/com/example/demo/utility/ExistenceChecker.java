package com.example.demo.utility;

import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.PassportRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Service
public class ExistenceChecker {
    private static ContactRepository contactRepository;
    private static PassportRepository passportRepository;

    public ExistenceChecker(ContactRepository contactRepository, PassportRepository passportRepository) {
        ExistenceChecker.contactRepository = contactRepository;
        ExistenceChecker.passportRepository = passportRepository;
    }

    public static Boolean isExists(RedirectAttributes redirectAttributes,String email, String passportNumber, String pin, String studentPhone){
        if (contactRepository.existsByEmail(email) && passportRepository.existsByPassportNumber(passportNumber) && contactRepository.existsByStudentPhone(studentPhone)) {
            redirectAttributes.addFlashAttribute("error", "The email, student phone number and passport number are already exist");
            return true;
        } else if (contactRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "The email is already exist");
            return true;
        } else if (passportRepository.existsByPassportNumber(passportNumber)) {
            redirectAttributes.addFlashAttribute("error", "The passport number is already exist");
            return true;
        } else if (passportRepository.existsByPin(pin)) {
            redirectAttributes.addFlashAttribute("error", "The passport pin is already exist");
            return true;
        }else if(contactRepository.existsByStudentPhone(studentPhone)){
            redirectAttributes.addFlashAttribute("error", "The student phone number is already exist");
        }
        return false;
    }
}
