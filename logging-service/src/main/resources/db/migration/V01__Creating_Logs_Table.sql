CREATE TABLE IF NOT EXISTS vbank.logs (
    log_id UUID NOT NULL,
    message TEXT NOT NULL,
    message_type VARCHAR(255) NOT NULL,
    date_time TIMESTAMP NOT NULL,
    producer_service VARCHAR(255),
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT pk_logs PRIMARY KEY (log_id)
);