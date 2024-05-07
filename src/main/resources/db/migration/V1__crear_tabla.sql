CREATE SEQUENCE user_detail_seq START 1;

CREATE TABLE user_detail (
    id BIGINT DEFAULT nextval('user_detail_seq') PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(100) not null
);