create table user (
    user_id   INTEGER  AUTO_INCREMENT,
    user_name VARCHAR(128) NOT NULL,
    user_email VARCHAR(128),
    subscription_status varchar(50),
    subscribed_at date,
    unsubscribed_at date,
    PRIMARY KEY (user_id)
)
AS
SELECT *
FROM CSVREAD('classpath:mock_user.csv');