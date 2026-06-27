package com.example.splitbill.controller;

import com.example.splitbill.entity.GroupMember;
import com.example.splitbill.service.GroupMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-members")
@RequiredArgsConstructor
public class GroupMemberController {
    private final GroupMemberService service;

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
