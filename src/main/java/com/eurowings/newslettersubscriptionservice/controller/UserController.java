package com.eurowings.newslettersubscriptionservice.controller;

import com.eurowings.newslettersubscriptionservice.execption.UserAlreadyExitstException;
import com.eurowings.newslettersubscriptionservice.model.User;
import com.eurowings.newslettersubscriptionservice.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static lombok.Lombok.checkNotNull;
import static org.springframework.format.annotation.DateTimeFormat.ISO;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    private final UserService userService;
    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserController(final UserService userService) {
        this.userService = checkNotNull(userService, "userService must not be null");
    }

    @ApiResponses({
            @ApiResponse(
                    code = 201, response = String.class,
                    message = "This endpoint subscribed or unsubscribed a user"),

            @ApiResponse(code = 409, message = "user with the given email address already exists")
    })
    @PostMapping(value = "subscritions/users", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody final User userDto) {
        if (userService.exists(userDto)) {
            throw new UserAlreadyExitstException(userDto.getUserEmail());
        }

        return ResponseEntity.ok().body(userService.saveUser(userDto));
    }

    @ApiResponses({
            @ApiResponse(
                    code = 200, response = String.class,
                    message = "This endpoint will provide you information if a user is SUBSCRIBED or not" +
                            "in order to recieved Newsletter"),

            @ApiResponse(code = 404, message = "When a User with the given Id was not found")
    })
    @GetMapping(value = "subscription/newsletter/users/{user_Id}")
    public ResponseEntity<User> findUser(@PathVariable final Long user_Id) {
        final Optional<User> user = userService.findUser(user_Id);
        if (!user.isPresent()) {
            LOGGER.info("No such user with ID: {} ", user.get().getId());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user.get());
    }

    @ApiResponses({
            @ApiResponse(
                    code = 200, response = String.class,
                    message = "This Endpoint return all existing user who in a given time is subscribe.The Time must be" +
                            "given in iso Dateformat e.g: yyyyy:mm:dd"),

            @ApiResponse(code = 404, message = "When The time is in the future")
    })
    @GetMapping("subscribtion/users/{subscribed_at}")
    ResponseEntity<List<User>> getAllUsers(@PathVariable @DateTimeFormat(iso = ISO.DATE) final LocalDate subscribed_at,
                                           final Pageable pageable  ) {
        if (subscribed_at.isAfter(LocalDate.now())) {
            LOGGER.info("The Time: {} is in The Future ", subscribed_at);
            return ResponseEntity.status(NOT_FOUND).build();
        }
        List<User> users = userService.findAllUsers(subscribed_at, pageable);
        return ResponseEntity.ok().body(users);
    }
}
