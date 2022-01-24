package com.justcountit.group.membership;

import com.justcountit.commons.Role;
import com.justcountit.group.Group;
import com.justcountit.group.GroupRepository;
import com.justcountit.group.GroupService;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserRepository;
import com.justcountit.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupMembershipService {
    private final GroupMembershipRepository groupMembershipRepository;
    private final GroupRepository groupRepository;
    private final AppUserRepository appUserRepository;
    public boolean isOrganizer(Long userId, Long groupId){
        var groupMember = groupMembershipRepository.
                findById(new GroupMembershipKey(userId,groupId)).
                orElseThrow();
        return groupMember.getRole() == Role.ORGANIZER;

    }

    public void deleteUserFromGroupMembership(Long userId, Long groupId){
        groupMembershipRepository.deleteById(new GroupMembershipKey(userId,groupId));
    }

    public GroupMembership addUserToGroup(Long userId, Long groupId){
        Group group = groupRepository.findById(groupId).orElseThrow();
        AppUser appUser = appUserRepository.findById(userId).orElseThrow();

        return groupMembershipRepository.save(new GroupMembership(new GroupMembershipKey(userId,groupId),appUser, group, Role.ORGANIZER));

    }
}
