CREATE TABLE money (
    id CHAR(36),
    amount VARCHAR(50),
    type BOOLEAN,
    detail VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
