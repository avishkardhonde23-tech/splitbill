package com.example.splitbill.controller;

import com.example.splitbill.entity.GroupEntity;
import com.example.splitbill.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Group API", description = "Manage expense groups")
@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @Operation(summary = "Create a new group")
    @PostMapping
    public GroupEntity createGroup(
            @RequestBody GroupEntity group) {

        return groupService.createGroup(group);
    }
    @GetMapping
    @Operation(summary = "Get all groups")
    public List<GroupEntity> getAllGroups() {
        return groupService.getAllGroups();
    }
}
