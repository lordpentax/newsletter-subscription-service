package com.eurowings.newslettersubscriptionservice.execption;

import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(value = CONFLICT, reason = "User with this email address already exists")
public class UserAlreadyExitstException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String MESSAGE_FORMAT = "User with this  email address '%s' already exists!";

    private final String userEmail;

    public UserAlreadyExitstException(final String userEmail) {
        super(format(MESSAGE_FORMAT, userEmail));
        this.userEmail = userEmail;
    }
}
