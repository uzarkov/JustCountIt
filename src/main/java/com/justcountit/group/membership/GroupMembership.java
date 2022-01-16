package com.justcountit.group.membership;

import com.justcountit.commons.Role;
import com.justcountit.group.Group;
import com.justcountit.user.AppUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class GroupMembership {

    @EmbeddedId
    GroupMembershipKey id;

    @ManyToOne
    @MapsId("appUserId")
    @JoinColumn(name = "appuser_id")
    private AppUser appUser;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;

    private Role role;
}
