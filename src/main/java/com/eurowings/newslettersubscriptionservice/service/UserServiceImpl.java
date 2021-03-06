package com.eurowings.newslettersubscriptionservice.service;

import com.eurowings.newslettersubscriptionservice.execption.UserIdNotFoundException;
import com.eurowings.newslettersubscriptionservice.model.User;
import com.eurowings.newslettersubscriptionservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.eurowings.newslettersubscriptionservice.subscriptionstatus.SubscriptionStatus.SUBSCRIBED;
import static java.time.LocalDate.now;
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
        if (user.getSubscriptionStatus().equals(SUBSCRIBED)) {
            user.setSubscribedAt(now());
        } else {
            user.setUnsubscribedAt(now());
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUser(final Long userId) {
        return Optional.of(userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserIdNotFoundException(userId)));
    }

    @Override
    public List<User> findAllUsers(final LocalDate subscribedAt, final Pageable pageable) {
        try {
            if (subscribedAt.isAfter(now())) {
                throw new RuntimeException("The time must not be in the future");
            }
        } catch (Exception e) {
            LOGGER.info("The given time was not valid cause is in the future", e);
        }
        return userRepository.findBySubscribedAt(subscribedAt,pageable);
    }

    @Override
    public boolean exists(User user) {
        return userRepository.findByUserEmail(user.getUserEmail()) != null;
    }

    private Pageable createPageRequest() {
        return PageRequest.of(0, 30);
    }
}
