create table user (
    user_id   INTEGER  AUTO_INCREMENT,
    user_name VARCHAR(128) NOT NULL,
    user_email VARCHAR(128),
    subscriptionStatus varchar(50),
    subscribedAt date,
    unsubscribedAt date,
    PRIMARY KEY (user_id)
)
AS
SELECT *
FROM CSVREAD('/Users/cfernandez/IdeaProjects/newsletter-subscription-service/src/main/resources/mock_user.csv');