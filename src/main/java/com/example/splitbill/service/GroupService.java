package com.example.splitbill.service;

import com.example.splitbill.entity.GroupEntity;
import com.example.splitbill.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    public GroupEntity createGroup(GroupEntity group){

        return groupRepository.save(group);
    }
}
