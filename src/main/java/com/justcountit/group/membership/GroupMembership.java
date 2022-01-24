package com.justcountit.group.membership;

import com.justcountit.commons.Role;
import com.justcountit.group.Group;
import com.justcountit.user.AppUser;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GroupMembership {

    @EmbeddedId
    private GroupMembershipKey id;

    @ManyToOne
    @MapsId("appUserId")
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    @Enumerated(EnumType.STRING)
    private Role role;

    public GroupMembership(GroupMembershipKey groupMembershipKey, Role role) {
        id = groupMembershipKey;
        this.role = role;
        appUser = null;
        group = null;

    }
    public GroupMembership(AppUser user, Group group, Role role) {
        this.role = role;
        appUser = user;
        this.group = group;

    }
}
