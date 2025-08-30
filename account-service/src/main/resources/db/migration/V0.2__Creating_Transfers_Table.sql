CREATE TABLE IF NOT EXISTS vbank.transfer
(
    transaction_id uuid NOT NULL,
    from_account uuid NOT NULL,
    to_account uuid NOT NULL,
    amount numeric(18,2) NOT NULL,
    created_at timestamp with time zone DEFAULT now(),
    CONSTRAINT transfer_pkey PRIMARY KEY (transaction_id),
    CONSTRAINT transfer_from_account_fkey FOREIGN KEY (from_account)
        REFERENCES vbank.account (account_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
    CONSTRAINT transfer_to_account_fkey FOREIGN KEY (to_account)
        REFERENCES vbank.account (account_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
)