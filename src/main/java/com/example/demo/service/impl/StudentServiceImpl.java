package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.payload.StudentDataTransferObject;
import com.example.demo.payload.StudentDto;
import com.example.demo.repository.*;
import com.example.demo.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final PassportRepository passportRepository;
    private final CurrentAddressRepository currentAddressRepository;
    private final PermanentAddressRepository permanentAddressRepository;
    private final ContactRepository contactRepository;
    private final FacultyRepository facultyRepository;
    private final SpecialityRepository specialityRepository;
    private final OrderRepository orderRepository;

    public StudentServiceImpl(StudentRepository studentRepository, PassportRepository passportRepository, CurrentAddressRepository currentAddressRepository, PermanentAddressRepository permanentAddressRepository, ContactRepository contactRepository, FacultyRepository facultyRepository, SpecialityRepository specialityRepository, OrderRepository orderRepository) {
        this.studentRepository = studentRepository;
        this.permanentAddressRepository = permanentAddressRepository;
        this.currentAddressRepository = currentAddressRepository;
        this.passportRepository = passportRepository;
        this.contactRepository = contactRepository;
        this.facultyRepository = facultyRepository;
        this.specialityRepository = specialityRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public ResponseEntity<Student> createStudent(StudentDto studentDto) {
        try {
            Optional<PermanentAddress> optionalPermanentAddress = permanentAddressRepository.findById(studentDto.getPermanentAddressId());
            Optional<CurrentAddress> optionalCurrentAddress = currentAddressRepository.findById(studentDto.getCurrentAddressId());
            Optional<Passport> optionalPassport = passportRepository.findById(studentDto.getPassportId());
            Optional<Contact> optionalContact = contactRepository.findById(studentDto.getContactId());
            Optional<Faculty> optionalFaculty = facultyRepository.findById(studentDto.getFacultyId());
            Optional<Speciality> optionalSpeciality = specialityRepository.findById(studentDto.getSpecialityId());
            Optional<Order> optionalOrder = orderRepository.findById(studentDto.getOrderId());

            if (!(optionalPermanentAddress.isPresent() && optionalCurrentAddress.isPresent() && optionalPassport.isPresent() && optionalContact.isPresent()))
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            if (!(optionalFaculty.isPresent() && optionalSpeciality.isPresent() && optionalOrder.isPresent()))
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            PermanentAddress permanentAddress = optionalPermanentAddress.get();
            CurrentAddress currentAddress = optionalCurrentAddress.get();
            Passport passport = optionalPassport.get();
            Contact contact = optionalContact.get();
            Faculty faculty = optionalFaculty.get();
            Speciality speciality = optionalSpeciality.get();
            Order order = optionalOrder.get();

            Student savedStudent = studentRepository.save(new Student(
                    studentDto.getPhotoUrl(),
                    studentDto.getPaymentForm(),
                    studentDto.getAdmissionDate(),
                    studentDto.getSocialCategory(),
                    studentDto.getStudentCategory(),
                    studentDto.getPreviousEducation(),
                    permanentAddress,
                    currentAddress,
                    contact,
                    passport,
                    faculty,
                    speciality,
                    studentDto.getGroupName(),
                    order
            ));
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Student> createStudent(StudentDataTransferObject studentDto) {
        try {
            Optional<Faculty> optionalFaculty = facultyRepository.findById(studentDto.getFacultyId());
            Optional<Speciality> optionalSpeciality = specialityRepository.findById(studentDto.getSpecialityId());
            Optional<Order> optionalOrder = orderRepository.findById(studentDto.getOrderId());

            if (!(optionalFaculty.isPresent() && optionalSpeciality.isPresent() && optionalOrder.isPresent()))
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            PermanentAddress permanentAddress = permanentAddressRepository.save(studentDto.getPermanentAddress());
            CurrentAddress currentAddress = currentAddressRepository.save(studentDto.getCurrentAddress());
            Passport passport = passportRepository.save(studentDto.getPassport());
            Contact contact = contactRepository.save(studentDto.getContact());
            Faculty faculty = optionalFaculty.get();
            Speciality speciality = optionalSpeciality.get();
            Order order = optionalOrder.get();
            Student student = studentRepository.save(new Student(studentDto.getPhotoUrl(), studentDto.getPaymentForm(), studentDto.getAdmissionDate(), studentDto.getSocialCategory(), studentDto.getStudentCategory(), studentDto.getPreviousEducation(), permanentAddress, currentAddress, contact, passport, faculty, speciality, studentDto.getGroupName(), order));

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> studentList = studentRepository.findAll();
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Student> findPaginated(Pageable pageable, String searchKeyword, String facultyKeyword, String specialityKeyword, String paymentFormKeyword, String orderKeyword, String admissionDateKeyword, String groupNameKeyword) {
        final List<Student> students;

            students = studentRepository.findAllByKeywords(
                    searchKeyword,
                    facultyKeyword,
                    specialityKeyword,
                    paymentFormKeyword,
                    orderKeyword,
                    groupNameKeyword
            );
                /*searchKeyword,
                facultyKeyword,
                specialityKeyword,
                paymentFormKeyword,
                orderKeyword ,
                groupNameKeyword*/
        Page<Student> studentPage;
        try {
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = pageSize * currentPage;

            List<Student> list;
            if (startItem > students.size()) {
                list = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, students.size());
                list = students.subList(startItem, toIndex);
            }
            studentPage = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), students.size());

        } catch (Exception e) {
            return null;
        }
        return studentPage;
    }



    @Override
    public ResponseEntity<Student> getStudentById(Long id) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(id);
            if (!optionalStudent.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Student student = optionalStudent.get();
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByFirstname(String firstname) {
        try {
            List<Student> studentList = studentRepository.getAllByPassportFirstName(firstname);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByLastname(String lastname) {
        try {
            List<Student> studentList = studentRepository.getAllByPassportLastName(lastname);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByFullName(String firstname, String lastname) {
        try {
            List<Student> studentList = studentRepository.getAllByPassportFirstNameAndPassportLastName(firstname, lastname);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByGender(String gender) {
        try {
            List<Student> studentList = studentRepository.getAllByPassportGender(gender);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByFaculty(String faculty) {
        try {
            List<Student> studentList = studentRepository.getAllByFacultyName(faculty);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsBySpeciality(String speciality) {
        try {
            List<Student> studentList = studentRepository.getAllBySpecialityName(speciality);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByPermanentCountry(String country) {
        try {
            List<Student> studentList = studentRepository.getAllByPermanentAddressCountry(country);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByPermanentRegion(String region) {
        try {
            List<Student> studentList = studentRepository.getAllByPermanentAddressRegion(region);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByPermanentDistrict(String district) {
        try {
            List<Student> studentList = studentRepository.getAllByPermanentAddressDistrict(district);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByCurrentRegion(String region) {
        try {
            List<Student> studentList = studentRepository.getAllByCurrentAddressRegion(region);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByCurrentDistrict(String district) {
        try {
            List<Student> studentList = studentRepository.getAllByCurrentAddressDistrict(district);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Student>> getStudentsByCurrentAddressResidenceStatus(String statusResidence) {
        try {
            List<Student> studentList = studentRepository.getAllByCurrentAddressStatusResidence(statusResidence);
            return new ResponseEntity<>(studentList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Student> updateStudent(Long id, StudentDataTransferObject studentDto) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(id);
            if (!optionalStudent.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            Optional<Faculty> optionalFaculty = facultyRepository.findById(studentDto.getFacultyId());
            Optional<Speciality> optionalSpeciality = specialityRepository.findById(studentDto.getSpecialityId());
            Optional<Order> optionalOrder = orderRepository.findById(studentDto.getOrderId());

            if (!(optionalFaculty.isPresent() && optionalSpeciality.isPresent() && optionalOrder.isPresent()))
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            PermanentAddress permanentAddress = studentDto.getPermanentAddress();
            CurrentAddress currentAddress = studentDto.getCurrentAddress();
            Passport passport = studentDto.getPassport();
            Contact contact = studentDto.getContact();
            Faculty faculty = optionalFaculty.get();
            Speciality speciality = optionalSpeciality.get();
            Order order = optionalOrder.get();

            Student editedStudent = optionalStudent.get();
            editedStudent.setPhotoUrl(studentDto.getPhotoUrl());
            editedStudent.setPaymentForm(studentDto.getPaymentForm());
            editedStudent.setAdmissionDate(studentDto.getAdmissionDate());
            editedStudent.setSocialCategory(studentDto.getSocialCategory());
            editedStudent.setStudentCategory(studentDto.getStudentCategory());
            editedStudent.setPreviousEducation(studentDto.getPreviousEducation());

            //EDIT PERMANENT ADDRESS
            PermanentAddress editedPermanentAddress = editedStudent.getPermanentAddress();
            editedPermanentAddress.setCountry(permanentAddress.getCountry());
            editedPermanentAddress.setRegion(permanentAddress.getRegion());
            editedPermanentAddress.setDistrict(permanentAddress.getDistrict());
            editedPermanentAddress.setAddress(permanentAddress.getAddress());

            permanentAddressRepository.save(editedPermanentAddress);
            editedStudent.setPermanentAddress(editedPermanentAddress);
            //EDIT CURRENT ADDRESS
            CurrentAddress editedCurrentAddress = editedStudent.getCurrentAddress();
            editedCurrentAddress.setRegion(currentAddress.getRegion());
            editedCurrentAddress.setDistrict(currentAddress.getDistrict());
            editedCurrentAddress.setAddress(currentAddress.getAddress());
            editedCurrentAddress.setCountRoommates(currentAddress.getCountRoommates());
            editedCurrentAddress.setCategoryRoommates(currentAddress.getCategoryRoommates());
            editedCurrentAddress.setStatusResidence(currentAddress.getStatusResidence());

            currentAddressRepository.save(editedCurrentAddress);
            editedStudent.setCurrentAddress(editedCurrentAddress);

            //EDIT CONTACT
            Contact editedContact = editedStudent.getContact();
            editedContact.setEmail(contact.getEmail());
            editedContact.setStudentPhone(contact.getStudentPhone());
            editedContact.setParentPhone(contact.getParentPhone());

            contactRepository.save(editedContact);
            editedStudent.setContact(editedContact);

            //EDIT PASSPORT
            Passport editedPassport = editedStudent.getPassport();
            editedPassport.setFirstName(passport.getFirstName());
            editedPassport.setLastName(passport.getLastName());
            editedPassport.setPatronymic(passport.getPatronymic());
            editedPassport.setCitizenship(passport.getCitizenship());
            editedPassport.setNationality(passport.getNationality());
            editedPassport.setGender(passport.getGender());
            editedPassport.setDateOfBirth(passport.getDateOfBirth());
            editedPassport.setPassportNumber(passport.getPassportNumber());
            editedPassport.setPin(passport.getPin());

            passportRepository.save(editedPassport);
            editedStudent.setPassport(editedPassport);

            //EDIT FACULTY
            editedStudent.setFaculty(faculty);

            //EDIT SPECIALITY

            editedStudent.setSpeciality(speciality);

            //EDIT ORDER

            editedStudent.setOrder(order);

            //EDIT GROUP NAME
            editedStudent.setGroupName(studentDto.getGroupName());

            Student updatedStudent = studentRepository.save(editedStudent);
            return new ResponseEntity<>(updatedStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<HttpStatus> deleteStudent(Long id) {
        try {
            long count = studentRepository.count();
            if (studentRepository.existsById(id)) {
                studentRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
