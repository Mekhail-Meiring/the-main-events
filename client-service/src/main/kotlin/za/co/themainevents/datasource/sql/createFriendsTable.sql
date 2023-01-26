CREATE TABLE friends (
    client_id BIGINT REFERENCES clients (client_id),
    friend_id BIGINT REFERENCES clients (client_id)
)