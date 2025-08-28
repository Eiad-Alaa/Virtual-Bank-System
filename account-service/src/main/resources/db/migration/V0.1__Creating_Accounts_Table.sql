CREATE TABLE IF NOT EXISTS vbank.account
(
    account_id uuid NOT NULL DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    account_number character varying(12) COLLATE pg_catalog."default" NOT NULL,
    account_type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    balance numeric(18,2) NOT NULL,
    status character varying(50) COLLATE pg_catalog."default",
    created_at timestamp with time zone DEFAULT now(),
    updated_at timestamp with time zone DEFAULT now(),
    last_transaction_at timestamp with time zone DEFAULT now(),
    CONSTRAINT account_pkey PRIMARY KEY (account_id),
    CONSTRAINT acc_num_unique UNIQUE (account_number),
    CONSTRAINT account_account_number_key UNIQUE (account_number)
)
