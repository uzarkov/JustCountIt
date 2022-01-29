package com.justcountit.group;

import com.justcountit.group.membership.GroupMembershipService;
import com.justcountit.group.validation.DeletingUserFromGroupException;
import com.justcountit.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Objects;


@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService service;
    private final GroupMembershipService groupMembershipService;
    private final AppUserService appUserService;

    @GetMapping("/{groupId}/metadata")
    public ResponseEntity<GroupMetadata> getGroupMetadata(@PathVariable Long groupId) {
        return ResponseEntity.ok(service.getGroupMetadataFor(groupId));
    }

    @DeleteMapping("/{groupId}/user/{userId}")
    public ResponseEntity<String> deleteUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId, Principal principal){
        var callerEmail = principal.getName();
        var callerId = appUserService.getUserId(callerEmail);

        var isOrganizer = groupMembershipService.isOrganizer(callerId, groupId);
        // possibility of deleting yourself from group
        if (isOrganizer || Objects.equals(callerId, userId)){
            service.deleteUserFromGroup(userId, groupId);
            return ResponseEntity.ok("Deleted successfully");
        }
        else {
            throw DeletingUserFromGroupException.notAuthorized();
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


//
//    @GetMapping("/members/{groupId}")
//    public List<UsersInGroupMetadata> getGroupMembers(@PathVariable Long groupId){
//        Set<AppUserWithRole>  usersInGroup = service.getGroupMember(groupId);
//        List<UsersInGroupMetadata> returnList = new ArrayList<>();
//
//        for(var i : usersInGroup){
//            returnList.add(new UsersInGroupMetadata(i.getAppUser().getId(), i.getAppUser().getName(), i.getRole()));
//        }
//
//        return returnList;
//
//
//
//    }







}
