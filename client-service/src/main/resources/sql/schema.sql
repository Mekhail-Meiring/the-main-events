DROP TABLE IF EXISTS clients CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS friends CASCADE;

CREATE TABLE clients (
     client_id BIGSERIAL PRIMARY KEY ,
     first_name VARCHAR(50) NOT NULL,
     last_name VARCHAR(50) NOT NULL,
     email VARCHAR(50) NOT NULL,
     password TEXT NOT NULL
);

CREATE TABLE events (
    event_id BIGSERIAL PRIMARY KEY,
    from_client_id BIGINT not null REFERENCES clients (client_id),
    location TEXT not null,
    date VARCHAR(10) not null,
    time VARCHAR(7) not null,
    description VARCHAR(200)
);


CREATE TABLE friends (
     client_id BIGINT REFERENCES clients (client_id),
     friend_id BIGINT REFERENCES clients (client_id)
)