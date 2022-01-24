package com.justcountit.group;

import com.justcountit.commons.Role;
import com.justcountit.group.membership.GroupMembership;

public record MembershipMetadata(Long userId, String name, Role role) {
    public static MembershipMetadata from(GroupMembership groupMembership) {
        return new MembershipMetadata(groupMembership.getAppUser().getId(),
                                      groupMembership.getAppUser().getName(),
                                      groupMembership.getRole());
    }
}
