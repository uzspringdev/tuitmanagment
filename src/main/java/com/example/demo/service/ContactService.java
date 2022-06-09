package com.example.demo.service;

import com.example.demo.entity.Contact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {
    //CREATE
    ResponseEntity<Contact> createContact(Contact contact);

    //READ
    ResponseEntity<List<Contact>> getAllContacts();

    //READ BY ID
    ResponseEntity<Contact> getContactsById(Long id);

    //UPDATE
    ResponseEntity<Contact> updateContact(Long id, Contact contact);

    //DELETE
    ResponseEntity<HttpStatus> deleteContact(Long id);
}
