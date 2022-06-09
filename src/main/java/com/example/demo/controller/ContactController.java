package com.example.demo.controller;

import com.example.demo.entity.Contact;
import com.example.demo.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //CRATE
    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        return contactService.createContact(contact);
    }

    //READ
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        return contactService.getAllContacts();
    }

    //READ BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        return contactService.getContactsById(id);
    }

    //UPDATE
    @PutMapping(value = "/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact contact) {
        return contactService.updateContact(id, contact);
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable Long id) {
         return contactService.deleteContact(id);
    }
}
