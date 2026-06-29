package com.example.splitbill.controller;

import com.example.splitbill.entity.GroupMember;
import com.example.splitbill.service.GroupMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Group Member API", description = "Manage group members")
@RestController
@RequestMapping("/api/group-members")
@RequiredArgsConstructor
public class GroupMemberController {
    private final GroupMemberService service;

    @Operation(summary = "Add member to group")
    @PostMapping
    public GroupMember addMember(
            @RequestBody GroupMember member){

        return service.addMember(member);
    }
    @GetMapping("/{groupId}")
    public List<GroupMember> getMembers(@PathVariable Long groupId) {
        return service.getMembers(groupId);
    }
}
