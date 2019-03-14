package com.eurowings.newslettersubscriptionservice.service;

import com.eurowings.newslettersubscriptionservice.model.User;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> findUser(final Long id);

    List<User> findAllUsers(final LocalDate subscribedAt, final Pageable pageable);

    boolean exists(final User user);
}
