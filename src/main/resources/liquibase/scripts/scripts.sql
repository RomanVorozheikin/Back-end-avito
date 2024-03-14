create table users
(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(50) not null,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    phone VARCHAR(50),
    encoded_password VARCHAR(255) not null,
    image VARCHAR(50),
    role VARCHAR(50) not null
);
