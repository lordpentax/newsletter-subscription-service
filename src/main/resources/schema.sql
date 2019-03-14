drop table user if exists;
create table user (
    user_id   INTEGER  AUTO_INCREMENT,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255),
    subscription_status varchar(50),
    subscribed_at DATETIME,
    unsubscribed_at DATETIME,
    PRIMARY KEY (user_id));