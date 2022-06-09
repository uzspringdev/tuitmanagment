package com.example.demo.service;

import com.example.demo.entity.Group;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GroupService {
    //CREATE
    ResponseEntity<Group> createGroup(Group group);

    //READ
    ResponseEntity<List<Group>> getAllGroups();

    //READ BY ID
    ResponseEntity<Group> getGroupById(Long id);

    //UPDATE
    ResponseEntity<Group> updateGroup(Long id, Group group);

    //DELETE
    ResponseEntity<HttpStatus> deleteGroup(Long id);
}
