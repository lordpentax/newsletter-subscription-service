package com.eurowings.newslettersubscriptionservice.service;

import com.eurowings.newslettersubscriptionservice.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    User saveUser(User user);

    User updateUser(final Long id, final User user);

    List<User> findAllUsers(final LocalDate subscribedAt);

    boolean exists(final User user);
}
