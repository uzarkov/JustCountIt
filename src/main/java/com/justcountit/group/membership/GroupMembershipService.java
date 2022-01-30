package com.justcountit.group.membership;

import com.justcountit.commons.Role;
import com.justcountit.group.Group;
import com.justcountit.group.GroupBaseData;
import com.justcountit.group.GroupRepository;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserRepository;
import com.justcountit.user.AppUserWithRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupMembershipService {
    private final GroupMembershipRepository groupMembershipRepository;
    private final GroupRepository groupRepository;
    private final AppUserRepository appUserRepository;

    public List<GroupBaseData> getAllUserGroups(Long userId){
        return groupMembershipRepository.getAllUserGroups(userId);
    }

    public boolean isMemberOf(Long userId, Long groupId) {
        return groupMembershipRepository.isMemberOf(userId, groupId);
    }

    public GroupMembership getMembership(Long userId, Long groupId) {
        return groupMembershipRepository.findById(GroupMembershipKey.from(userId, groupId))
                .orElseThrow();
    }

    public boolean isOrganizer(Long userId, Long groupId){
        var groupMember = groupMembershipRepository.
                findById(GroupMembershipKey.from(userId, groupId)).
                orElseThrow();
        return groupMember.getRole() == Role.ORGANIZER;

    }

    public void deleteUserFromGroupMembership(Long userId, Long groupId){
        groupMembershipRepository.deleteById(GroupMembershipKey.from(userId,groupId));
    }

    public GroupMembership addUserToGroup(Long userId, Long groupId){
        Group group = groupRepository.findById(groupId).orElseThrow();
        AppUser appUser = appUserRepository.findById(userId).orElseThrow();
        var sth = groupMembershipRepository.getAllGroupMembers(groupId);
        // if group is empty new group member becomes organizer
        if (sth.isEmpty()){
            return groupMembershipRepository.save(new GroupMembership(GroupMembershipKey.from(userId, groupId),appUser, group, Role.ORGANIZER));
        }
        else {
            return groupMembershipRepository.save(new GroupMembership(GroupMembershipKey.from(userId, groupId), appUser, group, Role.MEMBER));
        }
    }

    public void addUserToGroup(AppUser user, Group group, Role role) {
        var membershipKey = GroupMembershipKey.from(user.getId(), group.getId());
        var membership = new GroupMembership(membershipKey, user, group, role);
        user.joinGroup(membership);
        group.addMembership(membership);

        groupMembershipRepository.save(membership);
    }

    public Set<AppUserWithRole> getGroupMembers(Long groupId){
        return groupMembershipRepository.getAllGroupMembers(groupId);
    }
}
