package com.justcountit.group.membership;

import com.justcountit.utils.exceptions.RestException;
import org.springframework.http.HttpStatus;

public class GroupMembershipException extends RestException {
    private GroupMembershipException(String message, HttpStatus status) {
        super(message, status);
    }

    public static GroupMembershipException principalNotMember() {
        var msg = "You are not a member of this group";
        return new GroupMembershipException(msg, HttpStatus.UNAUTHORIZED);
    }
}
