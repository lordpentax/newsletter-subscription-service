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
    public Optional<User> findById(final Long userId) {
        Optional<User> userEntity = userRepository.findById(userId);

        userEntity.orElseThrow(
                () -> new UserIdNotFoundException(userId));

        userRepository.save(userEntity.get());

        return userEntity;
    }

    @Override
    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(final Long userId, final User user) {
        return userRepository.findById(userId)
                .map(
                        userRepository::save
                ).orElseThrow(
                        () -> new UserIdNotFoundException(userId));
    }

    @Override
    public List<User> findAllUsers(final LocalDate subscribedAt) {
        return userRepository.findAllBySubscribedAt(subscribedAt);
    }


    @Override
    public boolean exists(User user) {
        return userRepository.findByUserEmail(user.getUserEmail()) != null;
    }
}
