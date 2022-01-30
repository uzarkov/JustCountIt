package com.justcountit.group.validation;

import com.justcountit.expenditure.validation.ExpenditureValidationException;
import com.justcountit.utils.exceptions.RestException;
import org.springframework.http.HttpStatus;

public class DeletingUserFromGroupException extends RestException {
    private DeletingUserFromGroupException(String message) {
        super(message, HttpStatus.PRECONDITION_FAILED);
    }

    public static DeletingUserFromGroupException pendingTransaction() {
        var msg = "You cannot delete user which has pending transactions!!";
        return new DeletingUserFromGroupException(msg);
    }

    public static DeletingUserFromGroupException notAuthorized() {
        var msg = "You are not authorized to delete this user!!";
        return new DeletingUserFromGroupException(msg);
    }
}
