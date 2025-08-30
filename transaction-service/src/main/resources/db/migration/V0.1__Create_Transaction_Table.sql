CREATE TABLE IF NOT EXISTS vbank.transaction
(
    transaction_id uuid NOT NULL,
    from_account uuid NOT NULL,
    to_account uuid NOT NULL,
    amount numeric(18,2) NOT NULL,
    status character varying(255) COLLATE pg_catalog."default",
    description character varying(255) COLLATE pg_catalog."default",
    created_at timestamp without time zone DEFAULT now(),
    CONSTRAINT transaction_pkey PRIMARY KEY (transaction_id)
)