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

    public static GroupMembershipException principalNotOrganizer() {
        var message = "You can not delete this group because you are not organiser";
        return new GroupMembershipException(message, HttpStatus.UNAUTHORIZED);
    }
}
