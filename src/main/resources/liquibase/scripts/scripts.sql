-- liquibase formatted sql

-- changeset roma: 1 create table user
create table users
(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(50) not null UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(50),
    encoded_password VARCHAR(255) not null,
    image TEXT,
    role VARCHAR(50) not null
);

-- changeset roma: 2 create table ads
create table ads
(
    ad_id SERIAL PRIMARY KEY,
    author_id INT REFERENCES users(user_id) ON DELETE CASCADE ,
    description TEXT,
    image TEXT not null ,
    price INT,
    title VARCHAR(100)
);

-- changeset roma: 3 create table comment
create table comments
(
    comment_id    SERIAL PRIMARY KEY,
    ad_id         INT REFERENCES ads (ad_id) ON DELETE CASCADE,
    author_id     INT REFERENCES users (user_id) ON DELETE CASCADE,
    creating_time BIGINT not null,
    comment_text  TEXT   not null
);

