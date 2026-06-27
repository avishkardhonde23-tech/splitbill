package com.example.splitbill.service;

import com.example.splitbill.entity.GroupMember;
import com.example.splitbill.repository.GroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository repository;
    public GroupMember addMember(GroupMember member){

        if (repository
                .findByGroupIdAndUserId(member.getGroupId(), member.getUserId())
                .isPresent()) {

            throw new RuntimeException("User is already a member of this group");
        }
        return repository.save(member);
    }
    public List<GroupMember>getMembers(Long groupId){
        return repository.findByGroupId(groupId);
    }

}
