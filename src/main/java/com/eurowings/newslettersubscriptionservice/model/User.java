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
    @Column(name = "user_id", updatable = false, nullable = false)
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


    public static final class UserEntityBuilder {
        private Long Id;
        private String name;
        private String userEmail;
        private SubscriptionStatus subscriptionStatus;
        private LocalDate subscribedAt;
        private LocalDate unsubscribedAt;
        private boolean isSubscribed;

        private UserEntityBuilder() {
        }

        public static UserEntityBuilder anUserEntity() {
            return new UserEntityBuilder();
        }

        public UserEntityBuilder withId(Long Id) {
            this.Id = Id;
            return this;
        }

        public UserEntityBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserEntityBuilder withUserEmail(String userEmail) {
            this.userEmail = userEmail;
            return this;
        }

        public UserEntityBuilder withSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
            this.subscriptionStatus = subscriptionStatus;
            return this;
        }

        public UserEntityBuilder withSubscribedAt(LocalDate subscribedAt) {
            this.subscribedAt = subscribedAt;
            return this;
        }

        public UserEntityBuilder withUnsubscribedAt(LocalDate unsubscribedAt) {
            this.unsubscribedAt = unsubscribedAt;
            return this;
        }

        public UserEntityBuilder withIsSubscribed(boolean isSubscribed) {
            this.isSubscribed = isSubscribed;
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
