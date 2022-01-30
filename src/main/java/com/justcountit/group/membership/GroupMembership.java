package com.justcountit.group.membership;

import com.justcountit.commons.Role;
import com.justcountit.group.Group;
import com.justcountit.request.FinancialRequest;
import com.justcountit.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy= "debtee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<FinancialRequest> requests;

    public GroupMembership(AppUser user, Group group, Role role) {
        this.id = GroupMembershipKey.from(user.getId(), group.getId());
        this.appUser = user;
        this.group = group;
        this.role = role;
        this.requests = new HashSet<>();
    }
}
