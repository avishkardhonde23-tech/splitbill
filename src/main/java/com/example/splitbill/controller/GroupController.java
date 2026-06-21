package com.example.splitbill.controller;

import com.example.splitbill.entity.GroupEntity;
import com.example.splitbill.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    public GroupEntity createGroup(
            @RequestBody GroupEntity group) {

        return groupService.createGroup(group);
    }
}
