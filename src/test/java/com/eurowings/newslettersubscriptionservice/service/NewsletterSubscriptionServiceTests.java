package com.eurowings.newslettersubscriptionservice.service;

import com.eurowings.newslettersubscriptionservice.execption.UserIdNotFoundException;
import com.eurowings.newslettersubscriptionservice.model.User;
import com.eurowings.newslettersubscriptionservice.repository.UserRepository;
import com.eurowings.newslettersubscriptionservice.subscriptionstatus.SubscriptionStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.Random;

import static com.eurowings.newslettersubscriptionservice.model.User.UserBuilder.anUser;
import static com.eurowings.newslettersubscriptionservice.subscriptionstatus.SubscriptionStatus.SUBSCRIBED;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class NewsletterSubscriptionServiceTests {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void serviceShouldStoreAGivenUser() {
        //GIVEN
        final Long userId = new Random().nextLong();
        final User user = anUser()
                .withName("Max Musterman")
                .withUserEmail("m.mustermann@dev.org")
                .withId(userId)
                .withSubscriptionStatus(SUBSCRIBED)
                .withSubscribedAt(now())
                .build();
        //WHEN
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.saveUser(user);

        //THEN
        assertThat(userService.saveUser(user)).isNotNull();

        //Iteraction with the DB Expected
        verify(userRepository, times(2)).save(user);
    }

    @Test
    public void aGivenIdShouldReturnAUserWhichCanRecievedNewsLetterOrNot() {
        //GIVEN
        final User createUser = anUser()
                .withId(new Random().nextLong())
                .withName("Jill Hampton")
                .withUserEmail("j.hampton@web.org")
                .withSubscribedAt(of(2016, 3, 2))
                .withUnsubscribedAt(of(2018, 3, 28))
                .withSubscriptionStatus(SubscriptionStatus.UNSUBSCRIBED)
                .build();

        final Optional<User> findUser = Optional.of(createUser);

        //WHEN
        when(userRepository.findById(anyLong())).thenReturn(findUser);

        //THEN
        assertThat(userService.findUser(findUser.get().getId())).isOfAnyClassIn(Optional.class);
    }

    @Test
    public void aGivenIdWhichIsNotinTheDBShouldthrowAGivenExceptio() {
        final User createUser = anUser()
                .withName("Jill Hampton")
                .withUserEmail("j.hampton@web.org")
                .withSubscribedAt(of(2016, 3, 2))
                .withUnsubscribedAt(of(2018, 3, 28))
                .withSubscriptionStatus(SubscriptionStatus.UNSUBSCRIBED)
                .build();

        final Optional<User> findUser = Optional.of(createUser);

        //WHEN
        when(userRepository.findById(anyLong())).thenReturn(findUser);

        //THEN
        assertThatExceptionOfType(UserIdNotFoundException.class).isThrownBy(
                () -> userService.findUser(findUser.get().getId()));
    }
}
