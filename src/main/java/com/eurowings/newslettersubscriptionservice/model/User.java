package com.eurowings.newslettersubscriptionservice.model;


import com.eurowings.newslettersubscriptionservice.subscriptionstatus.SubscriptionStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "user_name")
    private String name;

    @NotNull
    @NotBlank(message = "you must provide a valid email")
    @Column(unique = true, name = "user_email")
    @Size(max = 60)
    @Email(message = "Please provide a valid emailadress")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;

    private LocalDate subscribedAt;

    private LocalDate unsubscribedAt;

    private User() {
        //this is only for JPA
    }

    public static final class UserBuilder {
        private Long Id;
        private String name;
        private String userEmail;
        private SubscriptionStatus subscriptionStatus;
        private LocalDate subscribedAt;
        private LocalDate unsubscribedAt;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(Long Id) {
            this.Id = Id;
            return this;
        }

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UserBuilder withSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
            this.subscriptionStatus = subscriptionStatus;
            return this;
        }

        public UserBuilder withSubscribedAt(LocalDate subscribedAt) {
            this.subscribedAt = subscribedAt;
            return this;
        }

        public UserBuilder withUnsubscribedAt(LocalDate unsubscribedAt) {
            this.unsubscribedAt = unsubscribedAt;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(Id);
            user.setName(name);
            user.setUserEmail(userEmail);
            user.setSubscriptionStatus(subscriptionStatus);
            user.setSubscribedAt(subscribedAt);
            user.setUnsubscribedAt(unsubscribedAt);
            return user;
        }
    }
}
