package com.eurowings.newslettersubscriptionservice.execption;

import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND, reason = "No User was found with this id")
public class UserIdNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE_FORMAT = "User with id '%d' was not found!";

    private final Long userId;

    public UserIdNotFoundException(final Long userId) {
        super(format(MESSAGE_FORMAT, userId));
        this.userId = userId;
    }
}
