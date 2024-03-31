package com.hcc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 6553880992653223928L;

    public UserAlreadyExistsException(String message) {super(message);}
}
