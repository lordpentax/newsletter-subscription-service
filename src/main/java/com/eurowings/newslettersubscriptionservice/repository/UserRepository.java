package com.eurowings.newslettersubscriptionservice.repository;

import com.eurowings.newslettersubscriptionservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserEmail(final String userEmail);

    @Query(value = "SELECT u FROM User u WHERE u.subscriptionStatus = 'SUBSCRIBED' " +
            "AND u.subscribedAt = :subscribtiontime")
    List<User> findAllBySubscribedAt(@Param("subscribtiontime") final LocalDate subscribtiontime);
}
