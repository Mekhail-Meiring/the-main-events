create table events (
    event_id BIGSERIAL PRIMARY KEY,
    from_client_id BIGINT not null REFERENCES clients (client_id),
    location TEXT not null,
    date VARCHAR(10) not null,
    time VARCHAR(7) not null,
    description VARCHAR(200)
);