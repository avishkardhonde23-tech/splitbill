package com.example.splitbill.controller;

import com.example.splitbill.entity.GroupMember;
import com.example.splitbill.service.GroupMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
