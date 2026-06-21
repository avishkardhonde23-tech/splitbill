package com.example.splitbill.service;

import com.example.splitbill.entity.GroupMember;
import com.example.splitbill.repository.GroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository repository;
    public GroupMember addMember(GroupMember member){
        return repository.save(member);
    }

}
