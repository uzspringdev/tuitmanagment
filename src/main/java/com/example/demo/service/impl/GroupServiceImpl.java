package com.example.demo.service.impl;

import com.example.demo.entity.Group;
import com.example.demo.repository.GroupRepository;
import com.example.demo.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<Group> createGroup(Group group) {

        try {
            Group newGroup = groupRepository.save(new Group(group.getName()));
            return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<List<Group>> getAllGroups() {
        try {
            List<Group> groupList = groupRepository.findAll();
            return new ResponseEntity<>(groupList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Group> getGroupById(Long id) {
        try {
            Optional<Group> optionalGroup = groupRepository.findById(id);
            if (!optionalGroup.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Group group = optionalGroup.get();
            return new ResponseEntity<>(group, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Group> updateGroup(Long id, Group group) {
        try {
            Optional<Group> optionalGroup = groupRepository.findById(id);
            if (!optionalGroup.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            Group editedGroup = optionalGroup.get();
            editedGroup.setName(group.getName());
            Group updatedGroup = groupRepository.save(editedGroup);
            return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<HttpStatus> deleteGroup(Long id) {
        try {
            if (groupRepository.existsById(id)) {
                groupRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
