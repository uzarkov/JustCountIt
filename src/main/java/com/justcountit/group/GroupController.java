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

        if (isOrganizer){
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

    @GetMapping
    public String sth(){
        return "Hello";
    }





}
