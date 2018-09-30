package com.eurowings.newslettersubscriptionservice.service;

import com.eurowings.newslettersubscriptionservice.execption.UserIdNotFoundException;
import com.eurowings.newslettersubscriptionservice.model.User;
import com.eurowings.newslettersubscriptionservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static lombok.Lombok.checkNotNull;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = checkNotNull(userRepository, "userRepository must not be null");
    }

    @Override
    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUser(final Long userId) {
        return Optional.of(userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserIdNotFoundException(userId)));
    }

    @Override
    public List<User> findAllUsers(final LocalDate subscribedAt) {
        if (subscribedAt == null) {
            LOGGER.info("There's no User at this given Time", subscribedAt);
            throw new RuntimeException(String.format("There's no such user a this given time %s", subscribedAt));
        }
        return userRepository.findUserByName(subscribedAt);
    }


    @Override
    public boolean exists(User user) {
        return userRepository.findByUserEmail(user.getUserEmail()) != null;
    }
}
