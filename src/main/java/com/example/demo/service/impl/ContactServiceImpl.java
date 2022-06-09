package com.example.demo.service.impl;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
import com.example.demo.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public ResponseEntity<Contact> createContact(Contact contact) {
        try {
            Contact newContact = contactRepository.save(new Contact(contact.getEmail(), contact.getStudentPhone(), contact.getParentPhone()));
            return new ResponseEntity<>(newContact, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Contact>> getAllContacts() {
        try {
            List<Contact> contactList = contactRepository.findAll();
            return new ResponseEntity<>(contactList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Contact> getContactsById(Long id) {
        try {
            Optional<Contact> optionalContact = contactRepository.findById(id);
            if (optionalContact.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(optionalContact.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Contact> updateContact(Long id, Contact contact) {
        try {
            Optional<Contact> optionalContact = contactRepository.findById(id);
            if (optionalContact.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Contact editedContact = optionalContact.get();
            editedContact.setEmail(contact.getEmail());
            editedContact.setStudentPhone(contact.getStudentPhone());
            editedContact.setParentPhone(contact.getParentPhone());

            return new ResponseEntity<>(contactRepository.save(editedContact), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteContact(Long id) {
        try {
            if (contactRepository.existsById(id)) {
                contactRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
