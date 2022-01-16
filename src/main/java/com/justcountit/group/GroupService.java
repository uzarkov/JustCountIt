package com.justcountit.group;

import com.justcountit.expenditure.ExpenditureService;
import com.justcountit.group.membership.GroupMembership;
import com.justcountit.group.membership.GroupMembershipService;
import com.justcountit.request.FinancialRequestService;
import com.justcountit.user.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupMembershipService groupMembershipService;
    private final FinancialRequestService financialRequestService;
    private final GroupRepository groupRepository;


    public void deleteUserFromGroup(Long userId, Long groupId)  {
        if (!financialRequestService.hasFinancialRequests(userId, groupId))
        {
            groupMembershipService.deleteUserFromGroupMembership(userId, groupId);
        }
        else {
            throw new RuntimeException("You cannot delete user with pending transaction");
        }


    }
    public void addGroup(Group group){
        groupRepository.save(group);

    }
    public void addUserToGroup(Long groupId, Long userId){
        GroupMembership groupMembership = groupMembershipService.addUserToGroup(userId,groupId);

    }


}
