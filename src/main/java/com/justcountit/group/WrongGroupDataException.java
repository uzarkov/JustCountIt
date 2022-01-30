package com.justcountit.group;

import com.justcountit.utils.exceptions.RestException;
import org.springframework.http.HttpStatus;

public class WrongGroupDataException extends RestException {
    private WrongGroupDataException(String message, HttpStatus status) {
        super(message, status);
    }

    public static WrongGroupDataException wrongData() {
        var msg = "Provided group data is not valid.";
        return new WrongGroupDataException(msg, HttpStatus.BAD_REQUEST);
    }
}