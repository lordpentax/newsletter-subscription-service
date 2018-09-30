package com.eurowings.newslettersubscriptionservice.controller;

import com.eurowings.newslettersubscriptionservice.execption.UserAlreadyExitstException;
import com.eurowings.newslettersubscriptionservice.model.User;
import com.eurowings.newslettersubscriptionservice.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static lombok.Lombok.checkNotNull;
import static org.springframework.format.annotation.DateTimeFormat.ISO;
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
    @PostMapping(value = "/newsletter", consumes = APPLICATION_JSON_UTF8_VALUE,
            produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody final User userDto) {
        if (userService.exists(userDto)) {
            throw new UserAlreadyExitstException(userDto.getUserEmail());
        }
        userService.saveUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/newsletter/user/{user_Id}")
    public ResponseEntity<User> findUser(@PathVariable final Long user_Id,
                                         @Valid @RequestBody final User userDto) {

        final User user = userService.updateUser(user_Id, userDto);

        if (user == null) {
            LOGGER.info("No such user with ID: {} ", user.getId());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().build();
    }

    @ApiResponses({
            @ApiResponse(
                    code = 200, response = String.class,
                    message = "This Endpoint return all existing user who in a given time unsubscribe or subscribe"),
    })
    @GetMapping("/users/{subscribed_at}")
    ResponseEntity<List<User>> getAllUsers(@PathVariable @DateTimeFormat(iso = ISO.DATE) final LocalDate subscribed_at) {
        List<User> users = userService.findAllUsers(subscribed_at);

        if (users == null || users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(users);
    }
}