package com.justcountit.group;

import com.justcountit.group.membership.GroupMembershipService;
import com.justcountit.group.validation.DeletingUserFromGroupException;
import com.justcountit.user.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
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

        service.checkIfOrganizer(callerId,groupId, userId);
        String message  = service.deleteUserFromGroup(userId, groupId);
        return ResponseEntity.ok(message);

    }
    @GetMapping
    public ResponseEntity<List<GroupBaseData>> getAllGroups(Principal principal) {
        var userEmail = principal.getName();
        return ResponseEntity.ok(service.getAllGroups(userEmail));
    }

    @PostMapping
    public ResponseEntity<GroupBaseData> addGroup(@RequestBody GroupBaseData groupData, Principal principal){
        var organizerEmail = principal.getName();
        return ResponseEntity.ok(service.addGroup(groupData.toGroup(), organizerEmail));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> removeGroup(@PathVariable Long groupId, Principal principal) {
        var callerName = principal.getName();
        service.removeGroup(groupId, callerName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{groupId}/user/{userId}")
    public void addUserToGroup( @PathVariable Long groupId, @PathVariable Long userId){
        service.addUserToGroup(groupId, userId);
    }
}
