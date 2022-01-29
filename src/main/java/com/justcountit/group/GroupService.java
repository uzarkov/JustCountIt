package com.justcountit.group;

import com.justcountit.group.membership.GroupMembership;
import com.justcountit.group.membership.GroupMembershipService;
import com.justcountit.request.FinancialRequestService;
import com.justcountit.user.AppUserWithRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupMembershipService groupMembershipService;
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
//        if (!financialRequestService.hasFinancialRequests(userId, groupId))
//        {
//            groupMembershipService.deleteUserFromGroupMembership(userId, groupId);
//        }
//        else {
//            throw new RuntimeException("You cannot delete user with pending transaction");
//        }


    }
    public void addGroup(Group group){
        groupRepository.save(group);

    }
    public void addUserToGroup(Long groupId, Long userId){
        GroupMembership groupMembership = groupMembershipService.addUserToGroup(userId,groupId);

    }

    public Set<AppUserWithRole>  getGroupMember(Long groupId){
        return groupMembershipService.getGroupMembers(groupId);
    }


}
