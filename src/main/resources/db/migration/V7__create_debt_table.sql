CREATE TABLE debt (
    id CHAR(36),
    name VARCHAR(255),
    amount VARCHAR(50),
    detail VARCHAR(255),
    type BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
