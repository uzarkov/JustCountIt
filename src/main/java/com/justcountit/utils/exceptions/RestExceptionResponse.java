package com.justcountit.utils.exceptions;

record RestExceptionResponse(String message, int status, String error) {
    public static RestExceptionResponse from(RestException restException) {
        return new RestExceptionResponse(restException.getMessage(),
                                         restException.getStatus().value(),
                                         restException.getStatus().getReasonPhrase());
    }
}
