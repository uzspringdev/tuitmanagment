package com.example.demo.controller;

import com.example.demo.entity.Group;
import com.example.demo.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    //CREATE
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    //READ
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        return groupService.getAllGroups();
    }

    //READ BY ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    //UPDATE
    @PutMapping(value = "/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group group) {
        return groupService.updateGroup(id, group);
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteGroup(@PathVariable Long id) {
        return groupService.deleteGroup(id);
    }
}
