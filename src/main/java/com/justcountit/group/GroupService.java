package com.justcountit.group;

import com.justcountit.commons.Role;
import com.justcountit.group.membership.GroupMembership;
import com.justcountit.group.membership.GroupMembershipException;
import com.justcountit.group.membership.GroupMembershipService;
import com.justcountit.group.validation.DeletingUserFromGroupException;
import com.justcountit.request.FinancialRequestService;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserService;
import com.justcountit.user.AppUserWithRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupMembershipService groupMembershipService;
    private final AppUserService appUserService;
    private final FinancialRequestService financialRequestService;
    private final GroupRepository groupRepository;

    public GroupMetadata getGroupMetadataFor(Long groupId) {
        var group = groupRepository.findById(groupId).orElseThrow();
        var members = group.getGroupMembers().stream()
                           .map(MembershipMetadata::from)
                           .collect(Collectors.toMap(MembershipMetadata::userId, m -> m));

        return new GroupMetadata(groupId,
                                 group.getName(),
                                 group.getDescription(),
                                 group.getCurrency(),
                                 members);
    }

    public Group getGroupBy(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow();
    }


    public void deleteUserFromGroup(Long userId, Long groupId)  {
        if (!financialRequestService.hasFinancialRequests(userId, groupId))
        {
            groupMembershipService.deleteUserFromGroupMembership(userId, groupId);
        }
        else {
            throw DeletingUserFromGroupException.pendingTransaction();
        }


    }
    @Transactional
    public GroupBaseData addGroup(Group group, String organizerEmail){
        validateGroupData(group);
        var user = appUserService.getUserByEmail(organizerEmail);
        var newGroup = groupRepository.save(group);
        addUserToGroup(user, newGroup, Role.ORGANIZER);
        return GroupBaseData.from(newGroup);
    }

    @Transactional
    public void removeGroup(Long groupId, String email) {
        var userId = appUserService.getUserByEmail(email).getId();
        if (groupMembershipService.isOrganizer(userId, groupId)) {
            groupRepository.removeById(groupId);
        }
        else {
            throw GroupMembershipException.principalNotOrganizer();
        }
    }

    public void addUserToGroup(AppUser user, Group group, Role role) {
        groupMembershipService.addUserToGroup(user, group, role);
    }

    private void validateGroupData(Group group) {
        if (group.getName().length() < 1 || group.getCurrency() == null) {
            throw WrongGroupDataException.wrongData();
        }
    }

    public void addUserToGroup(Long groupId, Long userId){
        GroupMembership groupMembership = groupMembershipService.addUserToGroup(userId,groupId);

    }

    public Set<AppUserWithRole> getGroupMember(Long groupId){
        return groupMembershipService.getGroupMembers(groupId);
    }


}
