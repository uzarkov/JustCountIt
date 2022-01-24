package com.justcountit.group;

import com.justcountit.group.membership.GroupMembershipRepository;
import com.justcountit.group.membership.GroupMembershipService;
import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserRepository;
import com.justcountit.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.persistence.PostLoad;
import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor

public class GroupController {
    private final GroupService service;
    private final GroupMembershipService groupMembershipService;
    private final AppUserService appUserService;

    @DeleteMapping("/{groupId}/user/{userId}")
    public void deleteUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId, Principal principal){
        var callerEmail = principal.getName();

        var callerId = appUserService.getUserId(callerEmail);

        var isOrganizer = groupMembershipService.isOrganizer(callerId, groupId);
        // possibility of deleting yourself from group
        if (isOrganizer || Objects.equals(callerId, userId)){
            service.deleteUserFromGroup(userId, groupId);
        }
        else {
            throw new AuthorizationServiceException("Go back to the Shadow! You shall not pass!");
        }

    }

    @PostMapping
    public void addGroup(@RequestBody Group group){
        service.addGroup(group);
    }

    @PostMapping("/{groupId}/user/{userId}")
    public void addUserToGroup( @PathVariable Long groupId, @PathVariable Long userId){
        System.out.println(groupId);
        System.out.println(userId);
        service.addUserToGroup(groupId, userId);
    }
    // You can add expenditure from some user in group and assign financialRequest to debtor, only for testing
    @PostMapping("/{groupId}/user/{userId}/debtor/{debtorId}")
    public void addExpenditureAndRequest( @PathVariable Long groupId, @PathVariable Long userId, @PathVariable Long debtorId){
        service.addExpenditureAndRequest(groupId, userId, debtorId);

    }
    @GetMapping
    public String sth(){
        return "Hello";
    }





}